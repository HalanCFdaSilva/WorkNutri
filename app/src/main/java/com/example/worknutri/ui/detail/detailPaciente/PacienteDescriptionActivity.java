package com.example.worknutri.ui.detail.detailPaciente;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;


public class PacienteDescriptionActivity extends AppCompatActivity {


    private DetailPacienteAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_descrition);
        adapter = new DetailPacienteAdapter(getIntent(), this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.refreshData();
        setTitle(adapter.getPaciente().getNomePaciente().toUpperCase());
        adapter.insertTextInLayout(findViewById(R.id.detail_paciente_activity_layout));
        adapter.moreDetailButtonsConfig(findViewById(R.id.detail_paciente_activity_layout), getLayoutInflater());
        adapter.configureNavButtom(findViewById(R.id.detail_paciente_activity_nav_view), findViewById(R.id.detail_paciente_activity_layout));

    }


}
