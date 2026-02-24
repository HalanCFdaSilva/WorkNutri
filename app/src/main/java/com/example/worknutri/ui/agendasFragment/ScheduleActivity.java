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
import com.example.worknutri.databinding.ActivityMainBinding;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.forms.clinicForm.ClinicFormActivity;
import com.example.worknutri.ui.forms.patientForm.PatientFormActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ScheduleActivity extends AppCompatActivity {
    public static FragmentSelectedActivity fragmentSelected;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(binding.mainActivityNavView, navController);
        setNavGraph();

        fragmentSelected = getFragmentSelectedFromIntent(getIntent());

    }

    private void setNavGraph() {
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_activity_main);
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
        FloatingActionButton floatBottom = this.findViewById(R.id.main_activity_fab);
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
        BottomNavigationView navView = findViewById(R.id.main_activity_nav_view);
        navView.setSelectedItemId(bottomViewItemId);
    }


}