package com.example.worknutri.ui.popUp.detailsPopUp;


import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.ContextCompat;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.util.StringsUtil;
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.popUp.PopUpFragment;

import java.util.Objects;

public class AntropometriaDetaillPopUp extends PopUpFragment {
    private int constraintId;
    private final Context context;
    public AntropometriaDetaillPopUp(LayoutInflater layoutInflater, Context context) {
        super(layoutInflater);
        this.context = context;

    }

    public void generateSmall( Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) getInflater().inflate(R.layout.popup_antropometria_description_formulario, getViewToInsert());
        constraintId = R.id.popup_antropometria_description_small;
        setSmallText(antropometria, viewGroup);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setSmallText(Antropometria antropometria,ViewGroup viewGroup) {
        TextView view = viewGroup.findViewById(R.id.antropometria_popup_imc);
        ViewsUtil.insertInTextView(view, StringsUtil.formatDouble(antropometria.getImc()));
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

    public void generateComplete( Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) getInflater().inflate(R.layout.popup_antropometria_description, getViewToInsert());
        constraintId = R.id.popup_antropometria_description;
        setText(antropometria,viewGroup);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setText(Antropometria antropometria,ViewGroup viewGroup) {
        insertCircumferenceData(antropometria, viewGroup);
        insertlengthWeigthData(antropometria, viewGroup);
        insertAntropometricData(antropometria, viewGroup);
    }

    private void insertAntropometricData(Antropometria antropometria, ViewGroup viewGroup) {
        String imc = StringsUtil.formatDouble(antropometria.getImc());
        TextView view = viewGroup.findViewById(R.id.antropometria_popup_imc);
        ViewsUtil.insertInTextView(view,imc );
        generateClassificacaoImc(Double.parseDouble(imc), context);
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

    private void insertlengthWeigthData(Antropometria antropometria, ViewGroup viewGroup) {
        TextView view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_altura);
        insertWithM(view, antropometria.getAltura());

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_atual);
        insertWithKg(view, antropometria.getPeso());
        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_ideal);
        insertWithKg(view, antropometria.getPesoIdeal());
    }

    private void insertCircumferenceData(Antropometria antropometria, ViewGroup viewGroup) {
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
    }

    private void insertWithKcal(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" Kcal"));
    }

    private void insertWithCm(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" cm"));
    }

    private void insertWithKg(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" kg"));
    }

    private void insertWithM(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" m"));
    }

    private void insertWithMl(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" mlm"));
    }

    private void insertWithKcalDia(TextView textView, String string) {
        ViewsUtil.insertInTextView(textView, StringsUtil.formatDouble(string).concat(" Kcal/Dia"));
    }


    private void generateClassificacaoImc(double imc, Context context) {

        ClassificacaoImc classificacaoImc = ClassificacaoImc.tipoImc(imc);
        TextView textView = new TextView(context);
        textView.setContentDescription(context.getText(R.string.imc_type_content));
        textView.setId(R.id.classificacao_imc_textview);
        textView.setText(classificacaoImc != null ? classificacaoImc.toString().replaceAll("_", " ") : "");
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        textView.setBackgroundResource(R.color.white);
        textView.setBackgroundColor(ContextCompat.getColor(context, Objects.requireNonNull(classificacaoImc).getColor()));

        textView.setPadding(5, 0, 5, 0);
        ConstraintLayout layout = getViewGroup().findViewById(constraintId);
        layout.addView(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(textView.getId(), ConstraintSet.START, R.id.antropometria_popup_imc, ConstraintSet.END, 8);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, R.id.antropometria_popup_imc, ConstraintSet.TOP);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, R.id.antropometria_popup_imc, ConstraintSet.BOTTOM);
        constraintSet.applyTo(layout);


    }


}
