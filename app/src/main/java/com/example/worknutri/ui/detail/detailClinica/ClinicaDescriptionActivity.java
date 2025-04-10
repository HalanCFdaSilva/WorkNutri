package com.example.worknutri.ui.detail.detailClinica;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClinicaDescriptionActivity extends AppCompatActivity {

    private ClinicaDescriptionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinica_description);
        adapter = new ClinicaDescriptionAdapter(getIntent(), this);
        adapter.configureNavButton(findViewById(R.id.clinica_description_activity_nav_view),
                findViewById(R.id.clinica_description_activity_root));
        FloatingActionButton fab = findViewById(R.id.clinica_description_activity_fab_back);
        fab.setOnClickListener(onClick -> this.finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.insertClinicaInLayout(findViewById(R.id.clinica_description_activity_root));
        adapter.insertDaysOfWorkInLayout(findViewById(R.id.clinica_description_activity_horario_atendimento_layout),
                getLayoutInflater());
    }


}
