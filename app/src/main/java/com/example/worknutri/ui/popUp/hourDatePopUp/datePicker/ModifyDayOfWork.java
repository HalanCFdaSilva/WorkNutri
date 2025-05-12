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
public class ModifyDayOfWork {

    private final DayOfWork dayOfWork;

    public ModifyDayOfWork(DayOfWork dayOfWork) {
        this.dayOfWork = dayOfWork;
    }


    /**
     * Método que gera uma instância da classe DayOfWork apartir de uma viewgroup oriunda do arquivo
     * time_descrition_fragment.xml.
     *
     * @param viewGroup instância inflada do layout time_descrition_fragment
     * @see DayOfWork
     */
    public void generateOfTimeDescritionFragment(ViewGroup viewGroup) {

        NumberPicker numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_hour_start);
        String hourStart = numberPicker.getDisplayedValues()[numberPicker.getValue()];
        dayOfWork.setHoraInicio(hourStart);

        numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_hour_end);
        String hourEnd = numberPicker.getDisplayedValues()[numberPicker.getValue()];
        dayOfWork.setHoraFim(hourEnd);

        numberPicker = viewGroup.findViewById(R.id.date_picker_pop_up_number_picker_week_day);
        String dayOfWeek = numberPicker.getDisplayedValues()[numberPicker.getValue()];
        dayOfWork.setDayOfWeek(dayOfWeek);


    }

    public DayOfWork getDayOfWork() {
        return dayOfWork;
    }

}
