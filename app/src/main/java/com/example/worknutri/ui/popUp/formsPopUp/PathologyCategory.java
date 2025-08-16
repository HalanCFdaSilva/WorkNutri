package com.example.worknutri.ui.popUp.formsPopUp;

import com.example.worknutri.R;

public enum PathologyCategory {
    ACTUAL_PATHOLOGY ("Patologia Atual:", R.string.actual_pathology_hint),
    URINE("Urina:", R.string.urine_hint),
    STOOL("Fezes:", R.string.stool_hint),
    SLUMBER("Sono:", R.string.slumber_hint),
    MEDICATION("Medicação:", R.string.medication_hint),
    SUPPLEMENT("Suplementação:", R.string.supplement_hint),
    ETHYLIC("Etilico:", R.string.ethylic_hint),
    SMOKER("Fumante:", R.string.smoker_hint),
    ALLERGY("Alergia:", R.string.allergy_hint),
    WATER("Água:", R.string.water_hint),
    SUGAR("Açúcar:", R.string.sugar_hint),
    ACTIVITY("Atividade Fisica:", R.string.activity_hint),;

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
