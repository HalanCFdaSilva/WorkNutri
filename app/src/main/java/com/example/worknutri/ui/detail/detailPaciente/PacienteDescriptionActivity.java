package com.example.worknutri.ui.detail.detailPaciente;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;

public class PacienteDescriptionActivity extends AppCompatActivity {


    private DetailPacienteAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_descrition);
        adapter = new DetailPacienteAdapter(getIntent(),this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.refreshData();
        setTitle(adapter.getPaciente().getNomePaciente().toUpperCase());
        adapter.insertTextInLayout(findViewById(R.id.detail_paciente_activity_layout));
        adapter.moreDetailButtonsConfig(findViewById(R.id.detail_paciente_activity_layout),getLayoutInflater());
        adapter.configureNavButtom(findViewById(R.id.detail_paciente_activity_nav_view),findViewById(R.id.detail_paciente_activity_layout));

    }




}
