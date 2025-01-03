package com.example.nutricoop.ui.addClinica;


import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;

import com.example.nutricoop.R;


public class DatePickerFragment  {
    private final PopupWindow pw;
    
    private final  ViewGroup viewGroupToCreatePopUpWindow;
    private int width = LinearLayout.LayoutParams.MATCH_PARENT;
    private int heigth = LinearLayout.LayoutParams.MATCH_PARENT;

    public DatePickerFragment(ViewGroup viewGroup) {
        this.pw = new PopupWindow(viewGroup,
                width,heigth, true);
        this.viewGroupToCreatePopUpWindow = viewGroup;
        this.configuraBotoes();
        this.encerrarAoClicarFora();
    }

    public PopupWindow getPopUpWindow(){

        return pw;

    }
    public void configuraBotoes() {

        final NumberPicker weekPicker = this.viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_week_day);
        final NumberPicker beginPicker = this.viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
        final NumberPicker endPicker = this.viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_hour_end);

        Resources resources = this.viewGroupToCreatePopUpWindow.getResources();

        weekPicker.setMinValue(0);
        weekPicker.setMaxValue(6);
        weekPicker.setDisplayedValues(resources.getStringArray(R.array.dias_semana));
        this.viewGroupToCreatePopUpWindow.setAlpha(1.0f);



        beginPicker.setMinValue(0);
        beginPicker.setMaxValue(23);
        beginPicker.setDisplayedValues(resources.getStringArray(R.array.hours_total));

        endPicker.setMinValue(0);
        endPicker.setMaxValue(23);
        endPicker.setDisplayedValues(resources.getStringArray(R.array.hours_total));



    }


    private void encerrarAoClicarFora(){
        ImageView imageViewBackground = this.viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_image_view_not_use);
        imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pw.dismiss();
            }
        });
    }

    public void layoutGenerate( HourDateFragment hourDateFragment,ViewGroup viewGroupToInsert) {
        Button buttonConfirm = this.viewGroupToCreatePopUpWindow .findViewById(R.id.date_picker_pop_up_button_confirm);
        Context context = this.viewGroupToCreatePopUpWindow.getContext();
        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                NumberPicker numberPicker = viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_week_day);
                String valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];


                hourDateFragment.setDayOfweek(valuePicked.toUpperCase());

                numberPicker = viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
                valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                hourDateFragment.setHourBegin(valuePicked);

                numberPicker = viewGroupToCreatePopUpWindow.findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
                valuePicked = numberPicker.getDisplayedValues()[numberPicker.getValue()];




                hourDateFragment.setHourEnd(valuePicked);
                hourDateFragment.addLayout(viewGroupToInsert);
                pw.dismiss();

            }
        });

    }
}