package com.example.worknutri.ui.detail.detailClinica;


import android.os.Bundle;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ClinicDescriptionActivity extends AppCompatActivity {

    private ClinicDescriptionAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clinica_description);
        adapter = new ClinicDescriptionAdapter(getIntent(), this);
        adapter.configureNavButton(findViewById(R.id.clinica_description_activity_nav_view),
                findViewById(R.id.clinica_description_activity_root_layout));
        FloatingActionButton fab = findViewById(R.id.clinica_description_activity_fab_back);
        fab.setOnClickListener(onClick -> this.finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.insertClinicInLayout(findViewById(R.id.clinica_description_activity_root_layout));
        ViewGroup serviceHourLayout = findViewById(R.id.clinica_description_activity_horario_atendimento_layout);
        serviceHourLayout.removeAllViews();
        adapter.insertDaysOfWorkInLayout(serviceHourLayout);
    }


}
