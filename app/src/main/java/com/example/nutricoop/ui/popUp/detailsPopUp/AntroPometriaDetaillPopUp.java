package com.example.nutricoop.ui.popUp.detailsPopUp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.ui.InsertSelectViewSupport;
import com.example.nutricoop.ui.popUp.PopUpFragment;

public class AntroPometriaDetaillPopUp extends PopUpFragment {
    public AntroPometriaDetaillPopUp(LayoutInflater layoutInflater, Antropometria antropometria,boolean isComplete) {
        super( layoutInflater);
        if (isComplete) generateComplete(layoutInflater,antropometria);
        else generateSmall(layoutInflater,antropometria);
    }

    private void generateSmall(LayoutInflater layoutInflater, Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.antropometria_small_popup,null);
        this.insertView(viewGroup);
        setSmallText(antropometria);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setSmallText(Antropometria antropometria) {
        ViewGroup viewGroup = getViewGroup();
        TextView view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_imc);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getImc());
        view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_taxa_metabolica);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getTaxaMetabolica());
        view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_valor_metabolico);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getValorMetabolico());
        view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_bolso);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getRegraBolso());
        view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_venta);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getVenta());
        view = (TextView) viewGroup.findViewById(R.id.antropometria_small_popup_agua);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getAgua());

    }

    private void generateComplete(LayoutInflater layoutInflater, Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.paciente_descrition_antropometria,null);
        this.insertView(viewGroup);
        setText(antropometria);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setText(Antropometria antropometria){
        ViewGroup viewGroup = getViewGroup();

        TextView view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_braco);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getCircumferenciaBracoDir());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_coxa);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getCircumferenciaCoxaDir());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_abdomen);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getCircumferenciaAbdomen());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_cintura);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getCircumferenciaCintura());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_quadril);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getCircumferenciaQuadril());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_altura);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getAltura());

        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_atual);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getPeso());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_ideal);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getPesoDesejado());


        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_imc);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getImc());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_taxa_metabolica);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getTaxaMetabolica());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_valor_metabolico);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getValorMetabolico());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_bolso);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getRegraBolso());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_venta);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getVenta());
        view = (TextView) viewGroup.findViewById(R.id.paciente_descrition_antropometria_agua);
        InsertSelectViewSupport.insertInTextView(view,antropometria.getAgua());

    }


}
