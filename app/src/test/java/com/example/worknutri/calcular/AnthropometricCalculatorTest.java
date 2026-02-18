package com.example.worknutri.calcular;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AnthropometricCalculatorTest {
    AntropometricCalculator calculator;

    @Before
    public void setUp(){
        calculator = new AntropometricCalculator(70,1.8);
    }

    @Test
    public void calculateImcCorrectly(){
        AntropometricCalculator calculator = new AntropometricCalculator(67.5,1.5);
        double imc = Double.parseDouble(calculator.generateImc());
        Assert.assertEquals(30.0,imc,0.5);
    }

    @Test
    public void calculateMetabolicRateCorrectlyInMan(){
        double tmbExpected = (10 * 70) + (6.25 * 180) - (5 * 25) + 5;
        double tmb = Double.parseDouble(calculator.generateTMB('M',25));
        Assert.assertEquals(tmbExpected,tmb,0);

    }

    @Test
    public void calculateMetabolicRateCorrectlyInWoman(){
        double tmbExpected = (10 * 70) + (6.25 * 180) - (5 * 25) - 161;
        double tmb = Double.parseDouble(calculator.generateTMB('F',25));
        Assert.assertEquals(tmbExpected,tmb,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInMenSedentary(){
        double tmb = Double.parseDouble(calculator.generateTMB('M',25));
        double getExpected = tmb * 1.2;
        double getReceived = Double.parseDouble(calculator.generateGET(tmb,0));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInMenLightlyActive(){
        double tmb = Double.parseDouble(calculator.generateTMB('M',25));
        double getExpected = tmb * 1.375;
        double getReceived = Double.parseDouble(calculator.generateGET(tmb,1));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInInMenModeratelyActive(){
        double tmb = Double.parseDouble(calculator.generateTMB('M',25));
        double getExpected = tmb * 1.55;
        double getReceived = Double.parseDouble(calculator.generateGET(tmb,2));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInInMenIntenseActivity(){
        double tmb = Double.parseDouble(calculator.generateTMB('M',25));
        double getExpected = tmb * 1.9;
        double getReceived = Double.parseDouble(calculator.generateGET(tmb,3));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInInWomenSedentary(){
        double femaleTmb = Double.parseDouble(calculator.generateTMB('F',25));
        double getExpected = femaleTmb * 1.2;
        double getReceived = Double.parseDouble(calculator.generateGET(femaleTmb,0));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInInWomenLightlyActive(){
        double femaleTmb = Double.parseDouble(calculator.generateTMB('F',25));
        double getExpected = femaleTmb * 1.375;
        double getReceived = Double.parseDouble(calculator.generateGET(femaleTmb,1));
        Assert.assertEquals(getExpected,getReceived,0);


    }

    @Test
    public void calculateEnergyExpendCorrectlyInInWomenModeratelyActive(){
        double femaleTmb = Double.parseDouble(calculator.generateTMB('F',25));
        double getExpected = femaleTmb * 1.55;
        double getReceived = Double.parseDouble(calculator.generateGET(femaleTmb,2));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateEnergyExpendCorrectlyInInWomenIntenseActivity(){
        double femaleTmb = Double.parseDouble(calculator.generateTMB('F',25));
        double getExpected = femaleTmb * 1.9;
        double getReceived = Double.parseDouble(calculator.generateGET(femaleTmb,3));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculateIdealWeightCorrectly(){
        double idealWeight = 1.8 * 1.8 * 22;
        Assert.assertEquals(idealWeight, AntropometricCalculator.generatePesoIdeal(1.8),0.001);

    }

    @Test
    public void calculateThumbCorrectlyToWeightEqualIdealWeight(){
        double idealWeight = AntropometricCalculator.generatePesoIdeal(1.78);
        double thumbCalculate = Double.parseDouble(calculator.generateBolso(idealWeight));
        Assert.assertEquals(0,thumbCalculate,0.001);
    }

    @Test
    public void calculateThumbCorrectlyToWeightGreaterThanIdealWeight(){
        double idealWeight = AntropometricCalculator.generatePesoIdeal(1.78);
        calculator.setWeight(80);
        double thumbCalculate = Double.parseDouble(calculator.generateBolso(idealWeight));
        Assert.assertEquals(-1600,thumbCalculate,0.001);
    }

    @Test
    public void calculateThumbCorrectlyToWeightLessThanIdealWeight(){
        double idealWeight = AntropometricCalculator.generatePesoIdeal(1.78);
        calculator.setWeight(60);
        double thumbCalculate = Double.parseDouble(calculator.generateBolso(idealWeight));
        Assert.assertEquals(1800,thumbCalculate,0.001);
    }

    @Test
    public void calculateVentaCorrectlyToOneKg(){
        double get = getEnergyExpend();
        double ventaExpected = get - (double) 770/3;
        double venta = Double.parseDouble(calculator.generateVenta(get,0));
        Assert.assertEquals(ventaExpected,venta,0.001);
    }

    @Test
    public void calculateVentaCorrectlyForMoreThanOneKg(){
        double get = getEnergyExpend();
        double valorKg = (double)770 / 3;
        for (int i = 0; i <=3; i++) {
            double ventaExpected = get - valorKg  - (valorKg/2)*i;
            double venta = Double.parseDouble(calculator.generateVenta(get,i));
            Assert.assertEquals(ventaExpected,venta,0.001);
        }
    }

    private double getEnergyExpend() {
        double tmb = Double.parseDouble(calculator.generateTMB('F',25));
        return Double.parseDouble(calculator.generateGET(tmb,0));
    }

    @Test
    public void calculateYearFromDateCorrectly(){
        int yearExpected = LocalDate.now().getYear() - 1998;
        LocalDate now = LocalDate.now().withYear(1998);
        String born = now.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Assert.assertEquals(yearExpected, AntropometricCalculator.getYearFromDate(born));
        born = now.plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Assert.assertEquals(yearExpected-1, AntropometricCalculator.getYearFromDate(born));
    }


}
