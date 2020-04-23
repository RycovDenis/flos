package kz.dev.home.flos.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import kz.dev.home.flos.R;
import kz.dev.home.flos.datamodels.Task;
import kz.dev.home.flos.activitys.UpdateTaskActivity;

public class TasksAdapter extends RecyclerView.Adapter<TasksAdapter.TasksViewHolder> {

    private Context mCtx;
    private List<Task> taskList;

    public TasksAdapter(Context mCtx, List<Task> taskList) {
        this.mCtx = mCtx;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TasksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.rcv_task_item, parent, false);
        return new TasksViewHolder(view);
    }

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void onBindViewHolder(TasksViewHolder holder, int position) {
        Task t = taskList.get(position);
        holder.textViewTask.setText(t.getTask());
        holder.textViewDesc.setText(t.getDesc());
        holder.textViewFinishBy.setText(t.getFinishBy());
        final int blockedColorValue = Color.parseColor("#D30000");
        final int lowerColorValue = Color.parseColor("#64DD17");

        if (t.isFinished()) {
            holder.textViewStatus.setText(R.string.completed);
            holder.textViewStatus.setBackgroundColor(lowerColorValue);
        }
        else {
            holder.textViewStatus.setText(R.string.not_completed);
            holder.textViewStatus.setBackgroundColor(blockedColorValue);
        }
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    class TasksViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView textViewStatus, textViewTask, textViewDesc, textViewFinishBy;
        CardView cardViewTask;

        TasksViewHolder(View itemView) {
            super(itemView);
            cardViewTask = itemView.findViewById(R.id.cv_task);
            textViewStatus = itemView.findViewById(R.id.textViewStatus);
            textViewTask = itemView.findViewById(R.id.textViewTask);
            textViewDesc = itemView.findViewById(R.id.textViewDesc);
            textViewFinishBy = itemView.findViewById(R.id.textViewFinishBy);


            cardViewTask.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Task task = taskList.get(getAdapterPosition());
            Intent intent = new Intent(mCtx.getApplicationContext(), UpdateTaskActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("task", task);
            mCtx.startActivity(intent);
        }
    }
}
