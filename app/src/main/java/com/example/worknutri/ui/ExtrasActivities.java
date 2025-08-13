package com.example.worknutri.ui;

public enum ExtrasActivities {
    SCHEDULE_EXTRA ("IS_PACIENTE"),
    PACIENTE_EXTRA ("PACIENTE"),
    CLINICA_EXTRA ("CLINICA");

    private final String key;
    ExtrasActivities(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }


}