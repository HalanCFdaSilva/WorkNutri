package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyType;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyTypeToPathologyMapper;
import com.example.worknutri.ui.popUp.formsPopUp.PatologiaFormFragment;


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
                    PathologyType pathologyType = PathologyType.from(title);
                    new PathologyTypeToPathologyMapper(pathologyType)
                            .insertValueInCorrectAtribute( pathology,getMessageOfView(child));
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
     * @see InsertSelectViewSupport
     */
    public void InsertPatologiaValuesInViewGroup(Context context, List<PathologyType> pathologies) {
        if (viewGroup != null){
            this.context = context;
            generateView(PathologyType.ACTUAL_PATHOLOGY, pathologies);
            generateView(PathologyType.MEDICATION, pathologies);
            generateView(PathologyType.SUPPLEMENT, pathologies);
            generateView(PathologyType.ACTIVITY, pathologies);
            generateView(PathologyType.SLUMBER, pathologies);
            generateView(PathologyType.SUGAR, pathologies);
            generateView(PathologyType.WATER, pathologies);
            generateView(PathologyType.URINE, pathologies);
            generateView(PathologyType.STOOL, pathologies);
            generateView(PathologyType.ALLERGY, pathologies);
            generateView(PathologyType.ETHYLIC, pathologies);
            generateView(PathologyType.SMOKER, pathologies);
        }
    }

    private void generateView(PathologyType pathologyTypeToInsert, List<PathologyType> pathologies) {
        String valueOfPathology = new PathologyTypeToPathologyMapper(pathologyTypeToInsert)
                .getValueOfPathology(pathology);
        if (valueOfPathology != null && !valueOfPathology.isEmpty()) {
            PatologiaFormFragment patologiaFormFragment = new PatologiaFormFragment(pathologyTypeToInsert,pathology);
            CardView cardView = patologiaFormFragment.generateViewGroup(context, valueOfPathology);
            pathologies.remove(pathologyTypeToInsert);
            patologiaFormFragment.configureDeleteButton(viewGroup, pathologies);
            viewGroup.addView(cardView);
        }
    }



}
