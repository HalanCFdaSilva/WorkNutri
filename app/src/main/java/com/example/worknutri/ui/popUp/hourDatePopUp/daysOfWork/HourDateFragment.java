package com.example.worknutri.ui.popUp.hourDatePopUp.daysOfWork;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

public class HourDateFragment {
    private  ViewGroup layout;

    public void generateNewLayout(LayoutInflater layoutInflater){
        layout = (ViewGroup) layoutInflater.inflate(R.layout.time_descrition_fragment, null, false);
    }

    public void setDayOfweek(String dayOfweek) {
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        textView.setText(dayOfweek);
    }

    public void setHourBegin(String hourBegin) {
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        textView.setText(hourBegin);
    }

    public void setHourEnd(String hourEnd) {
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_hour_end);
        textView.setText(hourEnd);
    }

    public void addLayout(ViewGroup layoutWhereAdd) {
        layoutWhereAdd.addView(layout);
    }

    public ImageButton getTrashButton(){
        return layout.findViewById(R.id.time_descrition_fragment_button_delete);
    }

    public void removeButtonDelete(){
        getTrashButton().setVisibility(View.GONE);
    }

    public ViewGroup getLayout() {
        return layout;
    }

    public void setFromDayOfWork(DayOfWork dayOfWork) {
        setDayOfweek(dayOfWork.getDayOfWeek());
        setHourBegin(dayOfWork.getHoraInicio());
        setHourEnd(dayOfWork.getHoraFim());

    }

}
