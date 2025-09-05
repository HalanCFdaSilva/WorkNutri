package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;
import com.example.worknutri.ui.popUp.pathology.addPopUp.PathologyFormFragmentFactory;


import java.util.List;

public class PathologyInsertionPacienteForm extends InsertionPacienteForm {

    private final Patologia pathology;
    private Context context;


    public PathologyInsertionPacienteForm(@Nullable ViewGroup viewGroup, Patologia pathology) {
        super(viewGroup);
        this.pathology = pathology;
    }

    public void insertViewGroupInPatologia() {
        if (viewGroup != null){
            for (int i = 0; i < viewGroup.getChildCount() ; i++) {
                ViewGroup child = (ViewGroup) viewGroup.getChildAt(i);
                String title = getTitleOfView(child);

                try{
                    PathologyField pathologyType = PathologyField.from(title);
                    new PathologyFieldMapper(pathologyType)
                            .setValue( pathology,getMessageOfView(child));
                }catch (IllegalArgumentException ignored){}

            }
        }
    }

    @NonNull
    private static String getTitleOfView(ViewGroup child) {
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
    public void InsertPatologiaValuesInViewGroup(Context context, List<PathologyField> pathologies) {
        if (viewGroup != null){
            this.context = context;
            generateView(PathologyField.ACTUAL_PATHOLOGY, pathologies);
            generateView(PathologyField.MEDICATION, pathologies);
            generateView(PathologyField.SUPPLEMENT, pathologies);
            generateView(PathologyField.ACTIVITY, pathologies);
            generateView(PathologyField.SLUMBER, pathologies);
            generateView(PathologyField.SUGAR, pathologies);
            generateView(PathologyField.WATER, pathologies);
            generateView(PathologyField.URINE, pathologies);
            generateView(PathologyField.STOOL, pathologies);
            generateView(PathologyField.ALLERGY, pathologies);
            generateView(PathologyField.ETHYLIC, pathologies);
            generateView(PathologyField.SMOKER, pathologies);
        }
    }

    private void generateView(PathologyField pathologyTypeToInsert, List<PathologyField> pathologies) {
        String valueOfPathology = new PathologyFieldMapper(pathologyTypeToInsert).getValue(pathology);

        if (valueOfPathology != null && !valueOfPathology.isEmpty()) {
            PathologyFormFragmentFactory pathologyFormFragmentFactory = new PathologyFormFragmentFactory(pathologyTypeToInsert,pathology);
            pathologyFormFragmentFactory.generateViewGroup(context, viewGroup);
            pathologyFormFragmentFactory.configureEditText(valueOfPathology);
            pathologies.remove(pathologyTypeToInsert);
            pathologyFormFragmentFactory.configureDeleteButton(viewGroup, pathologies);

        }
    }



}
