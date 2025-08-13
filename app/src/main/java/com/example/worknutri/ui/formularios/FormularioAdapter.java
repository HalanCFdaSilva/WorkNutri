package com.example.worknutri.ui.formularios;

import android.content.Context;
import android.content.Intent;

import com.example.worknutri.ui.agendasFragment.FragmentSelectedActivity;
import com.example.worknutri.ui.agendasFragment.ScheduleActivity;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.ui.BottomMenuConfigurator;
import com.example.worknutri.ui.ExtrasActivities;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FormularioAdapter {

    private final Context context;
    private final AppDataBase dataBase;

    public FormularioAdapter(Context context) {
        this.context = context;
        dataBase = AppDataBase.getInstance(context);
    }

    public void configuraBottomNav(BottomNavigationView navigationView, Intent intentActivity) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context,
                navigationView);
        if(intentActivity.hasExtra(ExtrasActivities.PACIENTE_EXTRA.getKey())||
                intentActivity.hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey())){

            Intent intent = new Intent(getContext(), ScheduleActivity.class);
            intent.putExtra(ExtrasActivities.SCHEDULE_EXTRA.getKey(),true);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes, intent);

            intent = new Intent(getContext(), ScheduleActivity.class);
            intent.putExtra(ExtrasActivities.SCHEDULE_EXTRA.getKey(),false);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas, intent);
        }else{
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes, FragmentSelectedActivity.PACIENTE_FRAGMENT);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas, FragmentSelectedActivity.CLINICA_FRAGMENT);
        }
    }

    public Context getContext() {
        return context;
    }

    protected AppDataBase getDataBase() {
        return dataBase;
    }
}
