package com.gzy.imapplication.module.contacts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gzy.imapplication.R;
import com.gzy.imapplication.model.Account;

import java.util.List;

public class SearchContactAdapter  extends BaseQuickAdapter<Account,BaseViewHolder> {


    public SearchContactAdapter(@Nullable List<Account> data) {
        super(R.layout.item_student,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Account item) {
        helper.setText(R.id.tv_name,item.getName());
    }
}