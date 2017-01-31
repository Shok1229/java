package com.example.shokk.to_do_list;

import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;
    ArrayAdapter mAdapter;
    ListView lstTask;
    Todo todoList = new Todo();
    Todo todoTitre = new Todo();
    String _Task;
    //List<Todo> todoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);
        lstTask = (ListView)findViewById(R.id.lstTask);
        lstTask.setAdapter(mAdapter);
        Todo todoList = new Todo();
        loadTaskList();
    }

    private void loadTaskList() {

        ArrayList<String> taskList = dbHelper.getTaskList();
        //todoList = dbHelper.getTaskList();
        if (mAdapter == null)
        {
            mAdapter = new ArrayAdapter<String>(this,R.layout.row, R.id.task_little, taskList);
            lstTask.setAdapter(mAdapter);
        }
        else
        {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        Drawable icon = menu.getItem(0).getIcon();
        icon.mutate();
        icon.setColorFilter(getResources().getColor(android.R.color.white), PorterDuff.Mode.SRC_IN);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                final View view = LayoutInflater.from(this).inflate(R.layout.add_todo_alert_dialog, null, false);
                // final EditText taskEditText2 = new EditText(this);
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Add a new task");
                dialog.setMessage("What are you going to do next ?");
                dialog.setView(view);
                dialog.setPositiveButton("Add", null);
                final AlertDialog alert1 = dialog.create();
                alert1.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button button = alert1.getButton(AlertDialog.BUTTON_POSITIVE);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                EditText title = (EditText)view.findViewById(R.id.title_edittext);
                                EditText content = (EditText)view.findViewById(R.id.content_edit);
                                EditText date = (EditText)view.findViewById(R.id.date_edit);
                                //Pour fermer l'alertDialog
                                String Title = String.valueOf(title.getText());
                                String Content = String.valueOf(content.getText());
                                String Date = String.valueOf(date.getText());
                                todoList.setTitre(Title);
                                todoList.setContent(Content);
                                todoList.setDate(Date);
                                dbHelper.insertNewtask(todoList);
                                loadTaskList();
                                alert1.dismiss();
                            }
                        });

                    }
                });
                alert1.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void focusTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) findViewById(R.id.task_little);
        String task = String.valueOf(taskTextView.getId());
        _Task = task;
        final EditText taskEditTask = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Modify the name")
                .setMessage("What u gonna do for real homie ?")
                .setView(taskEditTask)
                .setPositiveButton("Modify", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView taskTextView = (TextView) findViewById(R.id.task_little);
                        //String task = String.valueOf(taskTextView.getText());
                        String task2 = String.valueOf(taskEditTask.getText());
                        Todo todoTitre =  dbHelper.getTask(_Task);
                        todoTitre.setTitre(task2);
                        todoTitre.setContent("test");
                        todoTitre.setContent("test2");
                        dbHelper.updateTask(todoTitre, todoTitre.getId());
                        loadTaskList();
                    }
                })
                .setTitle(todoList.getTitre())
                .setNegativeButton("Cancel", null)
                .create();
        dialog.show();
        loadTaskList();
    }


    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) findViewById(R.id.task_little);
        String task = String.valueOf(taskTextView.getText());
        int id = lstTask.getPositionForView(view);
        Log.d("DEBUG : ", task);
        Log.d("ID : ", Integer.toString(id));
        dbHelper.deleteTask(task);
        loadTaskList();
    }

}