package com.example.worknutri.ui.popUp.formsPopUp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import androidx.cardview.widget.CardView;
import com.example.worknutri.R;
import com.example.worknutri.ui.popUp.PopUpFragment;
import java.util.List;
import java.util.stream.Collectors;

public class PopUpPathologyAdd extends PopUpFragment {

    private final Context context;
    private final List<PathologyCategory> pathology;
    public PopUpPathologyAdd(Context context,List<PathologyCategory> pathologyCategories) {
        super(LayoutInflater.from(context));
        CardView cardView =  (CardView) getInflater().inflate(R.layout.popup_patologia_add,null);
    insertView(cardView);
        getViewGroup().findViewById(R.id.popup_base_layout_scrollview_layout_header);
        this.pathology = pathologyCategories;
        this.context = context;
    }

    public void configurePopUp(ViewGroup viewWereAdd){
        ViewGroup viewGroup = getViewGroup();
        configureSpinner(viewGroup.findViewById(R.id.pop_up_patologia_add_spinner),viewGroup.findViewById(R.id.pop_up_patologia_add_multiAutoComplete));
        configureButton(viewGroup.findViewById(R.id.pop_up_patologia_add_button),viewWereAdd);
    }

    private void configureSpinner(Spinner spinner, MultiAutoCompleteTextView textView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_spinner_item, getPathologyNames());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemClickListener((parent, view, position, id) -> {
            String itemAtPosition = (String) spinner.getItemAtPosition(position);
            PathologyCategory pathologyCategory = getPathologyCategory(itemAtPosition);
            textView.setText("");
            textView.setHint(pathologyCategory.getHint());
        });
    }

    private PathologyCategory getPathologyCategory(String selectedPathology) {
        return pathology.stream()
                .filter(pathologyCategory -> pathologyCategory.getName().equals(selectedPathology))
                .findAny().orElse(null);

    }
    private List<String> getPathologyNames() {
        return pathology.stream()
                .map(PathologyCategory::name)
                .collect(Collectors.toList());
    }

    private void configureButton(Button button,ViewGroup viewGroup) {
        button.setOnClickListener(onClick ->{
            Spinner spinner = getViewGroup().findViewById(R.id.pop_up_patologia_add_spinner);
            String selectedPathology = (String) spinner.getSelectedItem();
            removePathology(selectedPathology);
            PopUpPatologiaDescription popUpPatologiaDescription = new PopUpPatologiaDescription();
            //TODO implementar a inserção do popUp no viewGroup
        });
    }

    private void removePathology(String selectedPathology) {

        pathology.remove(getPathologyCategory(selectedPathology));
    }




}
