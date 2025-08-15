package com.example.worknutri.ui.formularios.formularioPaciente;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.ui.editTextKeysListener.CalendarioKeyListener;
import com.example.worknutri.ui.editTextKeysListener.FoneKeyListener;
import com.example.worknutri.ui.editTextKeysListener.PesoKeyListener;
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
        adapter.configuraBottomNav(findViewById(R.id.formulario_paciente_activity_nav_view),getIntent());
        adapter.insertInFormulario(getIntent(), findViewById(R.id.formulario_paciente_activity_constraint_layout));
        adapter.OpenNivelAtividadePopUpOnClick(findViewById(R.id.formulario_paciente_antropometria_calculos_atividade_info_imageview),
                getLayoutInflater(), findViewById(R.id.formulario_paciente_activity_constraint_layout));


        this.insertKeyListeners();
        FloatingActionButton button = findViewById(R.id.formulario_paciente_activity_fab);
        button.setOnClickListener(v -> saveLayout());
    }



    private void insertKeyListeners() {
        EditText editText = findViewById(R.id.formulario_paciente_dados_pessoais_fone);
        editText.setOnKeyListener(new FoneKeyListener());

        editText = findViewById(R.id.formulario_paciente_dados_pessoais_nascimento);
        editText.setOnKeyListener(new CalendarioKeyListener());

        PesoKeyListener keyListener = new PesoKeyListener();
        keyListener.setSpinnerAltura(findViewById(R.id.formulario_paciente_antropometria_spinner_altura));
        keyListener.setPesoIdeal(findViewById(R.id.formulario_paciente_antropometria_peso_ideal_spinner),
                findViewById(R.id.formulario_paciente_antropometria_peso_ideal));
        editText = findViewById(R.id.formulario_paciente_antropometria_altura);
        editText.setOnKeyListener(keyListener);


    }


    private void verCalculos() {
        Button button = findViewById(R.id.formulario_paciente_antropometria_calculos_button);
        button.setOnClickListener(v -> adapter.getCalculosAntropometricos(getLayoutInflater(),
                    findViewById(R.id.formulario_paciente_activity_constraint_layout)));
    }

    private void saveLayout() {
        ViewGroup viewGroup = findViewById(R.id.formulario_paciente_activity_constraint_layout);

        if (adapter.validaFormulario(viewGroup, findViewById(R.id.formulario_paciente_activity_error))) {
            adapter.savePaciente(viewGroup);
            finish();
        }

    }


}
