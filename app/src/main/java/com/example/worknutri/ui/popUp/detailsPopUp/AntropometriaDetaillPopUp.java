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
import com.example.worknutri.calcular.StringWithUnitsFormatter;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.util.StringsUtil;
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.popUp.PopUpFragment;

import java.util.Objects;

public class AntropometriaDetaillPopUp extends PopUpFragment {
    
    private int constraintId;
    private final Context context;
    public AntropometriaDetaillPopUp(Context context) {
        super(LayoutInflater.from(context));
        this.context = context;

    }

    public void generateSmall( Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) getInflater().inflate(R.layout.popup_antropometria_description_formulario, getViewToInsert());
        constraintId = R.id.popup_antropometria_description_small;
        setSmallText(antropometria, viewGroup);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setSmallText(Antropometria antropometria,ViewGroup viewGroup) {
        TextView view = viewGroup.findViewById(R.id.antropometria_small_popup_imc);
        String imcFormatted = StringsUtil.formatDouble(antropometria.getImc());
        ViewsUtil.insertInTextView(view,imcFormatted);
        generateClassificacaoImc(Double.parseDouble(imcFormatted),R.id.antropometria_small_popup_imc);

        view = viewGroup.findViewById(R.id.antropometria_small_popup_taxa_metabolica);
        String stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getTaxaMetabolica());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.antropometria_small_popup_valor_metabolico);
        stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getValorMetabolico());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.antropometria_small_popup_bolso);
        stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getRegraBolso());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.antropometria_small_popup_venta);
        stringWithUnit = StringWithUnitsFormatter.withKcalDia( antropometria.getVenta());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.antropometria_small_popup_agua);
        stringWithUnit = StringWithUnitsFormatter.withMl( antropometria.getAgua());
        ViewsUtil.insertInTextView(view, stringWithUnit);
    }

    public void generateComplete( Antropometria antropometria) {
        ViewGroup viewGroup = (ViewGroup) getInflater().inflate(R.layout.popup_antropometria_description, getViewToInsert());
        constraintId = R.id.popup_antropometria_description;
        setCompleteTextViews(antropometria,viewGroup);
        this.insertTitle(R.string.antropometria_title);
    }

    private void setCompleteTextViews(Antropometria antropometria, ViewGroup viewGroup) {
        insertCircumferenceData(antropometria, viewGroup);
        insertlengthWeigthData(antropometria, viewGroup);
        insertAntropometricData(antropometria, viewGroup);
    }

    private void insertAntropometricData(Antropometria antropometria, ViewGroup viewGroup) {
        String imc = StringsUtil.formatDouble(antropometria.getImc());
        TextView view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_imc);
        ViewsUtil.insertInTextView(view, imc );
        generateClassificacaoImc(Double.parseDouble(imc),R.id.paciente_descrition_antropometria_imc);


        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_taxa_metabolica);
        String stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getTaxaMetabolica());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_valor_metabolico);
        stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getValorMetabolico());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_bolso);
        stringWithUnit = StringWithUnitsFormatter.withKcal( antropometria.getRegraBolso());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_venta);
        stringWithUnit = StringWithUnitsFormatter.withKcalDia( antropometria.getVenta());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_agua);
        stringWithUnit = StringWithUnitsFormatter.withMl( antropometria.getAgua());
        ViewsUtil.insertInTextView(view, stringWithUnit);
    }

    private void insertlengthWeigthData(Antropometria antropometria, ViewGroup viewGroup) {
        TextView view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_altura);
        String stringWithUnit = StringWithUnitsFormatter.withMeters( antropometria.getAltura());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_atual);
        stringWithUnit = StringWithUnitsFormatter.withKg( antropometria.getPeso());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_peso_ideal);
        stringWithUnit = StringWithUnitsFormatter.withKg( antropometria.getPesoIdeal());
        ViewsUtil.insertInTextView(view, stringWithUnit);
    }

    private void insertCircumferenceData(Antropometria antropometria, ViewGroup viewGroup) {
        TextView view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_braco);
        String stringWithUnit = StringWithUnitsFormatter.withCm(antropometria.getCircumferenciaBracoDir());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_coxa);
        stringWithUnit = StringWithUnitsFormatter.withCm( antropometria.getCircumferenciaCoxaDir());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_abdomen);
        stringWithUnit = StringWithUnitsFormatter.withCm( antropometria.getCircumferenciaAbdomen());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_cintura);
        stringWithUnit = StringWithUnitsFormatter.withCm( antropometria.getCircumferenciaCintura());
        ViewsUtil.insertInTextView(view, stringWithUnit);

        view = viewGroup.findViewById(R.id.paciente_descrition_antropometria_circum_quadril);
        stringWithUnit = StringWithUnitsFormatter.withCm( antropometria.getCircumferenciaQuadril());
        ViewsUtil.insertInTextView(view, stringWithUnit);
    }


    private void generateClassificacaoImc(double imc,int idConstraint) {

        ClassificacaoImc classificacaoImc = ClassificacaoImc.tipoImc(imc);
        TextView textView = new TextView(context);
        textView.setContentDescription(context.getText(R.string.imc_type_content));
        textView.setId(R.id.classificacao_imc_textview);
        textView.setText(classificacaoImc != null ? classificacaoImc.toString() : "");
        textView.setTypeface(Typeface.DEFAULT_BOLD);

        textView.setBackgroundResource(R.color.white);
        textView.setBackgroundColor(ContextCompat.getColor(context, Objects.requireNonNull(classificacaoImc).getColor()));

        textView.setPadding(5, 0, 5, 0);
        ConstraintLayout layout = getViewGroup().findViewById(constraintId);
        layout.addView(textView);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(layout);
        constraintSet.connect(textView.getId(), ConstraintSet.START, idConstraint, ConstraintSet.END, 8);
        constraintSet.connect(textView.getId(), ConstraintSet.TOP, idConstraint, ConstraintSet.TOP);
        constraintSet.connect(textView.getId(), ConstraintSet.BOTTOM, idConstraint, ConstraintSet.BOTTOM);
        constraintSet.applyTo(layout);


    }


}
