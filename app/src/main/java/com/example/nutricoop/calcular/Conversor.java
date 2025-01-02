package com.example.nutricoop.calcular;

public abstract class Conversor {
    public static String convertToGram(int currentMeasure,double weight){
        while(currentMeasure != 3){
            if(currentMeasure > 3){
                weight /= 10;
                currentMeasure--;
            } else {
                weight *= 10;
                currentMeasure++;
            }
        }
        return Double.toString(weight);

    }
}
