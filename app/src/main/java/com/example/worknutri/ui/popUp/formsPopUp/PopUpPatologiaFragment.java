package com.example.worknutri.ui.popUp.formsPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.worknutri.R;

public class PopUpPatologiaFragment {

    private final Context context;
    protected PopUpPatologiaFragment( Context context) {
        this.context = context;
    }

    public ViewGroup generateViewGroup(PathologyCategory category, String message) {


        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.popup_patologia_description_formulario, null);

        TextView title = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_title);
        title.setText(category.getName());

        EditText editText = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        editText.setHint(category.getHint());
        editText.setText(message);

        return viewGroup;
    }


}
