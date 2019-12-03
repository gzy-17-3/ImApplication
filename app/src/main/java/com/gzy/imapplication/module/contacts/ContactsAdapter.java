package com.gzy.imapplication.module.contacts;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzy.imapplication.R;
import com.gzy.imapplication.model.Account;

import java.util.List;

class ContactsAdapter extends BaseQuickAdapter<Account, BaseViewHolder> {


    public ContactsAdapter(@Nullable List<Account> data) {
        super(R.layout.item_student,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Account item) {
        helper.setText(R.id.tv_name,item.getName());

        Glide.with(mContext)
                .load(item.getAvatarUrlString())
                .into((ImageView) helper.getView(R.id.tv_item_icon_note));
    }
}