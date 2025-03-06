package com.example.worknutri.ui.popUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.worknutri.R;

public class RemoveConfirmPopUp extends PopUpFragment{
    public RemoveConfirmPopUp(LayoutInflater layoutInflater) {
        super(layoutInflater);
        ViewGroup viewGroup = getViewGroup();
        viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout_header).setVisibility(View.GONE);
        ViewGroup viewToInsert = generateView(layoutInflater);
        insertView(viewToInsert);

    }

    private ViewGroup generateView(LayoutInflater layoutInflater) {
        ViewGroup viewToInsert = (ViewGroup) layoutInflater.inflate(R.layout.popup_delete_confirm,null);
        Button button = viewToInsert.findViewById(R.id.popup_delete_confirm_layout_cancel_button);
        button.setOnClickListener(onClick -> {
            getPopUpWindow().dismiss();
        });
        return viewToInsert;
    }

    public Button getConfirmButton(){
        return getViewGroup().findViewById(R.id.popup_delete_confirm_layout_confirm_button);
    }

}
