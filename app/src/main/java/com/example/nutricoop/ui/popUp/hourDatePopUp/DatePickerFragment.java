package com.example.nutricoop.ui.popUp.hourDatePopUp;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.clinica.domain.DayOfWork;
import com.example.nutricoop.ui.formularios.formularioClinica.GenerateDayOfWork;
import com.example.nutricoop.ui.popUp.PopUpFragment;

import java.util.ArrayList;
import java.util.List;


public class DatePickerFragment  extends PopUpFragment {


    private List<DayOfWork> daysOfWork;
    public DatePickerFragment(ViewGroup viewGroup,List<DayOfWork> dayOfWorkList) {
        super(viewGroup,R.id.date_picker_pop_up_image_view_not_use);
        this.configuraBotoes();
        daysOfWork = dayOfWorkList;


    }


    public void configuraBotoes() {

        final NumberPicker weekPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_week_day);
        final NumberPicker beginPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
        final NumberPicker endPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_end);

        Resources resources = this.getViewGroup().getResources();

        weekPicker.setMinValue(0);
        weekPicker.setMaxValue(6);
        weekPicker.setDisplayedValues(resources.getStringArray(R.array.dias_semana));
        this.getViewGroup().setAlpha(1.0f);



        beginPicker.setMinValue(0);
        beginPicker.setMaxValue(23);
        beginPicker.setDisplayedValues(resources.getStringArray(R.array.hours_total));

        endPicker.setMinValue(0);
        endPicker.setMaxValue(23);
        endPicker.setDisplayedValues(resources.getStringArray(R.array.hours_total));



    }



    public void layoutGenerate(HourDateFragment hourDateFragment, ViewGroup viewGroupToInsert) {
        Button buttonConfirm = this.getViewGroup() .findViewById(R.id.date_picker_pop_up_button_confirm);
        buttonConfirm.setOnClickListener(v -> {


            NumberPicker numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
            String hourStart = numberPicker.getDisplayedValues()[numberPicker.getValue()];
            numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
            String hourEnd = numberPicker.getDisplayedValues()[numberPicker.getValue()];
            if (Integer.parseInt(hourStart.substring(0,2)) < Integer.parseInt(hourEnd.substring(0,2)) ){

                numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_week_day);
                String dayOfWeek = numberPicker.getDisplayedValues()[numberPicker.getValue()];

                hourDateFragment.setDayOfweek(dayOfWeek.toUpperCase());
                hourDateFragment.setHourBegin(hourStart);
                hourDateFragment.setHourEnd(hourEnd);
                newDayOfWork(hourDateFragment);
                hourDateFragment.addLayout(viewGroupToInsert);

                getPopUpWindow().dismiss();
            }else {
                getViewGroup().findViewById(R.id.date_picker_pop_up_textview_error).setVisibility(View.VISIBLE);
            }
        });
    }

    private void newDayOfWork(HourDateFragment hourDateFragment){
        DayOfWork day = GenerateDayOfWork.generateOfTimeDescritionFragment(hourDateFragment.getLayout());
        daysOfWork.add(day);
    }
}