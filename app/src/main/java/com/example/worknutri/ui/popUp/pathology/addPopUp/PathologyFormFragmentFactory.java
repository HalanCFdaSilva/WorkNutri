package com.example.worknutri.ui.popUp.pathology.addPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;

import java.util.List;


public class PathologyFormFragmentFactory {

    private final PathologyField category;
    private CardView viewGroup;
    private final Patologia pathology;
    public PathologyFormFragmentFactory(PathologyField category, Patologia pathology) {

        this.category = category;
        this.pathology = pathology;
    }

    public void generateViewGroup(Context context, ViewGroup viewGroupWereHasInsert) {
        viewGroup = checkViewNotAlreadyInflated(viewGroupWereHasInsert);
        if (viewGroup == null) {
            viewGroup = (CardView) LayoutInflater.from(context).inflate(R.layout.popup_patologia_description_formulario, viewGroupWereHasInsert,false);
            TextView title = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_title);
            title.setText(category.getUpperName());
            viewGroupWereHasInsert.addView(viewGroup);
        }

    }

    private CardView checkViewNotAlreadyInflated(ViewGroup viewGroupWereHasInsert) {
        for (int i = 0; i < viewGroupWereHasInsert.getChildCount(); i++) {
            CardView childViewGroup = (CardView) viewGroupWereHasInsert.getChildAt(i);
            TextView title = childViewGroup.findViewById(R.id.pop_up_patologia_description_formulario_title);
            if(title.getText().toString().equals(category.getUpperName())){
                return childViewGroup;
            }
        }
        return null;
    }

    public void configureEditText( String message) {
        MultiAutoCompleteTextView editText = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        editText.setHint(category.getHint());
        editText.setText(message);
    }

    public void configureDeleteButton(ViewGroup viewWereHasInsert, List<PathologyField> categories) {
        ImageButton button = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        button.setOnClickListener(onClick ->{

            viewWereHasInsert.removeView(viewGroup);

            if (categories.stream()
                    .noneMatch(categoryOfList -> categoryOfList.toString()
                            .equals(category.toString()))){

                categories.add(category);
                new PathologyFieldMapper(category)
                        .setValue(pathology,""); // Clear the value for the category
            }

        });
    }




}
