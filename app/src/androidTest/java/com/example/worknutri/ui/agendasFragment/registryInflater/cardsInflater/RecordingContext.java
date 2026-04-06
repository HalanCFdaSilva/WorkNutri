package com.example.worknutri.ui.agendasFragment.registryInflater.cardsInflater;

import android.content.Context;
import android.content.Intent;
import android.view.ContextThemeWrapper;

public class RecordingContext extends ContextThemeWrapper {
    private Intent[] lastStartedIntents;

    RecordingContext(Context base, int themeResId) {
        super(base, themeResId);
    }

    @Override
    public void startActivities(Intent[] intents) {
        this.lastStartedIntents = intents;
    }

    Intent[] getLastStartedIntents() {
        return lastStartedIntents;
    }
}

