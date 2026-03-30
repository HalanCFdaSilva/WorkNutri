package com.example.worknutri.ui.agendasFragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.worknutri.R;
import com.example.worknutri.databinding.RegistryActivityBinding;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.forms.clinicForm.ClinicFormActivity;
import com.example.worknutri.ui.forms.patientForm.PatientFormActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RegistryActivity extends AppCompatActivity {
    public static FragmentSelectedActivity fragmentSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        RegistryActivityBinding binding = RegistryActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.registry_activity_nav_host_fragment);
        NavigationUI.setupWithNavController(binding.registryActivityNavView, navController);
        setNavGraph();

        fragmentSelected = getFragmentSelectedFromIntent(getIntent());

    }

    private void setNavGraph() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.registry_activity_nav_host_fragment);
        if (navHostFragment != null) {
            NavController controller = navHostFragment.getNavController();
            NavGraph navGraph = controller.getNavInflater().inflate(R.navigation.paciente_nav_start);
            controller.setGraph(navGraph);
        }
    }

    private FragmentSelectedActivity getFragmentSelectedFromIntent(Intent intent) {
        FragmentSelectedActivity selected = FragmentSelectedActivity.PACIENTE_FRAGMENT;
        if (intent.hasExtra(ExtrasActivities.SCHEDULE_EXTRA.getKey()))
            selected = (FragmentSelectedActivity) intent.getSerializableExtra(ExtrasActivities.SCHEDULE_EXTRA.getKey());
        return selected;
    }


    @Override
    protected void onStart() {
        super.onStart();
        configurefab();
        selectCorrectFragment();

    }

    private void configurefab() {
        FloatingActionButton floatBottom = this.findViewById(R.id.registry_activity_fab);
        floatBottom.setOnClickListener(v -> {
            Intent intent = new Intent(getBaseContext(), ClinicFormActivity.class);
            if (fragmentSelected == FragmentSelectedActivity.PACIENTE_FRAGMENT) {
                intent = new Intent(getBaseContext(), PatientFormActivity.class);
            }

            startActivities(new Intent[]{intent});

        });
    }

    private void selectCorrectFragment() {
        if (fragmentSelected == FragmentSelectedActivity.CLINICA_FRAGMENT)
            selectItemInBottomNavView(R.id.navigation_clinicas);
        else if (fragmentSelected == FragmentSelectedActivity.PACIENTE_FRAGMENT)
            selectItemInBottomNavView(R.id.navigation_pacientes);
    }

    private void selectItemInBottomNavView(int bottomViewItemId) {
        BottomNavigationView navView = findViewById(R.id.registry_activity_nav_view);
        navView.setSelectedItemId(bottomViewItemId);
    }


}