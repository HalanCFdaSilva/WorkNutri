package com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter;

public enum PacienteFilterOrderBy {
    NONE("NONE"),
    NOME_ASC("NOME_ASC"),
    NOME_DESC("NOME_DESC");

    private final String value;

    PacienteFilterOrderBy(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static PacienteFilterOrderBy fromValue(String value) {
        for (PacienteFilterOrderBy orderBy : values()) {
            if (orderBy.value.equals(value)) {
                return orderBy;
            }
        }
        return NONE; // Default case
    }
}
