package com.example.nutricoop.ui.addClinica;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutricoop.R;
import com.example.nutricoop.databinding.ActivityMainBinding;
import com.example.nutricoop.sqlLite.dao.ClinicaDao;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.ui.BottomMenuConfigurator;
import com.example.nutricoop.ui.InsertSelectViewSupport;
import com.example.nutricoop.ui.editTextKeysListener.CepKeyListener;
import com.example.nutricoop.ui.editTextKeysListener.FoneKeyListener;
import com.example.nutricoop.ui.popUp.hourDatePopUp.DatePickerFragment;
import com.example.nutricoop.ui.popUp.hourDatePopUp.HourDateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddClinicActivy extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clinic_activity);

        EditText viewById = findViewById(R.id.add_clinica_dados_pessoais_fone);
        viewById.setOnKeyListener(new FoneKeyListener(viewById));

        viewById = findViewById(R.id.add_clinica_endereco_cep);
        viewById.setOnKeyListener(new CepKeyListener(viewById));

        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(this,
                findViewById(R.id.add_clinica_activy_activity_nav_view));
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes,true);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas,false);

        this.addHour();

        save();
    }

    private void addHour() {
        Button button = findViewById(R.id.add_clinica_horario_atendimento_button_add);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup view =(ViewGroup) getLayoutInflater().inflate(R.layout.date_picker_pop_up, null);
                DatePickerFragment datePickerFragment = new DatePickerFragment(view);
                HourDateFragment hourDateFragment = new HourDateFragment(getLayoutInflater());
                datePickerFragment.layoutGenerate(hourDateFragment,findViewById(R.id.add_clinica_horario_atendimento_layout));
                datePickerFragment.getPopUpWindow().showAtLocation(findViewById(R.id.add_clinica_linear_layout), Gravity.CENTER, -1, -1);
            }
        });
    }

    private void save(){

        FloatingActionButton button = findViewById(R.id.add_clinica_activy_fab);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertIn();
                finish();
            }
        });

    }

    private void insertIn(){
        ClinicaDao clinicaDao = AppDataBase.getInstance(this).clinicaDao();
        Clinica clinica = new Clinica();

        String stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_dados_gerais_name));
        clinica.setNome(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_dados_pessoais_email));
        clinica.setEstado(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_dados_pessoais_fone));
        clinica.setTelefone1(stringOfEditText);

        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_endereco_rua));
        clinica.setRua(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_endereco_numero));
        clinica.setNumero(Integer.parseInt(stringOfEditText));
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_endereco_complemento));
        clinica.setComplemento(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_endereco_bairro));
        clinica.setBairro(stringOfEditText);
        stringOfEditText = InsertSelectViewSupport.getStringOfEditText(findViewById(R.id.add_clinica_endereco_cidade));
        clinica.setBairro(stringOfEditText);

        clinicaDao.insertAll(clinica);
    }


}
