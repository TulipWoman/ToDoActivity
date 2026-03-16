package com.example.todoactivity;

import static com.example.todoactivity.TasksDB.db;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
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
        db = TasksDB.getInstance(this);

    }
//        String filename = "myFile.txt";
//        String contents = "Here's some text";
//
//        File file = new File(getFilesDir(), filename);


//        try{
//            FileOutputStream fos = new FileOutputStream(file);
//            OutputStreamWriter osw = new OutputStreamWriter(fos);
//            osw.write(contents);
//            osw.close();
//        }
//        catch (FileNotFoundException e) { e.printStackTrace(); }
//        catch (IOException e) { e.printStackTrace(); }
//
//        try{
//            FileInputStream fis = new FileInputStream(file);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader bufferedReader = new BufferedReader(isr);
//            String content = bufferedReader.readLine();
//            Log.d("ToDoAPP", content)
// }
//        catch (FileNotFoundException e) { e.printStackTrace(); }
//        catch (IOException e ) { e.printStackTrace(); }

//        Button saveButton = findViewById(R.id.saveTaskButton);
//
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.d("ToDoActivity", "onSaveClick");
//            }
//        });



    public void onSaveClick(View view) {

//            EditText titleView = findViewById(R.id.taskTitleView);
//            EditText descView = findViewById(R.id.taskDescriptionView);
//            EditText dateView = findViewById(R.id.taskDueDateView);
//            EditText timeView = findViewById(R.id.taskDueTimeView);
//            String title = titleView.getText().toString();

        TasksDB db = TasksDB.getInstance(this);
        Log.d("ToDoActivity", "onSaveClick");

        final Task task1 = new Task();
        task1.title = "test_title";
        task1.description = "a very meaningful description";

        Executor myExecutor = Executors.newSingleThreadExecutor();
        myExecutor.execute(new Runnable() {
            @Override
            public void run() {
                db.tasksDAO().insert(task1);
            }
        });

//        finish();
    }

    public void onDateClick(View view) {
        Log.d("ToDoActivity", "onDateClick");

        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                EditText dateView = findViewById(R.id.taskDueDateView);

                dateView.setText(dayOfMonth + "/" + month + "/" + year);
            }
        };

        DatePickerDialog dialog = new DatePickerDialog(this, listener, 2020, 1, 1);
        dialog.show();
    }

    public void onTimeClick(View view) {
        Log.d("ToDoActivity", "onTimeClick");

        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                EditText timeView = findViewById(R.id.taskDueTimeView);

                timeView.setText(hourOfDay + "/" + minute);
            }
        };

        TimePickerDialog dialog = new TimePickerDialog(this, listener, 11, 1, true);
        dialog.show();
    }
    public void onCameraClick (View view){
        String imageFileName = "JPEG_" + System.currentTimeMillis()+".jpg";
        File imageFile= new File(getFilesDir(), imageFileName);
        Log.d("ToDoApp", "onCameraClick");

    }
}