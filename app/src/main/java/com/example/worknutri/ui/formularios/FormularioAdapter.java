package com.example.worknutri.ui.formularios;

import android.content.Context;
import android.content.Intent;

import com.example.worknutri.MainActivity;
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
        if(intentActivity.hasExtra(ExtrasActivities.PACIENTE)||
                intentActivity.hasExtra(ExtrasActivities.CLINICA)){

            Intent intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra(ExtrasActivities.PACIENTE_BOOLEAN,true);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes, intent);

            intent = new Intent(getContext(), MainActivity.class);
            intent.putExtra(ExtrasActivities.PACIENTE_BOOLEAN,false);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas, intent);
        }else{
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes, true);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas, false);
        }
    }

    public Context getContext() {
        return context;
    }

    protected AppDataBase getDataBase() {
        return dataBase;
    }
}
