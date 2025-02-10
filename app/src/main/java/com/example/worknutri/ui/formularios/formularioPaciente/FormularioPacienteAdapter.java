package com.example.worknutri.ui.formularios.formularioPaciente;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.formularios.FormularioAdapter;
import com.example.worknutri.ui.formularios.ValidaFormulario;
import com.example.worknutri.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;

import java.util.List;

public class FormularioPacienteAdapter extends FormularioAdapter {

    private Patologia patologia;
    private Antropometria antropometria;
    private Paciente paciente;
    private List<Clinica> clinicasInOrder;

    public FormularioPacienteAdapter(Context context) {
        super(context);
        paciente = new Paciente();
        antropometria = new Antropometria();
        patologia = new Patologia();
        clinicasInOrder = getDataBase().clinicaDao().getAllInOrder();
    }

    public void insertInFormulario(Intent intent,ViewGroup viewRootFormulario){
        if (intent.hasExtra(ExtrasActivities.PACIENTE)){
            paciente = (Paciente) intent.getSerializableExtra(ExtrasActivities.PACIENTE);
            antropometria = getDataBase().antropometriaDao().loadAllByIdPaciente(paciente.getId()).get(0);
            patologia = getDataBase().patologiaDao().loadAllByIdPaciente(paciente.getId()).get(0);
            InsertionPacienteFormulario insertion =  new InsertionPacienteFormulario();
            insertion.insertPacienteInViewGroup(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_layout),paciente);
            insertion.insertAntropometria(viewRootFormulario.findViewById(R.id.formulario_paciente_antropometria_layout),antropometria);
            insertion.InsertPatologia(viewRootFormulario.findViewById(R.id.formulario_paciente_patologia_layout),patologia);
            insertion.SelectClinica(viewRootFormulario.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner),
                    paciente.getClinicaId(),getDataBase().clinicaDao().getAllInOrder());
        }
    }


    public void savePaciente(ViewGroup viewGroup){
        InsertionPacienteFormulario generator = new InsertionPacienteFormulario();
            generator.insertViewGroupInPaciente(viewGroup,paciente);
            generator.insertClinicaInPaciente(viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner),paciente,clinicasInOrder);
            generator.insertViewGroupInAntropometria(viewGroup,antropometria,paciente);
            generator.insertViewGroupInPatologia(viewGroup,patologia);


            getDataBase().pacienteDao().insertAll(paciente);
            getDataBase().antropometriaDao().insertAll(antropometria);
            getDataBase().patologiaDao().insertAll(patologia);
    }

    public void setClinicas(Spinner spinner) {

        spinner.setAdapter(new ClinicaArrayAdapter(getContext(),clinicasInOrder));
    }

    public boolean validaFormulario(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = validaObrigatorios(viewRoot, textViewError);
        if (validado && !ValidaFormulario.validaData(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_nascimento),
                textViewError)){
            validado = false;
        }
        if (validado && !ValidaFormulario.validaEmail(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_email),
                textViewError)){
            validado = false;
        }
        if (validado && !ValidaFormulario.validaTelefone(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_fone),
                textViewError)){
            validado = false;
        }

        return validado;
    }

    private boolean validaObrigatorios(ViewGroup viewRoot, TextView textViewError) {
        boolean validado = !ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_name_obrigatorio), textViewError);
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_idade),
                viewRoot.findViewById(R.id.formulario_paciente_dados_pessoais_idade_obrigatorio),textViewError)){
            validado = false;
        }
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_altura_obrigatorio),textViewError)){
            validado = false;
        }
        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_atual_obrigatorio),textViewError)){
            validado = false;
        }

        if (ValidaFormulario.checaEditText(viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal),
                viewRoot.findViewById(R.id.formulario_paciente_antropometria_peso_ideal_obrigatorio),textViewError)){
            validado = false;
        }


        return validado;
    }

    public void getCalculosAntropometricos(LayoutInflater inflater, ViewGroup viewGroup) {
        if (validaCalculosAntropometricos(viewGroup)){
            viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_error).setVisibility(View.GONE);
            ViewGroup layout = viewGroup.findViewById(
                    R.id.formulario_paciente_activity_constraint_layout);
            InsertionPacienteFormulario generator = new InsertionPacienteFormulario();
            generator.insertViewGroupInPaciente(layout,paciente);
            generator.insertViewGroupInAntropometria(layout,antropometria,paciente);
            AntroPometriaDetaillPopUp popUp = new AntroPometriaDetaillPopUp(inflater,antropometria,false);
            popUp.getPopUpWindow().showAtLocation(viewGroup.findViewById(
                    R.id.formulario_paciente_activity_constraint_layout), Gravity.CENTER, -1, -1);
        }else {
            viewGroup.findViewById(R.id.formulario_paciente_antropometria_calculos_error).setVisibility(View.VISIBLE);
        }
    }

    private boolean validaCalculosAntropometricos(ViewGroup viewGroup){
        EditText editText = viewGroup.findViewById(R.id.formulario_paciente_dados_pessoais_idade);
        if (!editText.getText().toString().isBlank()){
            editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_altura);
            if (!editText.getText().toString().isBlank()){
                editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_atual);
                if (!editText.getText().toString().isBlank()){
                    editText = viewGroup.findViewById(R.id.formulario_paciente_antropometria_peso_ideal);
                    return !editText.getText().toString().isBlank();
                }
            }
        }
        return false;
    }

    public void patologiaCheckBoxConfigure(CheckBox checkBox, EditText editText){
        checkBox.setOnClickListener(v -> {
            if (!checkBox.isChecked()){
                editText.setVisibility(View.GONE);
            }else {
                editText.setVisibility(View.VISIBLE);
            }
        });
    }
}
