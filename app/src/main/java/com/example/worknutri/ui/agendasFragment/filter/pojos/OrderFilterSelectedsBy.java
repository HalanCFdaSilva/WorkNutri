package com.example.worknutri.ui.agendasFragment.filter.pojos;


public enum OrderFilterSelectedsBy {
    NAME_ASC("NOME_ASC"),
    NAME_DESC("NOME_DESC"),

    // For PacienteFilter
    IMC_CATEGORY("IMC_CATEGORY"),
    HEIGHT("ALTURA"),
    WEIGHT("PESO"),
    AGE("IDADE"),

    // For ClinicaFilter
    DISTRICT("BAIRRO"),
    CITY("CIDADE"),
    DAY_OF_WEEK("DIA_DA_SEMANA"),
    NUMBER_OF_PATIENTS("NUMERO_DE_PACIENTES");




    private final String value;

    OrderFilterSelectedsBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
