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
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaskListActivity extends
    RecyclerView.Adapter<TaskListAdapter.RecyclerView.ViewHolder> {

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
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.task_layout,parent, false);
        return new ViewHolder(view);
        RecyclerView recyclerView = findViewById(R.id.taskListRecyclerView);

    }
    // Create callbacks for the ItemTouchHelper and pass the chosen
// direction of swipe as a parameter
    ItemTouchHelper.SimpleCallback touchHelperCallback = new
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

                // This callback returns false to prevent move gestures in the RecyclerView.
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView,
                                      @NonNull RecyclerView.ViewHolder viewHolder,
                                      @NonNull RecyclerView.ViewHolder target) {
                    return false;
                }

                // This callback should respond to swipe gestures
                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder,
                                     int direction) {

                    // Get position of swiped item from the ViewHolder
                    int swipedPosition = viewHolder.getAdapterPosition();

                    // Call a method in the Adapter which will delete the task at this position
                    taskListAdapter.deleteTask(swipedPosition);
                }
            };

    // Create the ItemTouchHelper with the callback
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);

// Attach the ItemTouchHelper to the RecyclerView
itemTouchHelper.attachToRecyclerView(recyclerView);
    TaskListAdapter taskListAdapter = new TaskListAdapter();
        recyclerView.setAdapter(taskListAdapter);
        tasks.observe (this, new Observer<List<Task>>(){

            @override
                    public void onChanged(List<Task>tasks){
            taskListAdapter.setTaskList(db, tasks);
                Log.d("ToDoApp", "The task list has changed");
                linearLayout.removeAllViews();
                for (Task task : tasks){
                    Log.d("ToDoApp", task.title + " : " + task.description);

                    View listView = getLayoutInflater().inflate(
                            R.layout.task_layout,
                            linearLayout,
                            false);
                    TextView titleView = listView.findViewById(R.id.taskListTitle);
                    TextView descView = listView.findViewById(R.id.taskListDesc);
                    ImageView imageView = listView.findViewById(R.id.taskListImage);

                    titleView.setText(task.title);
                    descView.setText(task.description);
                    imageView.setImageURI(Uri.parse(task.imageURI));
                    linearLayout.addView(listView);
                }
        }
    };

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder, int position) {
//        final Task task = tasks.get(position);
//        holder.titleView.setText(task.title);
//        holder.descView.setText(task.description);
//        holder.imageView.setImageURI(Uri.parse(task.image));
        holder.doneCheckBox.setChecked(task.done);
        holder.doneCheckBox.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener(){
                    @Override
                    public void onCheckedChnaged(CompoundButton buttonView, boolean isChecked){
                        task.done = isChecked;
                        Executor myExecutor = Executor.newSingleThreadExecutor();
                        myExecutor.execute(new Runnable(){
                            @Override
                            public void run() {
                                db.tasksDAO().updateTask(task);
                            }
                        });
                    }
                }
        );
    }

    @Override
    public int getItemCount(){
        if (tasks == null) return 0;
        return tasks.size();
    }

    public void deleteTask(int position) {

        // Get the task from the specified location in the list
        final Task task = tasks.get(position);

        // Delete the task from the database
        // On separate thread to UI
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {

                // Execute delete operation from TaskDAO
                db.tasksDAO().deleteTask(task);
            }
        });
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleView;
        TextView descView;
        ImageView imageView;
        CheckBox doneCheckBox;

        public ViewHolder(@NonNull View itemView){

            super(itemView);

            titleView = itemView.findViewById(R.id.taskListTitle);
            descView = itemView.findViewById(R.id.taskListDesc);
            imageView = itemView.findViewById(R.id.taskListImage);
            doneCheckBox = itemView.findViewById(R.idtaskListDone);

        }
    }


}





