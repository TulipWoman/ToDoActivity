package com.example.todoactivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button saveButton = findViewById(R.id.saveTaskButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("ToDoActivity", "onSaveClick");

            }
        });


    }

    public void onSaveClick(View view) {

        Log.d("ToDoActivity", "onSaveClick");
        //Section 1 - week 2
//        EditText titleView = findViewById (R.id.taskTitleView);
//        EditText descView = findViewById (R.id.taskDescriptionView);
//        EditText dateView = findViewById (R.id.taskDueDateView);
//        EditText timeView = findViewById (R.id.taskDueTimeView);
//        String title = titleView.getText().toString();
//        String desc = descView.getText().toString();
//        String date = dateView.getText().toString();
//        String time = timeView.getText().toString();
//
//        // Section 2 - week 3 preferences
//        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//        String storedTitle = sharedPref.getString("title", "None");
//        editor.putString("title", title);
//        editor.putString("desc", desc);
//        editor.putString("date", date);
//        editor.putString("time", time);
//        editor.apply();


        //create an instance of the Database
        TasksDB db = TasksDB.getInstance(this);
        //create a new Task
        final Task task1 = new Task();
        task1.title = "test_title";
        task1.description = "a very meaningful description";

        //This wont work.
        //db.tasksDAO().insert(task1);

        //We have to create a separate thread to insert data into the database
        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new

                                   Runnable() {
                                       @Override
                                       public void run() {
                                           //add the task to the database
                                           db.tasksDAO().insert(task1);
                                       }
                                   });
    }

    public void onDateClick(View view) {
        Log.d("ToDoActivity", "onDateClick");

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            //handler for the date set event
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //get a reference to the Date View from the layout by its id
                EditText dateView = findViewById(R.id.taskDueDateView);
                //change the date displayed in the date view.
                dateView.setText(dayOfMonth + "/" + month + "/" + year);

            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, listener, 2020, 1, 1);
        dialog.show();//display the dialog
    }

    //Time
    public void onTimeClick(View view) {
        Log.d("ToDoActivity", "onTimeClick");

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //get a reference to the Time View from the layout by its id
                EditText timeView = findViewById(R.id.taskDueTimeView);
                //change the time displayed in the time view.
                timeView.setText(hourOfDay + "/" + minute);
            }

        };

        TimePickerDialog dialog = new TimePickerDialog(this, listener, 1111, 1, true);
        dialog.show();//display the dialog
    }


}