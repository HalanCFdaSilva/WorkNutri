package com.example.worknutri.calcular;

import android.util.Log;

public class CalculadorAntropometrico {

    private final double peso;
    private final double altura;

    public CalculadorAntropometrico(double pesoAtual, double alturaEmMetros) {
        this.peso = pesoAtual;
        this.altura = alturaEmMetros;
    }

    public String generateImc() {
        return String.valueOf(peso / (altura * altura));
    }

    public String generateTMB(char genero, int idade) {
        String tmb = null;
        if (genero == 'M') {
            tmb = String.valueOf(66 + (13.7 * peso) + (5 * Conversor.convertToCm(0, altura)) - (6.8 * idade));
        } else if (genero == 'F') {
            tmb = String.valueOf(655 + (9.6 * peso) + (1.8 * Conversor.convertToCm(0, altura)) - (4.7 * idade));
        }
        return tmb;
    }

    public String generateGET(String taxaMetabolicaBasal, int nivelAtividadePaciente, char genero) {
        double tmb = Double.valueOf(taxaMetabolicaBasal);
        if (genero == 'M') {
            switch (nivelAtividadePaciente) {
                case 0:
                    return String.valueOf(tmb * 1.2);
                case 1:
                    return String.valueOf(tmb * 1.375);
                case 2:
                    return String.valueOf(tmb * 1.55);
                default:
                    return String.valueOf(tmb * 1.9);
            }
        } else if (genero == 'F') {
            switch (nivelAtividadePaciente) {
                case 2:
                    return String.valueOf(tmb * 1.64);
                case 3:
                    return String.valueOf(tmb * 1.82);
                default:
                    return String.valueOf(tmb * 1.56);
            }
        }
        return null;

    }

    public String generateBolso(double pesoIdeal) {
        if (pesoIdeal < peso) return String.valueOf(peso * 20 * -1);
        else if (pesoIdeal > peso) return String.valueOf(peso * 30);
        return "0";
    }

    public String generateVenta(double gastoEnergetico, int kgExtraAPerder) {
        double valorKg = (double) 770 / 3;
        return String.valueOf((gastoEnergetico - valorKg - ((valorKg / 2) * kgExtraAPerder)));
    }

    public int generateAgua(int idade, double peso) {

        int kg = (int) peso;
        int aguaInMl;
        if (idade <= 17) aguaInMl = 40 * kg;
        else if (idade <= 55) aguaInMl = 35 * kg;
        else if (idade <= 65) aguaInMl = 30 * kg;
        else aguaInMl = 25 * kg;

        kg = (int) ((peso - kg) % 10) * 10;
        if (kg > 0) {
            aguaInMl += this.generateAgua(idade, kg);
        }
        return aguaInMl;
    }
}
