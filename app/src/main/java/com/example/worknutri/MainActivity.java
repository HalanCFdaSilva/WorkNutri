package com.example.worknutri;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.worknutri.databinding.ActivityMainBinding;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.formularios.formularioClinica.FormularioClinicaActivity;
import com.example.worknutri.ui.formularios.formularioPaciente.FormularioPacienteActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    public static boolean isPaciente;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.mainActivityNavView, navController);
        MainActivity.isPaciente=getIntent().getBooleanExtra(ExtrasActivities.PACIENTE_BOOLEAN,true);

    }



    @Override
    protected void onStart() {
        super.onStart();
        configurefab();
        setNavGraph();
    }

    private void setNavGraph() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
        if (navHostFragment != null) {
            NavController controller = navHostFragment.getNavController();
            NavGraph navGraph = controller.getNavInflater().inflate(R.navigation.paciente_nav_start);
            if (!isPaciente) {
                navGraph = controller.getNavInflater().inflate(R.navigation.clinica_nav_start);
            }
            controller.setGraph(navGraph);
        }


    }

    private void configurefab() {
        FloatingActionButton floatBottom = this.findViewById(R.id.main_activity_fab);
        floatBottom.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), FormularioClinicaActivity.class);
            if (isPaciente) {
                intent = new Intent(getBaseContext(), FormularioPacienteActivity.class);
            }

            startActivities(new Intent[]{intent});

        });
    }


}