package com.example.worknutri;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.worknutri.databinding.ActivityMainBinding;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.formularios.formularioClinica.FormularioClinicaActivity;
import com.example.worknutri.ui.formularios.formularioPaciente.FormularioPacienteActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static boolean isPaciente;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        BottomNavigationView navView = findViewById(R.id.main_activity_nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_pacientes, R.id.placeholder, R.id.navigation_clinicas)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.mainActivityNavView, navController);

        getExtra(savedInstanceState);
        configurefab();


    }

    private void getExtra(Bundle bundle) {
        Intent intent = getIntent();
        isPaciente = intent.getBooleanExtra(ExtrasActivities.PACIENTE_BOOLEAN, true);
        if (!isPaciente) {

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_clinicas, false)
                    .build();

            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
            navController.navigate(R.id.action_navigation_pacientes_to_navigation_clinicas,
                    bundle, navOptions);


        }
    }

    private void configurefab() {
        FloatingActionButton floatBottom = this.findViewById(R.id.main_activity_fab);
        floatBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), FormularioClinicaActivity.class);
                if (isPaciente) {
                    intent = new Intent(getBaseContext(), FormularioPacienteActivity.class);
                }

                startActivities(new Intent[]{intent});

            }


        });
    }


}