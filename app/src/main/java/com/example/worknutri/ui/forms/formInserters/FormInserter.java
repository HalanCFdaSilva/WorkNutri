package com.example.worknutri.ui.forms.formInserters;

import android.view.ViewGroup;

public abstract class FormInserter<T> {

    protected final ViewGroup viewGroup;
    protected static int viewGroupIdExpected;

    public FormInserter(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;

    }

    public abstract void insertEntityInViewGroup(T entityToInserter);

    public abstract void insertViewGroupInEntity(T entityToInsert);


    public static int getViewGroupIdExpected() {
        return viewGroupIdExpected;
    }

}
