package com.example.worknutri.ui.popUp.pathology;

import com.example.worknutri.R;

public enum PathologyField {
    ACTUAL_PATHOLOGY ("Patologia Atual", R.string.actual_pathology_hint),
    URINE("Urina", R.string.urine_hint),
    STOOL("Fezes", R.string.stool_hint),
    SLUMBER("Sono", R.string.slumber_hint),
    MEDICATION("Medicação", R.string.medication_hint),
    SUPPLEMENT("Suplementação", R.string.supplement_hint),
    ETHYLIC("Etilico", R.string.ethylic_hint),
    SMOKER("Fumante", R.string.smoker_hint),
    ALLERGY("Alergia", R.string.allergy_hint),
    WATER("Água", R.string.water_hint),
    SUGAR("Açúcar", R.string.sugar_hint),
    ACTIVITY("Atividade Fisica", R.string.activity_hint);

    private final String name;
    private final int hint;

    PathologyField(String name, int hint) {
        this.name = name;
        this.hint = hint;
    }

    public static PathologyField from(String string) {
        for (PathologyField category : PathologyField.values()) {
            if (category.name.equalsIgnoreCase(string)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No PathologyCategory found for: " + string);
    }

    public String getUpperName() {
        return name.toUpperCase();
    }

    public String getName() {
        return name;
    }

    public int getHint() {
        return hint;
    }

}
