package com.example.worknutri.ui.popUp.formsPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.worknutri.R;

import java.util.List;


public class PatologiaFormFragment {

    private final PathologyCategory category;
    private CardView viewGroup;
    protected PatologiaFormFragment(PathologyCategory category) {

        this.category = category;
    }

    public CardView generateViewGroup(Context context, String message) {

        viewGroup = (CardView) LayoutInflater.from(context).inflate(R.layout.popup_patologia_description_formulario, null);

        TextView title = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_title);
        title.setText(category.getName());

        configureEditText(message);


        return viewGroup;
    }

    private void configureEditText( String message) {
        MultiAutoCompleteTextView editText = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        editText.setHint(category.getHint());
        editText.setText(message);
    }

    public void configureDeleteButton(ViewGroup viewWereHasInsert, List<PathologyCategory> categories) {
        ImageButton button = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        button.setOnClickListener(onClick ->{
            viewWereHasInsert.removeView(viewGroup);
            if (categories.stream()
                    .noneMatch(categoryOfList -> categoryOfList.toString()
                            .equals(category.toString()))){
                categories.add(category);
            }


        });
    }



}
