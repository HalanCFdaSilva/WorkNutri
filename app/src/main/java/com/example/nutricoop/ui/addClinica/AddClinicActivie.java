package com.example.nutricoop.ui.addClinica;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.nutricoop.MainActivity;
import com.example.nutricoop.R;
import com.example.nutricoop.databinding.ActivityMainBinding;
import com.example.nutricoop.ui.ExtrasActivities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AddClinicActivie extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_clinic_activity);


       this.onClickInBottomAppBar(R.id.navigation_pacientes,true);
       this.onClickInBottomAppBar(R.id.navigation_clinicas,false);
    }

    private void onClickInBottomAppBar(int idItemMenu, boolean isPaciente) {
        BottomNavigationView navView = findViewById(R.id.add_clinica_activy_activity_nav_view);
        navView.getMenu().findItem(idItemMenu).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra(ExtrasActivities.PACIENTE,isPaciente);
                startActivities(new Intent[]{intent});
                finish();
                return false;
            }
        });
    }
}
