package com.gzy.imapplication.module.contacts;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzy.imapplication.R;
import com.gzy.imapplication.model.AddFriendRequestFullAccount;

import java.util.List;

public class AddFriendRequestAdapter extends BaseQuickAdapter<AddFriendRequestFullAccount, BaseViewHolder> {


    public AddFriendRequestAdapter(@Nullable List<AddFriendRequestFullAccount> data) {
        super(R.layout.item_add_friend_request,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, AddFriendRequestFullAccount item) {
        helper.setText(R.id.tv_name,item.getAccount().getName());


        helper.setText(R.id.tv_state,item.getOperationEnum().toString());
        helper.setText(R.id.tv_date,item.getCreatedDate());

        Glide.with(mContext)
                .load(item.getAccount().getAvatarUrlString())
                .into((ImageView) helper.getView(R.id.tv_item_icon_note));
    }
}