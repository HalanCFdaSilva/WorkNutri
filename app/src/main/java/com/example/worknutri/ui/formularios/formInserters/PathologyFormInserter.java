package com.example.worknutri.ui.formularios.formInserters;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.formularios.InvalidViewGroupIdException;
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;
import com.example.worknutri.ui.popUp.pathology.addPopUp.PathologyFormFragmentFactory;


import java.util.List;

public class PathologyFormInserter extends FormInserter<Patologia> {

    private  final List<PathologyField> pathologies;
    private final Context context;


    private PathologyFormInserter(ViewGroup viewGroup, List<PathologyField> pathologies) {
        super(viewGroup);
        viewGroupIdExpected = R.id.formulario_paciente_patologia_layout_content;
        this.pathologies = pathologies;
        this.context = viewGroup.getContext();
    }

    public static PathologyFormInserter create(ViewGroup viewGroup,  List<PathologyField> pathologies) {
        if (checkViewGroupIsCorrectly(viewGroup)) {
            return new PathologyFormInserter(viewGroup, pathologies);
        } else {
            throw new InvalidViewGroupIdException( viewGroup.getId());
        }

    }

    @Override
    public void insertViewGroupInEntity(Patologia pathology) {
        ViewGroup viewGroupWithCards = viewGroup;
        if (viewGroup.getId() != R.id.formulario_paciente_patologia_layout_content) {
            viewGroupWithCards = viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content);
        }
        for (int i = 0; i < viewGroupWithCards.getChildCount() ; i++) {
            ViewGroup child = (ViewGroup) viewGroupWithCards.getChildAt(i);
            String title = getValueOfTextView(child);

            try{
                PathologyField pathologyType = PathologyField.from(title);
                new PathologyFieldMapper(pathologyType)
                        .setValue( pathology,getMessageOfView(child));
            }catch (IllegalArgumentException ignored){}

        }

    }

    @NonNull
    private static String getValueOfTextView(ViewGroup child) {
        TextView textViewType = child.findViewById(R.id.pop_up_patologia_description_formulario_title);
        return textViewType.getText().toString();


    }




    @NonNull
    private static String getMessageOfView(ViewGroup child) {
        MultiAutoCompleteTextView editText = child.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        return editText.getText().toString();
    }


    /**
     * Método que preenche o viewGroup de pathology do FormularioPaciente com os dados que recebe da classe Patologia com
     * a ajuda da classe InsertSelectViewSupport.
     *
     * @see Patologia
     * @see ViewsUtil
     */
    @Override
    public void insertEntityInViewGroup(Patologia pathology) {

        generateView(PathologyField.ACTUAL_PATHOLOGY, pathology);
        generateView(PathologyField.MEDICATION, pathology);
        generateView(PathologyField.SUPPLEMENT, pathology);
        generateView(PathologyField.ACTIVITY, pathology);
        generateView(PathologyField.SLUMBER, pathology);
        generateView(PathologyField.SUGAR, pathology);
        generateView(PathologyField.WATER, pathology);
        generateView(PathologyField.URINE, pathology);
        generateView(PathologyField.STOOL, pathology);
        generateView(PathologyField.ALLERGY, pathology);
        generateView(PathologyField.ETHYLIC, pathology);
        generateView(PathologyField.SMOKER, pathology);
    }

    private void generateView(PathologyField pathologyTypeToInsert, Patologia pathology) {
        String valueOfPathology = new PathologyFieldMapper(pathologyTypeToInsert).getValue(pathology);
        ViewGroup viewToInsert = viewGroup;
        if (viewGroup.getId() != R.id.formulario_paciente_patologia_layout_content) {
            viewToInsert = viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content);
        }
        if (valueOfPathology != null && !valueOfPathology.isEmpty()) {
            PathologyFormFragmentFactory pathologyFormFragmentFactory = new PathologyFormFragmentFactory(pathologyTypeToInsert,pathology);
            pathologyFormFragmentFactory.generateViewGroup(context, viewToInsert);
            pathologyFormFragmentFactory.configureEditText(valueOfPathology);
            pathologies.remove(pathologyTypeToInsert);
            pathologyFormFragmentFactory.configureDeleteButton(viewGroup, pathologies);

        }
    }

    public static boolean checkViewGroupIsCorrectly(ViewGroup viewGroup){
        return viewGroup != null && ( viewGroup.getId() == R.id.form_patient_activity ||
                viewGroup.getId() == R.id.formulario_paciente_patologia_layout ||
                viewGroup.getId() == R.id.formulario_paciente_patologia_layout_content);
    }



}
