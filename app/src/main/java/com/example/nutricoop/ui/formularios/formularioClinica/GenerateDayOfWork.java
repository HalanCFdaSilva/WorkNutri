package com.example.nutricoop.ui.formularios.formularioClinica;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.clinica.domain.Clinica;
import com.example.nutricoop.sqlLite.clinica.domain.DayOfWork;

/**Classe que gera uma instância da classe DayOfWork
 * @see DayOfWork*/
public class GenerateDayOfWork {

    /**Método que gera uma instância da classe DayOfWork apartir de uma viewgroup oriunda do arquivo
     * time_descrition_fragment.xml.
     * @param viewGroup instância inflada do layout time_descrition_fragment
     * @see DayOfWork*/
    public static DayOfWork generateOfTimeDescritionFragment( ViewGroup viewGroup){
        DayOfWork dayOfWork = new DayOfWork();



        TextView textView = viewGroup.findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        dayOfWork.setDayOfWeek(textView.getText().toString());


        textView = viewGroup.findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        dayOfWork.setHoraInicio(textView.getText().toString());


        textView = viewGroup.findViewById(R.id.time_descrition_fragment_textView_hour_end);
        dayOfWork.setHoraFim(textView.getText().toString());


        return dayOfWork;


    }
}
