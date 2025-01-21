package com.example.nutricoop.ui.addPaciente;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;
import com.example.nutricoop.ui.editTextKeysListener.CalendarioKeyListener;
import com.example.nutricoop.ui.editTextKeysListener.CpfKeyListener;
import com.example.nutricoop.ui.editTextKeysListener.FoneKeyListener;
import com.example.nutricoop.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddPacienteActivy extends AppCompatActivity {

    private AppDataBase dataBase;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_paciente_activy);
        dataBase = AppDataBase.getInstance(this);
        verCalculos();
        setClinicas();

        EditText editText = findViewById(R.id.add_paciente_dados_pessoais_cpf);
        editText.setOnKeyListener(new CpfKeyListener(editText));

        editText = findViewById(R.id.add_paciente_dados_pessoais_fone);
        editText.setOnKeyListener(new FoneKeyListener(editText));

        editText = findViewById(R.id.add_paciente_dados_pessoais_nascimento);
        editText.setOnKeyListener(new CalendarioKeyListener(editText));

        FloatingActionButton button = findViewById(R.id.add_paciente_activy_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLayout();
                finish();
            }
        });
    }

    private void setClinicas() {
        Spinner spinner = findViewById(R.id.add_paciente_dados_pessoais_clinica_spinner);
        spinner.setAdapter(new ClinicaArrayAdapter(this,dataBase.clinicaDao().getAllInOrder()));
    }

    private void verCalculos() {
        Button button = findViewById(R.id.add_paciente_antropometria_calculos_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GenerateEntitieOfAddPaciente generator = new GenerateEntitieOfAddPaciente(findViewById(R.id.add_paciente_constraint_layout));
                Paciente paciente = generator.generatePaciente();
                Antropometria antropometria = generator.generateAntropometria(paciente);
                AntroPometriaDetaillPopUp popUp = new AntroPometriaDetaillPopUp(getLayoutInflater(),antropometria,false);
                popUp.getPopUpWindow().showAtLocation(findViewById(R.id.add_paciente_constraint_layout), Gravity.CENTER, -1, -1);

            }
        });
    }

    private void saveLayout() {

        GenerateEntitieOfAddPaciente generator = new GenerateEntitieOfAddPaciente(findViewById(R.id.add_paciente_constraint_layout));
        Paciente paciente = generator.generatePaciente();
        Antropometria antropometria = generator.generateAntropometria(paciente);
        Patologia patologia = generator.generatePatologia();


        dataBase.pacienteDao().insertAll(paciente);
        dataBase.antropometriaDao().insertAll(antropometria);
        dataBase.patologiaDao().insertAll(patologia);

//






    }


}
