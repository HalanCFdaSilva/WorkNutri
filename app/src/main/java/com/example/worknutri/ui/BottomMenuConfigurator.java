package com.example.worknutri.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.worknutri.ui.agendasFragment.FragmentSelectedActivity;
import com.example.worknutri.ui.agendasFragment.ScheduleActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomMenuConfigurator {
    private final Context context;
    private final BottomNavigationView navView;

    public BottomMenuConfigurator(Context context, BottomNavigationView navView) {
        this.context = context;
        this.navView = navView;
    }

    public void onClickInBottomAppBar(int idItemMenu, FragmentSelectedActivity fragmentSelectedActivity) {

        navView.getMenu().findItem(idItemMenu).setOnMenuItemClickListener(item -> {
            ScheduleActivity.fragmentSelected = fragmentSelectedActivity;
            ((Activity) context).finish();
            return false;
        });
    }

    public void onClickInBottomAppBar(int idItemMenu, Intent intent) {
        navView.getMenu().findItem(idItemMenu).setOnMenuItemClickListener(item -> {
            context.startActivities(new Intent[]{intent});
            return false;
        });
    }

    public Context getContext() {
        return context;
    }
}
