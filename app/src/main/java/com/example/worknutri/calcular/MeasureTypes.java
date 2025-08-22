package com.example.worknutri.calcular;

public enum MeasureTypes {
    KILO(-3),
    HECTO(-2),
    DECA(-1),
    GRAM_METER(0),
    DECI(1),
    CENTI(2),
    MILI(3);
    private final int value;


    MeasureTypes(int value) {
        this.value = value;
    }
    public static MeasureTypes fromValue(int value) throws IllegalArgumentException{
        for (MeasureTypes type : MeasureTypes.values()) {
            if (type.value == value) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid measure type value: " + value);
    }

    /**
     * Retorna o valor inteiro associado ao tipo de medida.
     *
     * @return o valor inteiro do tipo de medida
     */
    public int getValue() {
        return value;
    }
}
