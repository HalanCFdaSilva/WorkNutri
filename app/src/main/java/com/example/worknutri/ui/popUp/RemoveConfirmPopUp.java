package com.example.worknutri.ui.popUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.example.worknutri.R;

public class RemoveConfirmPopUp extends PopUpFragment {
    public RemoveConfirmPopUp(LayoutInflater layoutInflater) {
        super(layoutInflater);
        ViewGroup viewGroup = getViewGroup();
        viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout_header).setVisibility(View.GONE);
        generateView(layoutInflater);

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone((ConstraintLayout) viewGroup);
        constraintSet.setMargin(R.id.popup_base_layout_scrollview, ConstraintSet.END, 32);
        constraintSet.setMargin(R.id.popup_base_layout_scrollview, ConstraintSet.START, 32);
        constraintSet.applyTo((ConstraintLayout) viewGroup
        );

    }

    private void generateView(LayoutInflater layoutInflater) {
        ViewGroup viewToInsert = (ViewGroup) layoutInflater.inflate(R.layout.popup_delete_confirm, getViewToInsert());
        Button button = viewToInsert.findViewById(R.id.popup_delete_confirm_layout_cancel_button);
        button.setOnClickListener(onClick -> getPopUpWindow().dismiss());

    }

    public Button getConfirmButton() {
        return getViewGroup().findViewById(R.id.popup_delete_confirm_layout_confirm_button);
    }

}
