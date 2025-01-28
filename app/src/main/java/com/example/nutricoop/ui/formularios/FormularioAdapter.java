package com.example.nutricoop.ui.formularios;

import android.content.Context;

import com.example.nutricoop.R;
import com.example.nutricoop.sqlLite.database.AppDataBase;
import com.example.nutricoop.ui.BottomMenuConfigurator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class FormularioAdapter {

    private final Context context;
    private final AppDataBase dataBase;

    public FormularioAdapter(Context context) {
        this.context = context;
        dataBase = AppDataBase.getInstance(context);
    }

    public void configuraBottomNav(BottomNavigationView navigationView){
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context,
                navigationView);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_pacientes,true);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinicas,false);
    }

    public Context getContext() {
        return context;
    }

    protected AppDataBase getDataBase() {
        return dataBase;
    }
}
