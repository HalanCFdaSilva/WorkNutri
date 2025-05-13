package com.example.worknutri.ui.detail.detailPaciente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.ExtrasActivities;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PacienteDescriptionActivity extends AppCompatActivity {


    private DetailPacienteAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_descrition);
        Intent intent = getIntent();
        if (intent.hasExtra(ExtrasActivities.PACIENTE)) {
            Paciente paciente = (Paciente)intent.getSerializableExtra(ExtrasActivities.PACIENTE);
            adapter = new DetailPacienteAdapter(paciente, this);
            FloatingActionButton fab = findViewById(R.id.paciente_description_activity_fab_back);
            fab.setOnClickListener(onClick -> this.finish());
        } else {
            finish();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.refreshData();
        setTitle(adapter.getNomePaciente().toUpperCase());
        adapter.insertTextInLayout(findViewById(R.id.paciente_description_activity_layout));
        adapter.generateAntropometriaAndPatologiaPopUp(findViewById(R.id.paciente_description_activity_layout), getLayoutInflater());
        adapter.configureNavButtom(findViewById(R.id.paciente_description_activity_nav_view));

    }


}
