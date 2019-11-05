package com.gzy.imapplication;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    public interface OnNeedLoadMoreListener{
        void loadMoreData();
    }

    public OnNeedLoadMoreListener onNeedLoadMoreListener;
    public boolean isOnNeedLoadMoreListener;
    public boolean isNoMoreData = false;

    Context context;
    List<Student> dataList;

    public StudentAdapter(Context context,List<Student> dataList) {
        this.context = context;
        this.dataList = dataList;
        isOnNeedLoadMoreListener = false;
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
        Log.d("TAG", "onBindViewHolder: position"+position);
        if (position == dataList.size() - 1){
            // 加载下一页
            if (onNeedLoadMoreListener != null) {
                // 当为非加载更多的时候才需要加载更多
                if (!isOnNeedLoadMoreListener && isNoMoreData == false) {
                    isOnNeedLoadMoreListener = true;
                    onNeedLoadMoreListener.loadMoreData();
                }

            }
        }
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


