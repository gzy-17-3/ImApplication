package com.gzy.imapplication.module.message;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzy.imapplication.R;
import com.gzy.imapplication.model.Account;
import com.gzy.imapplication.model.ChatSession;

import java.util.ArrayList;
import java.util.List;

class MessageAdapter  extends BaseQuickAdapter<ChatSession, BaseViewHolder> {


    public MessageAdapter(@Nullable List<ChatSession> data) {
        super(R.layout.item_student,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ChatSession item) {
        helper.setText(R.id.tv_name,item.getAccount().getName());

        Glide.with(mContext)
                .load(item.getAccount().getAvatarUrlString())
                .into((ImageView) helper.getView(R.id.tv_item_icon_note));
    }
}