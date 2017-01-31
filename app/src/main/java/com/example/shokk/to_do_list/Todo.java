package com.example.shokk.to_do_list;

/**
 * Created by Shokk on 29/01/2017.
 */

public class Todo {
    private String _titre;
    private String _content;
    private String _date;
    private int _id;

    public void setTitre(String title)
    {
        this._titre = title;
    }
    public String getTitre()
    {
        return this._titre;
    }
    public void setContent(String content)
    {
        this._content = content;
    }
    public String getContent()
    {
        return this._content;
    }
    public void setDate(String date)
    {
        this._date = date;
    }
    public String getDate()
    {
        return this._date;
    }
    public void setId(int id){this._id = id;}
    public int getId(){return this._id;}
    public  String toString() {
        return "ID : " + _id + "\nTITRE : " + _titre + "\n Content : " + _content + "\nDate : " + _date;
    }

}

