package com.example.worknutri.ui.agendasFragment.filter.pojos;

import org.jetbrains.annotations.Nullable;

public enum OrderFilterSelectedsBy {
    NOME_ASC("NOME_ASC"),
    NOME_DESC("NOME_DESC"),

    // For PacienteFilter
    IMC_CATEGORY("IMC_CATEGORY"),
    HEIGHT("HEIGHT"),
    WEIGHT("WEIGHT"),
    YEAR("YEAR"),

    // For ClinicaFilter
    BAIRRO("BAIRRO"),
    CITY("CITY"),
    DAY_OF_WEEK("DAY_OF_WEEK"),
    NUMBER_OF_PATIENTS("NUMBER_OF_PATIENTS");




    private final String value;

    OrderFilterSelectedsBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Nullable
    public static OrderFilterSelectedsBy fromValue(String value) {
        for (OrderFilterSelectedsBy orderBy : values()) {
            if (orderBy.value.equals(value)) {
                return orderBy;
            }
        }
        return null;
    }
}
