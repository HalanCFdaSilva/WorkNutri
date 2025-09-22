package com.example.worknutri.ui.popUp.pathology.addPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.pathology.PathologyField;

import java.util.List;
import java.util.stream.Collectors;

public class PathologyAddPopUp extends PopUpFragment {

    private final Context context;
    private final List<PathologyField> pathologyTypes;
    private Patologia pathology;
    public PathologyAddPopUp(Context context, List<PathologyField> pathologyCategories) {
        super(LayoutInflater.from(context));
        getInflater().inflate(R.layout.popup_patologia_add,getViewToInsert());

        super.insertTitle(R.string.patologia_title);
        this.pathologyTypes = pathologyCategories;
        this.context = context;
    }

    public void configurePopUp(ViewGroup viewWereAdd, Patologia pathology){
        if (pathology==null)
            throw new NullPointerException();
        this.pathology = pathology;
        ViewGroup viewGroup = getViewGroup();
        configureSpinner(viewGroup.findViewById(R.id.popup_patologia_add_spinner),viewGroup.findViewById(R.id.popup_patologia_add_multiAutoComplete));
        configureButton(viewGroup.findViewById(R.id.popup_patologia_add_button),viewWereAdd);
    }

    private void configureSpinner(Spinner spinner, MultiAutoCompleteTextView textView) {
        List<String> pathologyNames = getPathologyNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, pathologyNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String itemAtPosition = (String) spinner.getItemAtPosition(position);
                PathologyField pathologyType = getPathologyCategory(itemAtPosition);
                textView.setHint(pathologyType.getHint());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
        if (pathologyNames.isEmpty()){
            spinner.setEnabled(false);
            spinner.setClickable(false);
        }

    }

    private PathologyField getPathologyCategory(String selectedPathology) {
        return pathologyTypes.stream()
                .filter(pathologyCategory -> pathologyCategory.getUpperName().equals(selectedPathology))
                .findAny().orElse(null);

    }
    private List<String> getPathologyNames() {
        return pathologyTypes.stream()
                .map(PathologyField::getUpperName)
                .collect(Collectors.toList());
    }

    private void configureButton(Button button,ViewGroup viewGroup) {
        button.setOnClickListener(onClick ->{
            Spinner spinner = getViewGroup().findViewById(R.id.popup_patologia_add_spinner);
            String selectedPathology = (String) spinner.getSelectedItem();
            PathologyField pathologyType = getPathologyCategory(selectedPathology);
            pathologyTypes.remove(pathologyType);
            configureSpinner(getViewGroup().findViewById(R.id.popup_patologia_add_spinner),getViewGroup().findViewById(R.id.popup_patologia_add_multiAutoComplete));
            PathologyFormFragmentFactory popUpPatologiaDescription = new PathologyFormFragmentFactory(pathologyType, pathology);
            popUpPatologiaDescription.generateViewGroup(context, viewGroup);

            MultiAutoCompleteTextView multiAutoCompleteTextView = getViewGroup().findViewById(R.id.popup_patologia_add_multiAutoComplete);
            String message = multiAutoCompleteTextView.getText().toString();

            popUpPatologiaDescription.configureEditText(message);
            popUpPatologiaDescription.configureDeleteButton(viewGroup, pathologyTypes);


            getPopUpWindow().dismiss();
        });
    }






}
