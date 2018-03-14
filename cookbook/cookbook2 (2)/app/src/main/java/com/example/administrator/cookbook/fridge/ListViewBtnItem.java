package com.example.administrator.cookbook.fridge;

import android.graphics.drawable.Drawable;

/**
 * Created by Interfo on 2017-12-21.
 */

public class ListViewBtnItem {
    private Drawable iconDrawable ;
    private String textStr ;

    public void setIcon(Drawable icon) {
        iconDrawable = icon ;
    }
    public void setText(String text) {
        textStr = text ;
    }

    public Drawable getIcon() {
        return this.iconDrawable ;
    }
    public String getText() {
        return this.textStr ;
    }
}