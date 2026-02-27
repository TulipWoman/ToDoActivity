package com.example.todoactivity;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Task {
    @PrimaryKey(autoGenerate = true) //sets this field to be the key
    public int uid;
    @ColumnInfo(name = "title") //sets the name of this filed in the database
    public String title;
    @ColumnInfo(name = "description")
    public String description;
    @ColumnInfo(name = "duedate")
    public Long duedate;


}
