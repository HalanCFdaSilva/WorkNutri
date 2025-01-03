package com.example.nutricoop.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;

import com.example.nutricoop.MainActivity;
import com.example.nutricoop.R;
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
                intent.putExtra(ExtrasActivities.PACIENTE,isPaciente);
                context.startActivities(new Intent[]{intent});
                ((Activity)context).finish();

                return false;
            }
        });
    }
}
