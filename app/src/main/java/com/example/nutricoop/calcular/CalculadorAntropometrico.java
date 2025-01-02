package com.example.nutricoop.calcular;

public abstract class CalculadorAntropometrico {

    public static String generateImc(double peso, double altura){
        return String.valueOf(peso/(altura*altura));
    }

    public static String generateTMB(double peso, int altura,char genero, int idade){
        String tmb = null;
        if (genero == 'M'){
            tmb = String.valueOf(66 +(13.7*peso) + (5.0 * altura) -(6.8*idade) );
        }else if (genero == 'F'){
            tmb = String.valueOf(655 +(9.6*peso) + (1.8 * altura) -(4.7*idade) );
        }
        return tmb;
    }
}
