package com.gzy.imapplication.module.message;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzy.imapplication.R;
import com.gzy.imapplication.core.Auth;
import com.gzy.imapplication.model.Chat;
import com.gzy.imapplication.model.ChatSession;

import java.util.List;

class ChatAdapter extends BaseMultiItemQuickAdapter<Chat, BaseViewHolder> {


    public ChatAdapter(@Nullable List<Chat> data) {
        super(data);

        // 绑定 layout 对应的 type
        addItemType(Chat.Me, R.layout.item_chat_me);
        addItemType(Chat.Other, R.layout.item_chat_other);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, Chat item) {

        helper.setText(R.id.tv_chat_time,item.getDate());
        helper.setText(R.id.tv_chat_data,item.getContent());

//        helper.setText(R.id.tv_name,item.getFrom().getName() + " -> " + item.getTo().getName() + " : <" + item.getContent() + ">");

        Glide.with(mContext)
                .load(item.getFrom().getAvatarUrlString())
                .into((ImageView) helper.getView(R.id.iv_chat_head_icon));


    }
}