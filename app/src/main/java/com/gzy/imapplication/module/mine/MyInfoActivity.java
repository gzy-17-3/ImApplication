package com.gzy.imapplication.module.mine;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;

import com.allen.android.lib.PermissionUtils;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.Token;
import com.gzy.imapplication.module.base.BaseActivity;
import com.gzy.imapplication.net.MineApi;
import com.gzy.imapplication.net.URLSet;
import com.gzy.imapplication.net.core.XXModelCallback;
import com.gzy.imapplication.net.core.XXURLUtils;
import com.gzy.imapplication.net.core.XXUploadFileUtils;
import com.gzy.imapplication.utils.Uri2FileUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.Call;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;


@RuntimePermissions
public class MyInfoActivity extends BaseActivity {

//    private static final int CAMERA_REQUEST_CODE = 100001;
    private static final short CAMERA_REQUEST_CODE = 1000;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MyInfoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    ImageView iv_avatar;
    TextView tv_name;
    TextView tv_gender;
    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        iv_avatar = findViewById(R.id.iv_avatar);
        tv_name = findViewById(R.id.tv_name);
        tv_gender = findViewById(R.id.tv_gender);

//        Token token = Auth.loadToken(this);

//        dialogs  对话框

//        Alert   弹框
//        AlertSheet  底部弹出的弹框


    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();
    }

    public void loadData() {
        // 加载数据操作

        Token token1 = Auth.loadToken(this);

        String userid = token1.getUserid() + "";
        String token = token1.getToken();
        MineApi.loadMineInfo(userid, token, new XXModelCallback<Account>(Account.class) {

            @Override
            public void onResponseData(Call call, Account model) {
                MyInfoActivity.this.account = model;
                refreshUI(model);
            }

            @Override
            public void onFailure2(Call call, IOException e, ErrType type, String message) {
                Toast.makeText(getContext(), "" + message, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void refreshUI(Account account) {
        // 从 acc 中获取需要显示的数据， 并且设置到 View 上

        tv_name.setText(account.getName());

        String genderStr = "未知";
        if (account.getGender() == 1) {
            genderStr = "女";
        } else if (account.getGender() == 2) {
            genderStr = "男";
        }

        tv_gender.setText(genderStr);

        if (TextUtils.isEmpty(account.getAvatar())) {

            Glide
                    .with(this)
                    .load(R.drawable.ic_tabbar_mine_n)
                    .into(iv_avatar);

            return;
        }

        String url = URLSet.File.PATH + "/" + account.getAvatar();
        Glide
                .with(this)
                .load(url)
                .into(iv_avatar);

    }

    public void onClickRowAvatar(View view) {

        if (account == null) {
            return;
        }
        //或者builder模式创建
        new AlertView.Builder().setContext(this)
                .setStyle(AlertView.Style.ActionSheet)
                .setTitle("选择操作")
                .setMessage(null)
                .setCancelText("取消")

                .setOthers(new String[]{"拍照", "从相册中选择"})

                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {

                        if (position == 0){
                            // 拍照
//                            takePhotos();
                            MyInfoActivityPermissionsDispatcher.takePhotosWithPermissionCheck(MyInfoActivity.this);

                        }else if (position == 1){
                            // 从相册中选择
                            chooseAlbum();
                        }
                    }
                })
                .build()
                .show();

    }

    //用于保存拍照图片的uri
    private Uri mCameraUri;
    // 用于保存图片的文件路径，Android 10以下使用图片路径访问图片
    private String mCameraImagePath;
    // 是否是Android 10以上手机
    private boolean isAndroidQ = Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q;


    @NeedsPermission({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void takePhotos() {

        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        Uri photoUri = null;

        if (isAndroidQ) {
            // 适配 android 10
            photoUri = createImageUri();//getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        } else {
            try {
                photoFile = createImageFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (photoFile != null) {
                mCameraImagePath = photoFile.getAbsolutePath();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                    photoUri = FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", photoFile);
                } else {
                    photoUri = Uri.fromFile(photoFile);
                }
            }
        }

        mCameraUri = photoUri;
        if (photoUri != null) {
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
        }

    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件 安卓 7 - 9
     */
    private File createImageFile() throws IOException {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                File file;
                if (isAndroidQ) {
                    // Android 10 使用图片uri加载
//                    ivPhoto.setImageURI(mCameraUri);
//                    mCameraUri
                    Toast.makeText(this, ""+mCameraUri, Toast.LENGTH_SHORT).show();

                    file = Uri2FileUtils.uriToFile(mCameraUri, this);



                } else {
                    // 使用图片路径加载
//                    ivPhoto.setImageBitmap(BitmapFactory.decodeFile(mCameraImagePath));

                    Toast.makeText(this, ""+mCameraImagePath, Toast.LENGTH_SHORT).show();
                    file = new File(mCameraImagePath);

                }

                // 上传

                uploadAvatarFile(file);
            } else {
                Toast.makeText(this,"取消",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadAvatarFile(File file) {
//        XXURLUtils.shared.

        XXUploadFileUtils.uploadFile(URLSet.File.UPLOAD,file);

    }

    @OnPermissionDenied({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onCameraDenied() {
        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show();
    }

    @OnNeverAskAgain({Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void onCameraNeverAskAgain() {
        runOnUiThread(2000, new Runnable() {
            @Override
            public void run() {
                new AlertView("警告", "您已经永久拒绝了授权照相机权限，\n是否跳转到系统设置页面？", "取消", new String[]{"确定"}, null, getContext(),
                        AlertView.Style.Alert, new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        Toast.makeText(MyInfoActivity.this, R.string.permission_camera_never_askagain, Toast.LENGTH_SHORT).show();
                        try {
                            PermissionUtils.toPermissionSetting(getContext());
                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();

            }
        });
    }



    public void chooseAlbum() {
        Toast.makeText(this, "从相册选择", Toast.LENGTH_SHORT).show();
    }

    public void onClickRowName(View view) {

        startActivity(new Intent(this,UpdateUsernameActivity.class));

    }

    public void onClickRowGender(View view) {

    }


//    @OnShowRationale(Manifest.permission.CAMERA)
//    fun showRationaleForCamera(request: PermissionRequest) {
//        showRationaleDialog(R.string.permission_camera_rationale, request)
//    }
//
//    @OnPermissionDenied(Manifest.permission.CAMERA)
//    fun onCameraDenied() {
//        Toast.makeText(this, R.string.permission_camera_denied, Toast.LENGTH_SHORT).show()
//    }
//
//    @OnNeverAskAgain(Manifest.permission.CAMERA)
//    fun onCameraNeverAskAgain() {
//        Toast.makeText(this, R.string.permission_camera_never_askagain, Toast.LENGTH_SHORT).show()
//    }



}
