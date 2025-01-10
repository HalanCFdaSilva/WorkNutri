package com.example.nutricoop.ui.popUp.detailsPopUp;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.example.nutricoop.ui.popUp.PopUpFragment;

public class PatologiaDetaillPopUp extends PopUpFragment {
    public PatologiaDetaillPopUp(ViewGroup viewGroup, Patologia patologia) {
        super(viewGroup, R.id.paciente_descrition_patologia_image);
        setText(patologia);
    }

    private void setText(Patologia patologia){
        ViewGroup viewGroup = getViewGroup();

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_patologia_atual)).
                setText(patologia.getPatologiaAtual());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_urina)).
                setText(patologia.getUrina());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_fezes)).
                setText(patologia.getFezes());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_sono)).
                setText(patologia.getHoraSono());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_medicacao)).
                setText(patologia.getMedicacao());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_suplemento)).
                setText(patologia.getSuplemento());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_etilico)).
                setText(patologia.getEtilico());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_fumante)).
                setText(patologia.getFumante());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_alergia)).
                setText(patologia.getAlergiaAlimentar());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_agua)).
                setText(patologia.getConsumoAgua());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_acucar)).
                setText(patologia.getAcucar());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_patologia_atividade)).
                setText(patologia.getAtividadeFisica());




    }
}
