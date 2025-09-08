package com.example.worknutri.ui.popUp.pathology.viewPopUp;



import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;

public class PathologyViewPopUp extends PopUpFragment {

    public PathologyViewPopUp(LayoutInflater inflater) {
        super(inflater);
        this.insertTitle(R.string.patologia_title);


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
        for (PathologyField pathologyType: PathologyField.values()) {
            String description = new PathologyFieldMapper(pathologyType).getValue(patologia);
            ViewGroup viewGenerated = generateView(pathologyType.getName(), description);
            getViewToInsert().addView(viewGenerated);
        }

    }


    /**Método que apartir da classe PatologiaPopUpFragment gera o layout de cada atributo da classe Patologia
     * @see PathologyViewFragmentFactory
     * @see Patologia*/
    public ViewGroup generateView(String title, String description) {
        PathologyViewFragmentFactory popUpFragment = new PathologyViewFragmentFactory(getInflater());
        popUpFragment.generateViewGroup(getViewToInsert());
        popUpFragment.setTitle(title);
        if (description == null || description.isBlank()) popUpFragment.setDescription("Não Informado");
        else popUpFragment.setDescription(description);
        return popUpFragment.getViewGroup();

    }
}
