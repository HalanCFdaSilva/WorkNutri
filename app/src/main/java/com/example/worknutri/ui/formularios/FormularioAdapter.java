package com.example.worknutri.ui.formularios;

import android.content.Context;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.ui.BottomMenuConfigurator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FormularioAdapter {

    private final Context context;
    private final AppDataBase dataBase;

    public FormularioAdapter(Context context) {
        this.context = context;
        dataBase = AppDataBase.getInstance(context);
    }

    public void configuraBottomNav(BottomNavigationView navigationView) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context,
                navigationView);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes, true);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas, false);
    }

    public Context getContext() {
        return context;
    }

    protected AppDataBase getDataBase() {
        return dataBase;
    }
}
