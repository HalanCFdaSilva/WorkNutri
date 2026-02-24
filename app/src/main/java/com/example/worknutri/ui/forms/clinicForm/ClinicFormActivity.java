package com.example.worknutri.ui.forms.clinicForm;

import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClinicFormActivity extends AppCompatActivity {
    private ClinicFormAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinic_form_activity);

        adapter = new ClinicFormAdapter(this.findViewById(R.id.clinic_form));
        getClinicaOfIntent();
        adapter.configuraBottomNav(findViewById(R.id.clinic_form_nav_view), getIntent());
        adapter.configureKeyListeners();
        this.addHour();

        save();
    }

    private void getClinicaOfIntent() {
        if (getIntent().hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey())) {
            Clinica clinica = (Clinica) getIntent().getSerializableExtra(ExtrasActivities.CLINICA_EXTRA.getKey());
            adapter.insertClinicInlayout(clinica);
        }


    }

    private void addHour() {
        Button button = findViewById(R.id.clinic_form_horario_atendimento_button_add);
        button.setOnClickListener(onClick -> {
            DayOfWorkUiService dayOfWorkUiService = adapter.getDayOfWorkUiSave();
            dayOfWorkUiService.generatePopUpOfDatePickerToNewDayOfWork();
            dayOfWorkUiService.onPickerDayOfWorkClickInSaveButton();

        });
    }

    private void save() {

        FloatingActionButton button = findViewById(R.id.clinic_form_fab);
        button.setOnClickListener( v ->{
            if (adapter.validateForm(
                    findViewById(R.id.clinic_form_error))) {
                adapter.saveInDataBase();
                finish();
            }
        });

    }


}
