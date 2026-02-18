package com.example.worknutri.calcular;

public abstract class MeasureConverter {

    /**Converte um valor para a medida de grama ou metro sem alterar o valor original.
     * @param valueOriginal valor a ser modificado.
     * @param currentMeasure Medida atual do valor, onde 0 é grama ou metro, 1 é decigrama ou decímetro, 2 é centigrama ou centímetro, 3 é miligrama ou milímetro.
     * @return retorna o valor já modificado para grama ou metro apartir de uma nova variável*/
    public static double convertToGramOrMeters(MeasureTypes currentMeasure, double valueOriginal) {
        double value = valueOriginal;
        MeasureTypes gram = MeasureTypes.GRAM_METER;
        while (currentMeasure != gram) {
            if (currentMeasure.getValue() > gram.getValue()) {
                value /= 10;
                currentMeasure = MeasureTypes.fromValue(currentMeasure.getValue() - 1);
            } else {
                value *= 10;
                currentMeasure = MeasureTypes.fromValue(currentMeasure.getValue() + 1);
            }
        }
        return value;

    }

    /**Converte o valor para a medida mili(milimetro,centimetro)
     * @param valueOriginal valor a ser modificado. Obs: Não é alterado
     * @return retorna o valor já modificado para mili em uma nova variável*/
    public static double convertToMili(MeasureTypes measureTypes, double valueOriginal) {
        double value = valueOriginal;
        while (measureTypes != MeasureTypes.MILI) {
                value /= 10;
                measureTypes = MeasureTypes.fromValue(measureTypes.getValue()+1);
        }
        return value;

    }

    public static double convertTo(MeasureTypes measureTypeActual, double valueOriginal,MeasureTypes measureTypeExpected) {
        double value = valueOriginal;
        while (measureTypeActual != measureTypeExpected) {
            if (measureTypeActual.getValue() > measureTypeExpected.getValue()) {
                value /= 10;
                measureTypeActual = MeasureTypes.fromValue(measureTypeActual.getValue() - 1);
            } else {
                value *= 10;
                measureTypeActual = MeasureTypes.fromValue(measureTypeActual.getValue() + 1);
            }
        }
        return value;

    }
}
