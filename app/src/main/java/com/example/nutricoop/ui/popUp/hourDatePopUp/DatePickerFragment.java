package com.example.nutricoop.ui.popUp.hourDatePopUp;


import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import com.example.nutricoop.R;
import com.example.nutricoop.ui.popUp.PopUpFragment;


public class DatePickerFragment  extends PopUpFragment {

    public DatePickerFragment(ViewGroup viewGroup) {
        super(viewGroup,R.id.date_picker_pop_up_image_view_not_use);
        this.configuraBotoes();

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
        Context context = this.getViewGroup().getContext();
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                NumberPicker numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_week_day);
                String valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];


                hourDateFragment.setDayOfweek(valuePicked.toUpperCase());

                numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
                valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                hourDateFragment.setHourBegin(valuePicked);

                numberPicker = getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
                valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];




                hourDateFragment.setHourEnd(valuePicked);
                hourDateFragment.addLayout(viewGroupToInsert);
                getPopUpWindow().dismiss();

            }
        });

    }
}