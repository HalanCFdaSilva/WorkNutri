package com.example.nutricoop.ui.addPaciente;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPacienteActivy extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_paciente_activy);


        FloatingActionButton button = findViewById(R.id.add_paciente_activy_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLayout();
                finish();
            }
        });
    }

    private void saveLayout() {

        GenerateEntitieOfAddPaciente generator = new GenerateEntitieOfAddPaciente(findViewById(R.id.add_paciente_constraint_layout));
        Paciente paciente = generator.generatePaciente();
        Antropometria antropometria = generator.generateAntropometria(paciente);
        Patologia patologia = generator.generatePatologia();
       AppDataBase dataBase = AppDataBase.getInstance(this);

        dataBase.pacienteDao().insertAll(paciente);
        dataBase.antropometriaDao().insertAll(antropometria);
        dataBase.patologiaDao().insertAll(patologia);

//






    }


}
