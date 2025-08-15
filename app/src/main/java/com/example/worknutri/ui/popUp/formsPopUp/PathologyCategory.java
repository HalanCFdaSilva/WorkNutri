package com.example.worknutri.ui.popUp.formsPopUp;

public enum PathologyCategory {
    ACTUAL_PATHOLOGY,
    URINE,
    STOOL,
    SLUMBER,
    MEDICATION,
    SUPPLEMENT,
    ETHYLIC,
    SMOKER,
    ALLERGY,
    WATER,
    SUGAR,
    ACTIVITY;

    private final String name;
    private final int hint;

    PathologyCategory(String name, int hint) {
        this.name = name;
        this.hint = hint;
    }

    public String getName() {
        return name;
    }

    public int getHint() {
        return hint;
    }
}
