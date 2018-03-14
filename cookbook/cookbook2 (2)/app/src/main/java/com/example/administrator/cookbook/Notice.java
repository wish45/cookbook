package com.example.administrator.cookbook;

/**
 * Created by newhyodong on 2017-12-20.
 */

public class Notice {
    String Notice;
    String name;
    String date;

    public Notice(String notice, String name, String date) {
        Notice = notice;
        this.name = name;
        this.date = date;
    }

    public String getNotice() {
        return Notice;
    }

    public void setNotice(String notice) {
        Notice = notice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }



}
