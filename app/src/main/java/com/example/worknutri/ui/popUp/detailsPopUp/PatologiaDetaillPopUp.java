package com.example.worknutri.ui.popUp.detailsPopUp;

import static com.example.worknutri.R.layout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.popUp.PopUpFragment;

public class PatologiaDetaillPopUp extends PopUpFragment {
    private LayoutInflater inflater;
    public PatologiaDetaillPopUp(LayoutInflater inflater, Patologia patologia) {
        super(inflater);
        this.insertTitle(R.string.patologia_title);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) getViewGroup());
        constraintSet.setMargin(R.id.popup_base_layout_layout_intern,ConstraintSet.BOTTOM,0);
        constraintSet.applyTo((ConstraintLayout) getViewGroup());
        this.inflater = inflater;
        setText(patologia);
    }

    private void setText(Patologia patologia) {
        insertView(generateView("PATOLOGIA ATUAL",patologia.getPatologiaAtual()));
        insertView(generateView("URINA",patologia.getUrina()));
        insertView(generateView("fezes",patologia.getFezes()));
        insertView(generateView("Hora de Sono",patologia.getHoraSono()));
        insertView(generateView("medicação",patologia.getMedicacao()));
        insertView(generateView("suplemento",patologia.getSuplemento()));
        insertView(generateView("etilico",patologia.getEtilico()));
        insertView(generateView("fumante",patologia.getFumante()));
        insertView(generateView("alergia alimentar",patologia.getAlergiaAlimentar()));
        insertView(generateView("suplemento",patologia.getSuplemento()));
        insertView(generateView("Consumo de água",patologia.getConsumoAgua()));
        insertView(generateView("Consumo de Açucar",patologia.getAcucar()));
        insertView(generateView("Atividade Física",patologia.getAtividadeFisica()));

    }

    private ViewGroup generateView(String title, String description){
        PatologiaPopUpFragment popUpFragment = new PatologiaPopUpFragment(inflater);
        popUpFragment.setTitle(title);
        if (description.isBlank()) popUpFragment.setDescription("Não Informado");
        else popUpFragment.setDescription(description);
        return popUpFragment.getViewGroup();
    }
}
