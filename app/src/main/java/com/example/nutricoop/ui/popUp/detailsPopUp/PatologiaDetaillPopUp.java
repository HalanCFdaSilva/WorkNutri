package com.example.nutricoop.ui.popUp.detailsPopUp;

import static com.example.nutricoop.R.*;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.example.nutricoop.ui.InsertSelectViewSupport;
import com.example.nutricoop.ui.popUp.PopUpFragment;

public class PatologiaDetaillPopUp extends PopUpFragment {
    public PatologiaDetaillPopUp(LayoutInflater inflater, Patologia patologia) {
        super(inflater);
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(layout.paciente_descrition_patologia,null);
        this.insertView(viewGroup);
        setText(patologia);
        this.insertTitle(R.string.patologia_title);
    }

    private void setText(Patologia patologia){
        ViewGroup viewGroup = getViewGroup();


        TextView view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_patologia_atual);
        InsertSelectViewSupport.insertInTextView(view,patologia.getPatologiaAtual());

        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_urina);
        InsertSelectViewSupport.insertInTextView(view,patologia.getUrina());

        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_fezes);
        InsertSelectViewSupport.insertInTextView(view,patologia.getFezes());

        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_sono);
        InsertSelectViewSupport.insertInTextView(view,patologia.getHoraSono());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_medicacao);
        InsertSelectViewSupport.insertInTextView(view,patologia.getMedicacao());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_suplemento);
        InsertSelectViewSupport.insertInTextView(view,patologia.getSuplemento());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_etilico);
        InsertSelectViewSupport.insertInTextView(view,patologia.getEtilico());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_fumante);
        InsertSelectViewSupport.insertInTextView(view,patologia.getFumante());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_alergia);
        InsertSelectViewSupport.insertInTextView(view,patologia.getAlergiaAlimentar());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_agua);
        InsertSelectViewSupport.insertInTextView(view,patologia.getConsumoAgua());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_acucar);
        InsertSelectViewSupport.insertInTextView(view,patologia.getAcucar());

        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_patologia_atividade);
        InsertSelectViewSupport.insertInTextView(view,patologia.getAtividadeFisica());


    }
}
