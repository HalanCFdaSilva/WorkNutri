package com.example.worknutri.calcular;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AntropometricCalculator {

    private double weight;
    private double height;

    public AntropometricCalculator(double weightInKg, double HeightInMeters) {
        this.weight = weightInKg;
        this.height = HeightInMeters;
    }
    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setHeight(double height) {
        this.height = height;
    }


    public String generateImc() {
        return String.valueOf(weight / (height * height));
    }

    public String generateTMB(char genero, int idade) {
        double tmb = (10 * weight);
        tmb += (6.25 * MeasureConverter.convertTo(MeasureTypes.GRAM_METER, height, MeasureTypes.CENTI));
        tmb -= (5 * idade);

        if (genero == 'M') {
            tmb += 5;
        } else if (genero == 'F') {
            tmb -= 161;
        }
        return String.valueOf(tmb);
    }

    public String generateGET(double taxaMetabolicaBasal, int nivelAtividadePaciente) {
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

    }

    public String generateBolso(double pesoIdeal) {
        if (pesoIdeal + 1 < weight) return String.valueOf(weight * 20 * -1);
        else if (pesoIdeal - 1 > weight) return String.valueOf(weight * 30);
        return "0";
    }

    public String generateVenta(double gastoEnergetico, int halfKgMoreLess) {
        double valorKg = (double) 770 / 3;
        double venta = gastoEnergetico - valorKg;
        venta -=  (((valorKg / 2)) * halfKgMoreLess);
        return String.valueOf(venta);
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
        return alturaEmMetros * alturaEmMetros * 22;
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

    public static double getIdealWeight(double heightInMeters) {
        return 22 * Math.pow(heightInMeters, 2);
    }
}
