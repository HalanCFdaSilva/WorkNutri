package com.example.worknutri.ui.popUp.hourDatePopUp.datePicker;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;


public class PickerDayOfWorkGenerate {
    private final LayoutInflater inflater;

    private DatePickerPopUp datePickerPopUp;
    private final ViewGroup viewGroupOfActivity;

    public PickerDayOfWorkGenerate(ViewGroup viewGroupOfActivity){
        Context context = viewGroupOfActivity.getContext();
        this.inflater = ((Activity)context).getLayoutInflater();
        this.viewGroupOfActivity = viewGroupOfActivity;
        datePickerPopUp = new PopUpFactoryImpl(inflater).generateDatePickerPopUp();


    }

    public void modifyDay( DayOfWork dayOfWork) {

        datePickerPopUp.newLayout(inflater);
        datePickerPopUp.encerrarAoClicarFora();
        datePickerPopUp.configuraBotoes(dayOfWork);
        datePickerPopUp.getPopUpWindow().showAtLocation(viewGroupOfActivity, Gravity.CENTER, -1, -1);

    }



    public DatePickerPopUp getDatePickerPopUp() {
        return datePickerPopUp;
    }

    public ViewGroup getViewGroupOfActivity() {
        return viewGroupOfActivity;
    }
}
