package com.example.worknutri.ui.popUp.hourDatePopUp.datePicker;

import android.view.ViewGroup;
import android.widget.NumberPicker;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

/**
 * Classe que gera uma instância da classe DayOfWork
 *
 * @see DayOfWork
 */
public class DayOfWorkViewBinder {

    private final DayOfWork dayOfWork;

    public DayOfWorkViewBinder(DayOfWork dayOfWork) {
        this.dayOfWork = dayOfWork;
    }


    /**
     * Método que gera uma instância da classe DayOfWork apartir de uma viewgroup oriunda do arquivo
     * time_descrition_fragment.xml.
     *
     * @param viewGroup instância inflada do layout time_descrition_fragment
     * @see DayOfWork
     */
    public void bind(ViewGroup viewGroup) {

        if (viewGroup != null) {
            NumberPicker numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
            if(numberPicker != null) {
                String hourStart = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                dayOfWork.setHoraInicio(hourStart);
            }

            numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
            if (numberPicker != null) {
                String hourEnd = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                dayOfWork.setHoraFim(hourEnd);
            }

            numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_week_day);
            if (numberPicker != null) {
                String weekDay = numberPicker.getDisplayedValues()[numberPicker.getValue()];
                dayOfWork.setDayOfWeek(weekDay);
            }
        }else
            throw new IllegalArgumentException("ViewGroup não pode ser nulo");

    }

    public DayOfWork getDayOfWork() {
        return dayOfWork;
    }

}
