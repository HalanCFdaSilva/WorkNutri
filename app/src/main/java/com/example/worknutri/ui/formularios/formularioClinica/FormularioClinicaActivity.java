package com.example.worknutri.ui.formularios.formularioClinica;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FormularioClinicaActivity extends AppCompatActivity {
    private FormularioClinicaAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulario_clinica_activity);

        adapter = new FormularioClinicaAdapter(this);
        getClinicaOfIntent();
        adapter.configuraBottomNav(findViewById(R.id.formulario_clinica_activy_activity_nav_view), getIntent());
        adapter.configureKeyListeners(findViewById(R.id.formulario_clinica_linear_layout));
        this.addHour();

        save();
    }

    private void getClinicaOfIntent() {
        if (getIntent().hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey())) {
            Clinica clinica = (Clinica) getIntent().getSerializableExtra(ExtrasActivities.CLINICA_EXTRA.getKey());
            adapter.insertClinicaInlayout(clinica,findViewById(R.id.formulario_clinica_linear_layout));
        }


    }

    private void addHour() {
        Button button = findViewById(R.id.formulario_clinica_horario_atendimento_button_add);
        button.setOnClickListener(onClick -> {
            DayOfWorkUiService dayOfWorkUiService = adapter.getDayOfWorkUiSave();
            dayOfWorkUiService.generatePopUpOfDatePickerToNewDayOfWork();
            dayOfWorkUiService.onPickerDayOfWorkClickInSaveButton();

        });
    }

    private void save() {

        FloatingActionButton button = findViewById(R.id.formulario_clinica_activy_fab);
        button.setOnClickListener( v ->{
            if (adapter.validaFormulario(findViewById(R.id.formulario_clinica_linear_layout),
                    findViewById(R.id.formulario_clinica_activy_error))) {
                adapter.saveInDataBase(findViewById(R.id.formulario_clinica_linear_layout));
                finish();
            }
        });

    }


}
