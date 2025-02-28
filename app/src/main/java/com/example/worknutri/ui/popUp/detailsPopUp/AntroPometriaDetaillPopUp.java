package com.example.worknutri.ui.popUp.detailsPopUp;



import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.TextInViewSupport;
import com.example.worknutri.ui.popUp.PopUpFragment;

public class AntroPometriaDetaillPopUp extends PopUpFragment {
    public AntroPometriaDetaillPopUp(LayoutInflater layoutInflater, Antropometria antropometria, boolean isComplete) {
        super(layoutInflater);
        if (isComplete) generateComplete(layoutInflater, antropometria);
        else generateSmall(layoutInflater, antropometria);
    }

    private void generateSmall(LayoutInflater layoutInflater, Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.popup_antropometria_description_small, null);
        this.insertView(viewGroup);
        setSmallText(antropometria);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setSmallText(Antropometria antropometria) {
        ViewGroup viewGroup = getViewGroup();
        TextView view = viewGroup.findViewById(R.id.antropometria_small_popup_imc);
        InsertSelectViewSupport.insertInTextView(view, TextInViewSupport.formatDouble(antropometria.getImc()));
        view = viewGroup.findViewById(R.id.antropometria_small_popup_taxa_metabolica);
        insertWithKcal(view, antropometria.getTaxaMetabolica());
        view = viewGroup.findViewById(R.id.antropometria_small_popup_valor_metabolico);
        insertWithKcal(view, antropometria.getValorMetabolico());
        view = viewGroup.findViewById(R.id.antropometria_small_popup_bolso);
        insertWithKcal(view, antropometria.getRegraBolso());
        view = viewGroup.findViewById(R.id.antropometria_small_popup_venta);
        this.insertWithKcalDia(view, antropometria.getVenta());
        view = viewGroup.findViewById(R.id.antropometria_small_popup_agua);
        insertWithMl(view, antropometria.getAgua());
    }

    private void generateComplete(LayoutInflater layoutInflater, Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.popup_antropometria_description, null);
        this.insertView(viewGroup);
        setText(antropometria);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setText(Antropometria antropometria) {
        ViewGroup viewGroup = getViewGroup();

        TextView view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_braco);
        insertWithCm(view, antropometria.getCircumferenciaBracoDir());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_coxa);
        insertWithCm(view, antropometria.getCircumferenciaCoxaDir());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_abdomen);
        insertWithCm(view, antropometria.getCircumferenciaAbdomen());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_cintura);
        insertWithCm(view, antropometria.getCircumferenciaCintura());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_quadril);
        insertWithCm(view, antropometria.getCircumferenciaQuadril());


        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_altura);
        insertWithM(view, antropometria.getAltura());

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_atual);
        insertWithKg(view, antropometria.getPeso());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_ideal);
        insertWithKg(view, antropometria.getPesoIdeal());


        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_imc);
        InsertSelectViewSupport.insertInTextView(view, TextInViewSupport.formatDouble(antropometria.getImc()));
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_taxa_metabolica);
        insertWithKcal(view, antropometria.getTaxaMetabolica());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_valor_metabolico);
        insertWithKcal(view, antropometria.getValorMetabolico());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_bolso);
        insertWithKcal(view, antropometria.getRegraBolso());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_venta);
        insertWithKcalDia(view, antropometria.getVenta());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_agua);
        insertWithMl(view, antropometria.getAgua());
    }

    private void insertWithKcal(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" Kcal"));
    }

    private void insertWithCm(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" cm"));
    }

    private void insertWithKg(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" kg"));
    }

    private void insertWithM(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" m"));
    }

    private void insertWithMl(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" mlm"));
    }

    private void insertWithKcalDia(TextView textView, String string) {
        InsertSelectViewSupport.insertInTextView(textView, TextInViewSupport.formatDouble(string).concat(" Kcal/Dia"));
    }




}
