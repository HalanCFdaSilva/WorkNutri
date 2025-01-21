package com.example.nutricoop.ui.detailPaciente;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.example.nutricoop.ui.ExtrasActivities;
import com.example.nutricoop.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;
import com.example.nutricoop.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;

public class DetailPacienteActivie extends AppCompatActivity {

    private Paciente paciente;
    private Antropometria antropometria;
    private Patologia patologia;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paciente_descrition);
        this.getExtrasIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        setTitle(paciente.getNomePaciente().toUpperCase());
        ((TextView)findViewById(R.id.detail_paciente_activity_name_paciente_descrition)).setText(paciente.getNomePaciente());
        ((TextView)findViewById(R.id.detail_paciente_activity_cpf_paciente_descrition)).setText(paciente.getCpf());
        ((TextView)findViewById(R.id.detail_paciente_activity_idade_paciente_descrition)).setText(String.valueOf(paciente.getIdade()));
        ((TextView)findViewById(R.id.detail_paciente_activity_fone_paciente_descrition)).setText(paciente.getTelefone());
        ((TextView)findViewById(R.id.detail_paciente_activity_email_paciente_descrition)).setText(paciente.getEmail());
        ((TextView)findViewById(R.id.detail_paciente_activity_observation_paciente_descrition)).setText(paciente.getObservacoes());


        ((TextView)findViewById(R.id.detail_paciente_activity_height_paciente_descrition)).setText(antropometria.getAltura());
        ((TextView)findViewById(R.id.detail_paciente_activity_peso_paciente_descrition)).setText(antropometria.getPeso());
        ((TextView)findViewById(R.id.detail_paciente_activity_peso_ideal_paciente_descrition)).setText(antropometria.getPesoDesejado());

        moreDetailButtonsConfig();
    }

    private void moreDetailButtonsConfig() {

        AntroPometriaDetaillPopUp antropometriaPopUp = new AntroPometriaDetaillPopUp(getLayoutInflater(),antropometria,true);
        Button buttonAntropometria = findViewById(R.id.detail_paciente_activity_button_antropometric);
        buttonAntropometria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                antropometriaPopUp.getPopUpWindow().showAtLocation(findViewById(R.id.detail_paciente_activity_layout), Gravity.CENTER, -1, -1);
            }
        });

        PatologiaDetaillPopUp patologiaPopUp = new PatologiaDetaillPopUp(getLayoutInflater(),patologia);
        Button buttonPatologia = findViewById(R.id.detail_paciente_activity_button_patologic);
        buttonPatologia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patologiaPopUp.getPopUpWindow().showAtLocation(findViewById(R.id.detail_paciente_activity_layout), Gravity.CENTER, -1, -1);
            }
        });

    }

    private void getExtrasIntent() {
        Intent intent = getIntent();
        if (intent.hasExtra(ExtrasActivities.PACIENTE)){
            paciente = (Paciente) intent.getSerializableExtra(ExtrasActivities.PACIENTE);
            AppDataBase db = AppDataBase.getInstance(this);
            antropometria = db.antropometriaDao().loadAllByIdPaciente(paciente.getId()).get(0);
            patologia = db.patologiaDao().loadAllByIdPaciente(paciente.getId()).get(0);

        }else {
            finish();
        }
    }
}
