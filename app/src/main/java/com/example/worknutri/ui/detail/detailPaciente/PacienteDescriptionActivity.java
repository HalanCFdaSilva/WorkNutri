package com.example.worknutri.ui.detail.detailPaciente;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class PacienteDescriptionActivity extends AppCompatActivity {


    private DetailPacienteAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_descrition);
        adapter = new DetailPacienteAdapter(getIntent(), this);
        FloatingActionButton fab = findViewById(R.id.paciente_description_activity_fab_back);
        fab.setOnClickListener(onClick -> this.finish());

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.refreshData();
        setTitle(adapter.getPaciente().getNomePaciente().toUpperCase());
        adapter.insertTextInLayout(findViewById(R.id.paciente_description_activity_layout));
        adapter.generateAntropometriaAndPatologiaPopUp(findViewById(R.id.paciente_description_activity_layout), getLayoutInflater());
        adapter.configureNavButtom(findViewById(R.id.paciente_description_activity_nav_view));

    }


}
