package com.example.nutricoop;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.nutricoop.ui.addClinica.AddClinicActivy;
import com.example.nutricoop.ui.addPaciente.AddPacienteActiivie;
import com.example.nutricoop.ui.ExtrasActivities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.nutricoop.databinding.ActivityMainBinding;
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
                R.id.navigation_pacientes,R.id.placeholder, R.id.navigation_clinicas)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.mainActivityNavView, navController);

        getExtra(savedInstanceState);
        configurefab();


    }

    private void getExtra(Bundle bundle) {
        Intent intent = getIntent();
        isPaciente = intent.getBooleanExtra(ExtrasActivities.PACIENTE,true);
        if (!isPaciente){

            NavOptions navOptions = new NavOptions.Builder()
                    .setPopUpTo(R.id.navigation_clinicas, true)
                    .build();

            Navigation.findNavController(this, R.id.nav_host_fragment_activity_main)
                    .navigate(R.id.action_navigation_pacientes_to_navigation_clinicas,
                            bundle, navOptions);



        }
    }

    private void configurefab(){
        FloatingActionButton floatBottom = this.findViewById(R.id.main_activity_fab);
        floatBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddClinicActivy.class);;
                if(isPaciente){
                    intent = new Intent(getBaseContext(), AddPacienteActiivie.class);
                }

                startActivities(new Intent[]{intent});

            }


        });
    }









}