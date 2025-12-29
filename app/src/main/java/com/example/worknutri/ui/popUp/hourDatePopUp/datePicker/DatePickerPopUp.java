package com.example.worknutri.ui.popUp.hourDatePopUp.datePicker;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.util.StringsUtil;

import java.util.List;


public class DatePickerPopUp extends PopUpFragment {

    private final LayoutInflater inflater;
    private DayOfWork dayOfWork;



    public DatePickerPopUp(LayoutInflater inflater) {
        super((ViewGroup) inflater.inflate(R.layout.popup_date_picker, null),
                R.id.date_picker_pop_up_image_view_not_use);
        this.inflater = inflater;
        dayOfWork = new DayOfWork();


    }

    public void modifyDay( DayOfWork dayOfWork) {

        generateNewLayout();
        encerrarAoClicarFora();
        setDayOfWork(dayOfWork);
        configuraBotoes();

    }

    public void generateNewLayout(){
        setViewGroup((ViewGroup) inflater.inflate(R.layout.popup_date_picker, null));
    }

    public void encerrarAoClicarFora(){
        encerrarAoClicarFora(R.id.date_picker_pop_up_image_view_not_use);
    }
    public void configuraBotoes() {


        this.configureWeekPicker();
        this.configureBeginHourPicker();
        this.configureEndHourPicker();
    }

    public void setDayOfWork(DayOfWork dayOfWork) {
        if (checkDayOfWork(dayOfWork))
            this.dayOfWork = dayOfWork;
    }

    private boolean checkDayOfWork(DayOfWork dayOfWork) {
        if (dayOfWork.getId() != 0){
            return !dayOfWork.getDayOfWeek().isBlank() &&
                    !dayOfWork.getHoraInicio().isBlank() &&
                    !dayOfWork.getHoraFim().isBlank();
        }
        return true;
    }

    private void configureEndHourPicker() {
        NumberPicker endPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
        endPicker.setMinValue(0);
        endPicker.setMaxValue(23);
        endPicker.setDisplayedValues(getViewGroup().getResources().getStringArray(R.array.hours_total));
        if (!dayOfWork.getHoraFim().isBlank()) endPicker.setValue(StringsUtil.convertHourStringInInt(dayOfWork.getHoraFim()));
    }

    private void configureBeginHourPicker() {
        NumberPicker beginPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
        beginPicker.setMinValue(0);
        beginPicker.setMaxValue(23);
        beginPicker.setDisplayedValues(getViewGroup().getResources().getStringArray(R.array.hours_total));
        if (!dayOfWork.getHoraInicio().isBlank()) beginPicker.setValue(StringsUtil.convertHourStringInInt(dayOfWork.getHoraInicio()));
    }

    private void configureWeekPicker() {
        NumberPicker weekPicker = this.getViewGroup().findViewById(R.id.date_picker_pop_up_number_picker_week_day);
        weekPicker.setMinValue(0);
        weekPicker.setMaxValue(6);
        weekPicker.setDisplayedValues(getViewGroup().getResources().getStringArray(R.array.dias_semana));
        this.getViewGroup().setAlpha(1.0f);
        if (dayOfWork.getDayOfWeek()!= null &&  !dayOfWork.getDayOfWeek().isBlank()) {
            String[] displayedValues = weekPicker.getDisplayedValues();
            String dayOfWeek = dayOfWork.getDayOfWeek();
            for (int i = 0; i < displayedValues.length;i++){
                if (displayedValues[i].equals(dayOfWeek)){
                    weekPicker.setValue(i);
                }
            }
        }
    }


    public boolean DatePickerIsCorrect(List<DayOfWork> dayOfWorkList){
        if (datePickerHourPeriodIsCorrect()){
            if (!collideWithDayOfWorkOfClass(dayOfWorkList)){
                return true;
            }
            setLayoutErrorToDatePicker(R.string.error_date_picker_anotherDay);
        } else
            setLayoutErrorToDatePicker(R.string.error_date_picker_hour_period_not_exist);
        return false;
    }

    private boolean datePickerHourPeriodIsCorrect() {
        int hourStart = getValueOfHourInNumberPicker(R.id.date_picker_pop_up_number_picker_hour_start);
        int hourEnd = getValueOfHourInNumberPicker(R.id.date_picker_pop_up_number_picker_hour_end);
        return hourStart < hourEnd;
    }

    private int getValueOfHourInNumberPicker(int id){
        NumberPicker numberPicker = getViewGroup().findViewById(id);
        String hour = numberPicker.getDisplayedValues()[numberPicker.getValue()];
        return StringsUtil.convertHourStringInInt(hour);
    }

    private boolean collideWithDayOfWorkOfClass(List<DayOfWork> listOfDayToWorkAdded) {
        for (DayOfWork dayOfWorkAdded : listOfDayToWorkAdded)
            if (dayOfWorkAdded.isDaysOfWorkColidde(dayOfWork))
                return true;

        return false;
    }

    public void setLayoutErrorToDatePicker(int errorString) {
        TextView textView = getViewGroup().findViewById(R.id.date_picker_pop_up_textview_error);
        textView.setVisibility(View.VISIBLE);
        textView.setText(getViewGroup().getContext().getString(errorString));
    }

    public void convertToDayOfWork() {
        DayOfWorkViewBinder dayOfWorkViewBinder = new DayOfWorkViewBinder(dayOfWork);
        dayOfWorkViewBinder.bind(getViewGroup());

    }

    public DayOfWork getDayOfWork() {
        return dayOfWork;
    }

    public Button getButtonSave(){
        return this.getViewGroup().findViewById(R.id.date_picker_pop_up_button_confirm);
    }


}