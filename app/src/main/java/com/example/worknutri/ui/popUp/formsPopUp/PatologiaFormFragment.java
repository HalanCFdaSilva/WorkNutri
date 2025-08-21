package com.example.worknutri.ui.popUp.formsPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

import java.util.List;


public class PatologiaFormFragment {

    private final PathologyType category;
    private CardView viewGroup;
    private final Patologia pathology;
    public PatologiaFormFragment(PathologyType category, Patologia pathology) {

        this.category = category;
        this.pathology = pathology;
    }

    public void generateViewGroup(Context context, ViewGroup viewGroupWereHasInsert) {

        viewGroup = (CardView) LayoutInflater.from(context).inflate(R.layout.popup_patologia_description_formulario, viewGroupWereHasInsert);

        TextView title = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_title);
        title.setText(category.getUpperName());

    }

    public void configureEditText( String message) {
        MultiAutoCompleteTextView editText = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        editText.setHint(category.getHint());
        editText.setText(message);
    }

    public void configureDeleteButton(ViewGroup viewWereHasInsert, List<PathologyType> categories) {
        ImageButton button = viewGroup.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        button.setOnClickListener(onClick ->{

            viewWereHasInsert.removeView(viewGroup);

            if (categories.stream()
                    .noneMatch(categoryOfList -> categoryOfList.toString()
                            .equals(category.toString()))){

                categories.add(category);
                new PathologyTypeToPathologyMapper(category)
                        .insertValueInCorrectAtribute(pathology,""); // Clear the value for the category
            }

        });
    }




}
