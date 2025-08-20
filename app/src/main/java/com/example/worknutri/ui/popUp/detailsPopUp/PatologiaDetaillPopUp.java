package com.example.worknutri.ui.popUp.detailsPopUp;


import android.view.LayoutInflater;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.PopUpFragment;

public class PatologiaDetaillPopUp extends PopUpFragment {
    private final LayoutInflater inflater;

    public PatologiaDetaillPopUp(LayoutInflater inflater) {
        super(inflater);
        this.insertTitle(R.string.patologia_title);
        this.inflater = inflater;

    }

    /**Método que remove a margem do bottom do layout base.*/
    public void removeMarginBottom() {
        ConstraintSet constraintSet = new ConstraintSet();
        ConstraintLayout constraintLayout = getViewGroup().findViewById(R.id.popup_base_layout_scrollview_layout);
        constraintSet.clone(constraintLayout);
        constraintSet.setMargin(R.id.popup_base_layout_layout_intern, ConstraintSet.BOTTOM, 0);
        constraintSet.applyTo(constraintLayout);
    }

    public void setText(Patologia patologia) {
        generateView("PATOLOGIA ATUAL", patologia.getPatologiaAtual());
        generateView("URINA", patologia.getUrina());
        generateView("fezes", patologia.getFezes());
        generateView("Hora de Sono", patologia.getHoraSono());
        generateView("medicação", patologia.getMedicacao());
        generateView("suplemento", patologia.getSuplemento());
        generateView("etilico", patologia.getEtilico());
        generateView("fumante", patologia.getFumante());
        generateView("alergia alimentar", patologia.getAlergiaAlimentar());
        generateView("suplemento", patologia.getSuplemento());
        generateView("Consumo de água", patologia.getConsumoAgua());
        generateView("Consumo de Açucar", patologia.getAcucar());
        generateView("Atividade Física", patologia.getAtividadeFisica());

    }


    /**Método que apartir da classe PatologiaPopUpFragment gera o layout de cada atributo da classe Patologia
     * @see PatologiaPopUpFragment
     * @see Patologia*/
    private void generateView(String title, String description) {
        PatologiaPopUpFragment popUpFragment = new PatologiaPopUpFragment(inflater);
        popUpFragment.generateViewGroup(getViewToInsert());
        popUpFragment.setTitle(title);
        if (description == null || description.isBlank()) popUpFragment.setDescription("Não Informado");
        else popUpFragment.setDescription(description);
    }
}
