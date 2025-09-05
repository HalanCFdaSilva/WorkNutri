package com.example.worknutri.calcular;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CalculadorAntropometricoTest {
    CalculadorAntropometrico calculador;

    @Before
    public void generateCalculadorAntropometrico(){
        calculador = new CalculadorAntropometrico(70,1.8);
    }


    @Test
    public void calculaImcCorretamente(){
        CalculadorAntropometrico calculador = new CalculadorAntropometrico(67.5,1.5);
        double imc = Double.parseDouble(calculador.generateImc());
        Assert.assertEquals(30.0,imc,0.5);
    }

    @Test
    public void calculaTaxaMetabolicaBasalCorretamenteParaHomens(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        Assert.assertEquals(1755,tmb,0);

    }

    @Test
    public void calculaTaxaMetabolicaBasalCorretamenteParaMulheres(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        Assert.assertEquals(1533.5,tmb,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensSedentarios(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,0,'M'));
        Assert.assertEquals(2106,get,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensLevementeAtivos(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,1,'M'));
        Assert.assertEquals(2413.125,get,0);


    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensModeradamenteAtivos(){
        CalculadorAntropometrico calculador = new CalculadorAntropometrico(70,1.8);
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,2,'M'));
        Assert.assertEquals(2720.25,get,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaHomensAtividadeIntensa(){
        double tmb = Double.parseDouble(calculador.generateTMB('M',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,3,'M'));
        Assert.assertEquals(3334.5,get,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresSedentarias(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,0,'F'));
        Assert.assertEquals(1840.2,get,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresLevementeAtivas(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,1,'F'));
        Assert.assertEquals(2392.26,get,0);


    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresModeradamenteAtivas(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,2,'F'));
        Assert.assertEquals(2514.94,get,0);

    }

    @Test
    public void calculaGastoEnergeticoCorretamenteParaMulheresAtividadeIntensa(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,3,'F'));
        Assert.assertEquals(2790.97,get,0.001);

    }

    @Test
    public void calculaPesoIdealCorretamente(){
        Assert.assertEquals(70.47,CalculadorAntropometrico.generatePesoIdeal(1.8),0.001);

    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoIgualIdeal(){
        double pesoIdeal = CalculadorAntropometrico.generatePesoIdeal(1.8);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(0,bolsoCalculado,0.001);
    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoMaiorIdeal(){
        double pesoIdeal = CalculadorAntropometrico.generatePesoIdeal(1.8);
        calculador.setPeso(80);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(-1600,bolsoCalculado,0.001);
    }

    @Test
    public void calculaRegraDeBolsoCorretamenteParaPesoMenorIdeal(){
        double pesoIdeal = CalculadorAntropometrico.generatePesoIdeal(1.8);
        calculador.setPeso(60);
        double bolsoCalculado = Double.parseDouble(calculador.generateBolso(pesoIdeal));
        Assert.assertEquals(1800,bolsoCalculado,0.001);
    }

    @Test
    public void fazerOCalculoDeVentaCorretamenteParaUmKg(){
        double tmb = Double.parseDouble(calculador.generateTMB('F',25));
        double get = Double.parseDouble(calculador.generateGET(tmb,0,'F'));
        double venta = Double.parseDouble(calculador.generateVenta(get,1));
        Assert.assertEquals(0,venta,0.001);
    }
}
