package com.example.nutricoop.ui.popUp.detailsPopUp;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.ui.popUp.PopUpFragment;

public class AntroPometriaDetaillPopUp extends PopUpFragment {
    public AntroPometriaDetaillPopUp(ViewGroup viewGroup, Antropometria antropometria) {
        super(viewGroup, R.id.paciente_descrition_antropometria_image);
        setText(antropometria);
    }

    private void setText(Antropometria antropometria){
        ViewGroup viewGroup = getViewGroup();
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_braco)).
                setText(antropometria.getCircumferenceBracoDir());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_coxa)).
                setText(antropometria.getCircumferenceCoxaDir());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_abdomen)).
                setText(antropometria.getCircumferenciaAbdomen());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_cintura)).
                setText(antropometria.getCircumferenciaCintura());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_quadril)).
                setText(antropometria.getCircumferenciaQuadril());


        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_altura)).setText(antropometria.getAltura());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_atual)).setText(antropometria.getPeso());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_ideal)).setText(antropometria.getPesoDesejado());

        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_imc)).setText(antropometria.getImc());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_taxa_metabolica)).setText(antropometria.getTaxaMetabolica());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_valor_metabolico)).setText(antropometria.getValorMetabolico());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_bolso)).setText(antropometria.getRegraBolso());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_venta)).setText(antropometria.getVenta());
        ((TextView)viewGroup.findViewById(R.id.paciente_descrition_antropometria_agua)).setText(antropometria.getAgua());

    }
}
