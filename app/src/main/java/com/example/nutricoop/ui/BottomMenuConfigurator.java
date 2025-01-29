package com.example.nutricoop.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.nutricoop.MainActivity;
import com.example.nutricoop.sqlLite.clinica.domain.Clinica;
import com.example.nutricoop.ui.formularios.formularioClinica.FormularioClinicaAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuConfigurator {
    private final Context context;
    private final BottomNavigationView navView;

    public BottomMenuConfigurator(Context context, BottomNavigationView navView) {
        this.context = context;
        this.navView = navView;
    }

    public void onClickInBottomAppBar(int idItemMenu, boolean isPaciente) {

        navView.getMenu().findItem(idItemMenu).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                Intent intent = new Intent(context, MainActivity.class);
                intent.putExtra(ExtrasActivities.PACIENTE_BOOLEAN,isPaciente);
                context.startActivities(new Intent[]{intent});
                ((Activity)context).finish();

                return false;
            }
        });
    }

    public void onClickInBottomAppBar(int idItemMenu, Intent intent){
        navView.getMenu().findItem(idItemMenu).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {

                context.startActivities(new Intent[]{intent});
                ((Activity)context).finish();

                return false;
            }
        });
    }

    public BottomNavigationView getNavView() {
        return navView;
    }

    public Context getContext() {
        return context;
    }
}
