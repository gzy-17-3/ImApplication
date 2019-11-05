package com.gzy.imapplication;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    List<Student> dataList;

    public StudentAdapter(Context context,List<Student> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    /**
     *
     *  getItemView
     * onCreateViewHolder
     * @param parent
     * @param viewType
     * @return
     */
    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_student,null);
        return new StudentViewHolder(view);
    }

    /**
     * onBind ViewHolder
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = dataList.get(position);
        holder.tv_name.setText(student.getName());
        holder.tv_sid.setText(student.getSid());
        holder.tv_mark.setText(student.getMark());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView tv_sid;
        TextView tv_name;
        TextView tv_mark;
        public StudentViewHolder(View view) {
            super(view);
            tv_sid = view.findViewById(R.id.tv_sid);
            tv_name = view.findViewById(R.id.tv_name);
            tv_mark = view.findViewById(R.id.tv_mark);
        }


    }
}


