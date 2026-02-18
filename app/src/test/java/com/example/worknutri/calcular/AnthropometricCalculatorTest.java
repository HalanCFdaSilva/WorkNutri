package com.example.worknutri.calcular;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class AnthropometricCalculatorTest {
    AntropometricCalculator calculador;

    @Before
    public void generateCalculadorAntropometrico(){
        calculador = new AntropometricCalculator(70,1.8);
    }

    @Test
    public void calculaImcCorretamente(){
        AntropometricCalculator calculador = new AntropometricCalculator(67.5,1.5);
        double imc = Double.parseDouble(calculador.generateImc());
        Assert.assertEquals(30.0,imc,0.5);
    }

    @Test
    public void calculaTaxaMetabolicaBasalCorretamenteParaHomens(){
        double tmbExpected = (10 * 70) + (6.25 * 180) - (5 * 25) + 5;
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        Assert.assertEquals(tmbExpected,tmb,0);

    }

    @Test
    public void calculaTaxaMetabolicaBasalCorretamenteParaMulheres(){
        double tmbExpected = (10 * 70) + (6.25 * 180) - (5 * 25) - 161;
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        Assert.assertEquals(tmbExpected,tmb,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensSedentarios(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double getExpected = tmb * 1.2;
        double getReceived = Double.parseDouble(calculador.generateGET(tmb,0));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensLevementeAtivos(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double getExpected = tmb * 1.375;
        double getReceived = Double.parseDouble(calculador.generateGET(tmb,1));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensModeradamenteAtivos(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double getExpected = tmb * 1.55;
        double getReceived = Double.parseDouble(calculador.generateGET(tmb,2));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensAtividadeIntensa(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double getExpected = tmb * 1.9;
        double getReceived = Double.parseDouble(calculador.generateGET(tmb,3));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresSedentarias(){
        double femaletmb = Double.parseDouble(calculador.generateTMB('F',25));
        double getExpected = femaletmb * 1.2;
        double getReceived = Double.parseDouble(calculador.generateGET(femaletmb,0));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresLevementeAtivas(){
        double femaletmb = Double.parseDouble(calculador.generateTMB('F',25));
        double getExpected = femaletmb * 1.375;
        double getReceived = Double.parseDouble(calculador.generateGET(femaletmb,1));
        Assert.assertEquals(getExpected,getReceived,0);


    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresModeradamenteAtivas(){
        double femaletmb = Double.parseDouble(calculador.generateTMB('F',25));
        double getExpected = femaletmb * 1.55;
        double getReceived = Double.parseDouble(calculador.generateGET(femaletmb,2));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresAtividadeIntensa(){
        double femaletmb = Double.parseDouble(calculador.generateTMB('F',25));
        double getExpected = femaletmb * 1.9;
        double getReceived = Double.parseDouble(calculador.generateGET(femaletmb,3));
        Assert.assertEquals(getExpected,getReceived,0);

    }

    @Test
    public void calculaPesoIdealCorretamente(){
        double idealWeight = 1.8 * 1.8 * 22;
        Assert.assertEquals(idealWeight, AntropometricCalculator.generatePesoIdeal(1.8),0.001);

    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoIgualIdeal(){
        double pesoIdeal = AntropometricCalculator.generatePesoIdeal(1.78);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(0,bolsoCalculado,0.001);
    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoMaiorIdeal(){
        double pesoIdeal = AntropometricCalculator.generatePesoIdeal(1.78);
        calculador.setWeight(80);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(-1600,bolsoCalculado,0.001);
    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoMenorIdeal(){
        double pesoIdeal = AntropometricCalculator.generatePesoIdeal(1.78);
        calculador.setWeight(60);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(1800,bolsoCalculado,0.001);
    }

    @Test
    public void fazerOCalculoDeVentaCorretamenteParaUmKg(){
        double get = getEnergicCoast();
        double ventaExpected = get - (double) 770/3;
        double venta = Double.parseDouble(calculador.generateVenta(get,0));
        Assert.assertEquals(ventaExpected,venta,0.001);
    }

    @Test
    public void calculateVentaCorrectlyForMoreThanOneKg(){
        double get = getEnergicCoast();
        double valorKg = (double)770 / 3;
        for (int i = 0; i <=3; i++) {
            double ventaExpected = get - valorKg  - (valorKg/2)*i;
            double venta = Double.parseDouble(calculador.generateVenta(get,i));
            Assert.assertEquals(ventaExpected,venta,0.001);
        }
    }

    private double getEnergicCoast() {
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        return Double.parseDouble(calculador.generateGET(tmb,0));
    }

    @Test
    public void calcuateYearFromDateCorrectly(){
        int yearExpected = LocalDate.now().getYear() - 1998;
        LocalDate now = LocalDate.now().withYear(1998);
        String born = now.minusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));

        Assert.assertEquals(yearExpected, AntropometricCalculator.getYearFromDate(born));
        born = now.plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        Assert.assertEquals(yearExpected-1, AntropometricCalculator.getYearFromDate(born));
    }


}
