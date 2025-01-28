package com.example.nutricoop.ui.formularios.formularioPaciente;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nutricoop.R;
import com.example.nutricoop.ui.formularios.editTextKeysListener.CalendarioKeyListener;
import com.example.nutricoop.ui.formularios.editTextKeysListener.CpfKeyListener;
import com.example.nutricoop.ui.formularios.editTextKeysListener.FoneKeyListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FormularioPacienteActivity extends AppCompatActivity {

    private FormularioPacienteAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_paciente_activity);
        adapter = new FormularioPacienteAdapter(this);
        verCalculos();
        adapter.setClinicas(findViewById(R.id.formulario_paciente_dados_pessoais_clinica_spinner));
        adapter.configuraBottomNav(findViewById(R.id.formulario_paciente_activity_nav_view));
        adapter.insertInFormulario(getIntent(),findViewById(R.id.formulario_paciente_activity_constraint_layout));
        this.configurePatologiaCheckBox();
        this.insertKeyListeners();
        FloatingActionButton button = findViewById(R.id.formulario_paciente_activity_fab);
        button.setOnClickListener(v -> {
            saveLayout();

        });
    }

    private void configurePatologiaCheckBox() {
        adapter.patologiaCheckBoxConfigure(findViewById(R.id.formulario_paciente_patologia_medicacao_checkbox),findViewById(R.id.formulario_paciente_patologia_medicacao));
        adapter.patologiaCheckBoxConfigure(findViewById(R.id.formulario_paciente_patologia_suplemento_checkbox),findViewById(R.id.formulario_paciente_patologia_suplemento));
        adapter.patologiaCheckBoxConfigure(findViewById(R.id.formulario_paciente_patologia_etilico_checkbox),findViewById(R.id.formulario_paciente_patologia_etilico));
        adapter.patologiaCheckBoxConfigure(findViewById(R.id.formulario_paciente_patologia_fumante_checkbox),findViewById(R.id.formulario_paciente_patologia_fumante));
        adapter.patologiaCheckBoxConfigure(findViewById(R.id.formulario_paciente_patologia_alergia_checkbox),findViewById(R.id.formulario_paciente_patologia_alergia));
    }

    private void insertKeyListeners() {
        EditText editText = findViewById(R.id.formulario_paciente_dados_pessoais_cpf);
        editText.setOnKeyListener(new CpfKeyListener(editText));

        editText = findViewById(R.id.formulario_paciente_dados_pessoais_fone);
        editText.setOnKeyListener(new FoneKeyListener(editText));

        editText = findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
        editText.setOnKeyListener(new CalendarioKeyListener(editText));

    }


    private void verCalculos() {
        Button button = findViewById(R.id.formulario_paciente_antropometria_calculos_button);
        button.setOnClickListener(v -> {
            adapter.getCalculosAntropometricos(getLayoutInflater(),
                    findViewById(R.id.formulario_paciente_activity_constraint_layout));
        });
    }

    private void saveLayout() {
        ViewGroup viewGroup = findViewById(R.id.formulario_paciente_activity_constraint_layout);

        if (adapter.validaFormulario(viewGroup,findViewById(R.id.formulario_paciente_activity_error))){
            adapter.savePaciente(viewGroup);
            finish();
        }

    }


}
