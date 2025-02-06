package com.example.nutricoop.ui.formularios.formularioClinica;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.ui.ExtrasActivities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FormularioClinicaActivity extends AppCompatActivity {
    private FormularioClinicaAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_clinica_activity);

        adapter = new FormularioClinicaAdapter(this);
        getClinicaOfIntent();
        adapter.configuraBottomNav(findViewById(R.id.formulario_clinica_activy_activity_nav_view));
        adapter.configureKeyListeners(findViewById(R.id.formulario_clinica_linear_layout));
        this.addHour();

        save();
    }

    private void getClinicaOfIntent() {
        if (getIntent().hasExtra(ExtrasActivities.CLINICA)){
            Clinica clinica = (Clinica) getIntent().getSerializableExtra(ExtrasActivities.CLINICA);
            adapter.insertClinicaInlayout(clinica,findViewById(R.id.formulario_clinica_linear_layout),getLayoutInflater());
        }


    }

    private void addHour() {
        Button button = findViewById(R.id.formulario_clinica_horario_atendimento_button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.newDay(getLayoutInflater(),findViewById(R.id.formulario_clinica_linear_layout));
            }
        });
    }

    private void save(){

        FloatingActionButton button = findViewById(R.id.formulario_clinica_activy_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (  adapter.validaFormulario(findViewById(R.id.formulario_clinica_linear_layout),
                        findViewById(R.id.formulario_clinica_activy_error))){

                    adapter.saveInDataBase(findViewById(R.id.formulario_clinica_linear_layout));
                    finish();

                }

            }
        });

    }




}
