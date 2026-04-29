package com.example.todoactivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    TasksDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_to_do_list_layout);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        db = TasksDB.getInstance(this);
        LinearLayout linearLayout = findViewById(R.id.taskLinearLayout);
        LiveData<List<Task>> tasks = db.tasksDAO().observeAll();

        tasks.observe(this, new Observer<List<Task>>() {
            @Override
            public void onChanged(List<Task> tasks) {


                linearLayout.removeAllViews();

                for (Task task : tasks) {
                    Log.d("ToDoApp", task.title + ":" + task.description);

//                    TextView textView = new TextView(getApplicationContext());
//                    textView.setText(task.title);
//                    linearLayout.addView(textView);
                    View listView = getLayoutInflater().inflate(R.layout.task_layout, linearLayout, false);
                    TextView titleView = listView.findViewById(R.id.taskTitleView);
                    TextView descView = listView.findViewById(R.id.taskListDesc);
                    ImageView imageView = listView.findViewById(R.id.taskListImage);
                    titleView.setText(task.title);
                    descView.setText(task.description);
                    imageView.setImageURI(Uri.parse(task.imageURI));
                    task.done = false;

                    linearLayout.addView(listView);
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void onNewTaskClicked(View view){
        Log.d("ToDoApp", "onNewTaskClicked");

        Intent taskIntent = new Intent(this, MainActivity.class);
        startActivity(taskIntent);
    }

}


