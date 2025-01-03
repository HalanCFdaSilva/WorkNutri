package com.example.nutricoop.ui.addClinica;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutricoop.R;
import com.example.nutricoop.databinding.ActivityMainBinding;
import com.example.nutricoop.ui.BottomMenuConfigurator;

public class AddClinicActivy extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clinic_activity);

        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(this,
                findViewById(R.id.add_clinica_activy_activity_nav_view));
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes,true);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas,false);

        this.addHour();
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


}
