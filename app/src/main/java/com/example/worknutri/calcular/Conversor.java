package com.example.worknutri.calcular;

public abstract class Conversor {
    public static String convertToGram(int currentMeasure, double weight) {
        while (currentMeasure != 0) {
            weight /= 10;
            currentMeasure--;
        }
        return Double.toString(weight);

    }

    public static double convertToCm(int currentMeasure, double altura) {
        while (currentMeasure != 2) {
            if (currentMeasure < 2) {
                altura *= 10;
                currentMeasure++;
            } else {
                altura /= 10;
                currentMeasure--;
            }
        }
        return altura;

    }
}
