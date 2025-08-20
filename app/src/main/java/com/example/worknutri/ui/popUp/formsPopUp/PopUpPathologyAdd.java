package com.example.worknutri.ui.popUp.formsPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import androidx.cardview.widget.CardView;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.PopUpFragment;
import java.util.List;
import java.util.stream.Collectors;

public class PopUpPathologyAdd extends PopUpFragment {

    private final Context context;
    private final List<PathologyType> pathologyTypes;
    private Patologia pathology;
    public PopUpPathologyAdd(Context context, List<PathologyType> pathologyCategories) {
        super(LayoutInflater.from(context));
        getInflater().inflate(R.layout.popup_patologia_add,getViewToInsert());

        super.insertTitle(R.string.patologia_title);
        this.pathologyTypes = pathologyCategories;
        this.context = context;
    }

    public void configurePopUp(ViewGroup viewWereAdd,Patologia pathology){
        this.pathology = pathology;
        ViewGroup viewGroup = getViewGroup();
        configureSpinner(viewGroup.findViewById(R.id.pop_up_patologia_add_spinner),viewGroup.findViewById(R.id.pop_up_patologia_add_multiAutoComplete));
        configureButton(viewGroup.findViewById(R.id.pop_up_patologia_add_button),viewWereAdd);
    }

    private void configureSpinner(Spinner spinner, MultiAutoCompleteTextView textView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, getPathologyNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemAtPosition = (String) spinner.getItemAtPosition(position);
                PathologyType pathologyType = getPathologyCategory(itemAtPosition);
                textView.setText("");
                textView.setHint(pathologyType.getHint());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private PathologyType getPathologyCategory(String selectedPathology) {
        return pathologyTypes.stream()
                .filter(pathologyCategory -> pathologyCategory.getName().equals(selectedPathology))
                .findAny().orElse(null);

    }
    private List<String> getPathologyNames() {
        return pathologyTypes.stream()
                .map(PathologyType::getName)
                .collect(Collectors.toList());
    }

    private void configureButton(Button button,ViewGroup viewGroup) {
        button.setOnClickListener(onClick ->{
            Spinner spinner = getViewGroup().findViewById(R.id.pop_up_patologia_add_spinner);
            String selectedPathology = (String) spinner.getSelectedItem();
            PathologyType pathologyType = getPathologyCategory(selectedPathology);
            pathologyTypes.remove(pathologyType);

            MultiAutoCompleteTextView multiAutoCompleteTextView = getViewGroup().findViewById(R.id.pop_up_patologia_add_multiAutoComplete);
            String message = multiAutoCompleteTextView.getText().toString();
            PatologiaFormFragment popUpPatologiaDescription = new PatologiaFormFragment(pathologyType, pathology);
            CardView viewToAdd = popUpPatologiaDescription.generateViewGroup(context, message);
            popUpPatologiaDescription.configureDeleteButton(viewGroup, pathologyTypes);
            viewGroup.addView(viewToAdd);

            getPopUpWindow().dismiss();
        });
    }






}
