package com.example.shokk.to_do_list;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Shokk on 26/01/2017.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "DB";
    private static final int DB_VER = 1;
    public static final String DB_TABLE = "Task";
    public static final int NUM_COL_ID = 0;
    public static final int NUM_COL_TITRE = 1;
    public static final int NUM_COL_CONTENT = 2;
    public static final int NUM_COL_DATE = 3;
    public static final String COL_ID = "ID";
    public static final String DB_COL = "TaskName";
    public static final String DB_COL_CONTENT = "Content";
    public static final String DB_COL_DATE = "Date";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + DB_TABLE + " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DB_COL + " TEXT NOT NULL, " + DB_COL_CONTENT + " TEXT NOT NULL, " + DB_COL_DATE + " TEXT NOT NULL);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE);
        db.execSQL(query);
        onCreate(db);
    }

    public void insertNewtask(Todo todoList) {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues values = new ContentValues();
        //values.put(DB_COL_CONTENT, content);
        values.put(DB_COL, todoList.getTitre());
        values.put(DB_COL_CONTENT, todoList.getContent());
        values.put(DB_COL_DATE, todoList.getDate());
        db.insert(DB_TABLE, null, values);
        db.close();
    }

    public boolean updateTask(Todo todoList, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DB_COL, todoList.getTitre());
        values.put(DB_COL_CONTENT, "TEST");
        values.put(DB_COL_DATE, "test");
        //values.put(DB_COL_CONTENT, todoList.getContent());
        //values.put(DB_COL_DATE, todoList.getDate());
        db.update(DB_TABLE, values, COL_ID + " = " + id, null); //new String[]{task});
        db.close();
        return true;
    }

    public void deleteTask(String task) //Faire attention aux ids
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DB_TABLE, DB_COL + " == ?", new String[]{task});
        db.close();
    }


    public ArrayList<String> getTaskList() //Todo
    {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(DB_TABLE, new String[]{DB_COL}, null, null, null, null, null);
        while (cursor.moveToNext())
        {
            int index = cursor.getColumnIndex(DB_COL);
            taskList.add(cursor.getString(index));

            //tmp.setId(cursor.getInt(cursor.getColumnIndex(DB_COL_ID)));
            //tmp.setTitre(cursor.getString(cursor.getColumnIndex(DB_COL)));
            //§tmp.setContent(cursor.getString(cursor.getColumnIndex(DB_COL_CONTENT)));
            //tmp.setDate(cursor.getString(cursor.getColumnIndex(DB_COL_DATE)));

            //todoList.add(tmp);
            }
              cursor.close();
              db.close();
        return taskList; // par rapport à l'id par rapport au string

    }
    public Todo getTask(String titre) {

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(DB_TABLE, new String[]{COL_ID, DB_COL, DB_COL_CONTENT, DB_COL_DATE},
                DB_COL + " LIKE \"" + titre + "\"", null, null, null, null);
        return cursorToTask(c);
    }

    public Todo cursorToTask(Cursor c) {
        if (c.getCount() == 0)
            return null;
        c.moveToFirst();
        Todo todoList = new Todo();
        todoList.setId(c.getInt(NUM_COL_ID));
        todoList.setTitre(c.getString(NUM_COL_TITRE));
        todoList.setContent(c.getString(NUM_COL_CONTENT));
        todoList.setContent(c.getString(NUM_COL_DATE));
        c.close();
        return todoList;
    }

}
