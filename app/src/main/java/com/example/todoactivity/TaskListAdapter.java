//package com.example.todoactivity;
//
//public class TaskListAdapter {
//}
package com.example.todoactivity;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

    private List<Task> tasks;
    private TasksDB db;

    public void setTaskList(TasksDB db, List<Task> tasks) {
        this.db = db;
        this.tasks = tasks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = tasks.get(position);

        holder.titleView.setText(task.title);
        holder.descView.setText(task.description);
        holder.imageView.setImageURI(Uri.parse(task.imageURI));

        holder.doneCheckBox.setChecked(task.done);

        holder.doneCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        task.done = isChecked;

                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(() -> db.tasksDAO().updateTask(task));
                    }
                });
    }

    @Override
    public int getItemCount() {
        return (tasks == null) ? 0 : tasks.size();
    }

    public void deleteTask(int position) {
        Task task = tasks.get(position);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> db.tasksDAO().deleteTask(task));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView titleView;
        TextView descView;
        ImageView imageView;
        CheckBox doneCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            titleView = itemView.findViewById(R.id.taskListTitle);
            descView = itemView.findViewById(R.id.taskListDesc);
            imageView = itemView.findViewById(R.id.taskListImage);
            doneCheckBox = itemView.findViewById(R.id.taskListDone);
        }
    }
}

