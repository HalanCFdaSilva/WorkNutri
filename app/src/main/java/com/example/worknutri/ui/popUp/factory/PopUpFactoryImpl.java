package com.example.worknutri.ui.popUp.factory;

import android.content.Context;
import android.view.LayoutInflater;


import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.anthropometry.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.anthropometry.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.pathology.viewPopUp.PathologyViewPopUp;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.addPopUp.AddPathologyPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

import java.util.List;

public class PopUpFactoryImpl implements PopUpFactory{

    private final Context context;
    public PopUpFactoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public PathologyViewPopUp generatePatologiaDetailPopUp() {
        return new PathologyViewPopUp(LayoutInflater.from(context));
    }

    @Override
    public AntropometriaDetaillPopUp generateAntropometriaPopUp() {
        return new AntropometriaDetaillPopUp(context);
    }


    @Override
    public RemoveConfirmPopUp generateRemoveConfirmPopUp() {
        return new RemoveConfirmPopUp(LayoutInflater.from(context));
    }

    @Override
    public DatePickerPopUp generateDatePickerPopUp() {
        return new DatePickerPopUp(LayoutInflater.from(context));
    }

    @Override
    public AddPathologyPopUp generateAddPathologyPopUp(List<PathologyField> pathologyCategories) {
        return new AddPathologyPopUp(context, pathologyCategories);
    }

    @Override
    public ActivityLevelDetailPopUp generateActivityLevelDetailPopUp() {
        return new ActivityLevelDetailPopUp(context);
    }
}
