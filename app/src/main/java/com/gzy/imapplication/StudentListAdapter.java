package com.gzy.imapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class StudentListAdapter extends BaseQuickAdapter<Student, BaseViewHolder> {

    public StudentListAdapter(@Nullable List<Student> data) {
        super(R.layout.item_student,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, Student item) {
        helper.setText(R.id.tv_name,item.getName());
    }
}
