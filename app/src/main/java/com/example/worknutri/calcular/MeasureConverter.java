package com.example.worknutri.calcular;

public abstract class MeasureConverter {

    /**Converte um valor para a medida de grama ou metro sem alterar o valor original.
     * @param valueOriginal valor a ser modificado.
     * @param currentMeasure Medida atual do valor, onde 0 é grama ou metro, 1 é decigrama ou decímetro, 2 é centigrama ou centímetro, 3 é miligrama ou milímetro.
     * @return retorna o valor já modificado para grama ou metro apartir de uma nova variável*/
    public static double convertToGramOrMeters(int currentMeasure, double valueOriginal) {
        double value = valueOriginal;
        while (currentMeasure != 0) {
            value /= 10;
            currentMeasure--;
        }
        return value;

    }

    /**Converte o valor para a medida mili(milimetro,centimetro)
     * @param valueOriginal valor a ser modificado. Obs: Não é alterado
     * @param currentMeasure Medida atual do valor, onde 0 é grama ou metro, 1 é decigrama ou decímetro, 2 é centigrama ou centímetro, 3 é miligrama ou milímetro.
     * @return retorna o valor já modificado para mili em uma nova variável*/
    public static double convertToMili(int currentMeasure, double valueOriginal) {
        double value = valueOriginal;
        while (currentMeasure != 3) {
            if (currentMeasure < 3) {
                value *= 10;
                currentMeasure++;
            } else {
                value /= 10;
                currentMeasure--;
            }
        }
        return value;

    }
}
