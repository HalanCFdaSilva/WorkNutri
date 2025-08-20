package com.example.worknutri.calcular;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalculadorAntropometrico {

    private double peso;
    private double altura;

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
            tmb = String.valueOf(66 + (13.7 * peso) + (5 * MeasureConverter.convertToMili(0, altura)) - (6.8 * idade));
        } else if (genero == 'F') {
            tmb = String.valueOf(655 + (9.6 * peso) + (1.8 * MeasureConverter.convertToMili(0, altura)) - (4.7 * idade));
        }
        return tmb;
    }

    public String generateGET(double taxaMetabolicaBasal, int nivelAtividadePaciente, char genero) {
        if (genero == 'M') {
            switch (nivelAtividadePaciente) {
                case 0:
                    return String.valueOf(taxaMetabolicaBasal * 1.2);
                case 1:
                    return String.valueOf(taxaMetabolicaBasal * 1.375);
                case 2:
                    return String.valueOf(taxaMetabolicaBasal * 1.55);
                default:
                    return String.valueOf(taxaMetabolicaBasal * 1.9);
            }
        } else if (genero == 'F') {
            switch (nivelAtividadePaciente) {
                case 0:
                    return String.valueOf(taxaMetabolicaBasal * 1.2);
                case 1:
                    return String.valueOf(taxaMetabolicaBasal * 1.56);
                case 2:
                    return String.valueOf(taxaMetabolicaBasal * 1.64);
                default:
                    return String.valueOf(taxaMetabolicaBasal * 1.82);
            }
        }
        return null;

    }

    public String generateBolso(double pesoIdeal) {
        if (pesoIdeal + 1 < peso ) return String.valueOf(peso * 20 * -1);
        else if (pesoIdeal - 1 > peso) return String.valueOf(peso * 30);
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

    public static double generatePesoIdeal(double alturaEmMetros){
        return alturaEmMetros * alturaEmMetros * 21.75;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public static int getYearFromDate(String dateString) {
        if (dateString.isBlank())
            return 0;
        LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        LocalDate dateNow = LocalDate.now();
        int age = dateNow.getYear() - date.getYear();
        if (date.getDayOfYear() > dateNow.getDayOfYear())
            age--;
        return age;
    }
}
