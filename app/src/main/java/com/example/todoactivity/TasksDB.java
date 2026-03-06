package com.example.todoactivity;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Database(entities = {Task.class}, version = 1)
public abstract class TasksDB extends RoomDatabase {
    public abstract TasksDAO tasksDAO();

    private static final String DB_NAME = "tasks_database_name";
    public static TasksDB db;

    //Return a database instance.
    //If the database instance already exists it return the existing instance.
    //If not then it creates a new instance and returns that.


    public static TasksDB getInstance(Context context) {

        if (db == null) db = buildDatabaseInstance(context);
        return db;
    }

    //Create instance of Database
    private static TasksDB buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context, TasksDB.class, DB_NAME).build();
    }


}

