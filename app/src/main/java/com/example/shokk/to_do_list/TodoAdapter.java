package com.example.shokk.to_do_list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import java.util.List;

/**
 * Created by Shokk on 29/01/2017.
 */

public class TodoAdapter extends BaseAdapter {

    private List<Todo> _todoList;
    private Context _context;
    private LayoutInflater _layoutInflater;

    TodoAdapter(Context context, List<Todo> todoList)
    {
        _todoList = todoList;
        _context = context;
        _layoutInflater = LayoutInflater.from(_context);
    }

    @Override
    public int getCount() {
        return _todoList.size();
    }

    @Override
    public Object getItem(int position) {
        return _todoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return (position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*
            Verifier converView est instancié, en gros !=  null

            si oui uniquement le récupérer
            si non l'instancier dans une variable

            Ensuite remplir la vue avec les info souhaités

            EditText_title.setText("titre de ma todo");

            //retournée la vue*/

        View layout;

        if (convertView != null)
            layout = convertView;
        else
            layout = LayoutInflater.from(_context).inflate(R.layout.add_todo_alert_dialog, null, false);

        EditText title = (EditText)layout.findViewById(R.id.title_edittext);
        EditText content = (EditText)layout.findViewById(R.id.content_edit);
        EditText date = (EditText)layout.findViewById(R.id.date_edit);
        // Récuperer les EditText;

        //Inserer les textes dans les EditText

        //Retourner la vue

        return layout;
      }
}
