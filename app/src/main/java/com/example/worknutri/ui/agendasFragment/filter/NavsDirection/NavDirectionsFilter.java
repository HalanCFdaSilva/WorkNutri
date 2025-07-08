package com.example.worknutri.ui.agendasFragment.filter.NavsDirection;

import androidx.navigation.NavDirections;

public abstract class NavDirectionsFilter implements NavDirections {

    private final int actionId;


    protected NavDirectionsFilter(int actionId) {
        this.actionId = actionId;
    }

    @Override
    public int getActionId() {
        return actionId;
    }
}
