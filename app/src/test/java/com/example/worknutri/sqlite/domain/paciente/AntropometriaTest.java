package com.example.worknutri.sqlite.domain.paciente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;

import org.junit.Test;

public class AntropometriaTest {

    @Test
    public void defaultValues_onNewInstance() {
        Antropometria a = new Antropometria();

        assertEquals(0, a.getId());
        assertEquals(0, a.getIdPaciente());
        assertNull(a.getAltura());
        assertNull(a.getPeso());
        assertNull(a.getPesoIdeal());
        assertEquals(0, a.getIdade());
        assertNull(a.getImc());
        assertNull(a.getCircumferenciaBracoDir());
        assertNull(a.getCircumferenciaCoxaDir());
        assertNull(a.getCircumferenciaCintura());
        assertNull(a.getCircumferenciaAbdomen());
        assertNull(a.getCircumferenciaQuadril());
        assertNull(a.getTaxaMetabolica());
        assertNull(a.getValorMetabolico());
        assertNull(a.getRegraBolso());
        assertNull(a.getVenta());
        assertNull(a.getAgua());
    }

    @Test
    public void id_getterSetter() {
        Antropometria a = new Antropometria();
        a.setId(123);
        assertEquals(123, a.getId());
    }

    @Test
    public void idPaciente_getterSetter() {
        Antropometria a = new Antropometria();
        a.setIdPaciente(456);
        assertEquals(456, a.getIdPaciente());
    }

    @Test
    public void altura_getterSetter() {
        Antropometria a = new Antropometria();
        a.setAltura("1.75");
        assertEquals("1.75", a.getAltura());
    }

    @Test
    public void peso_getterSetter() {
        Antropometria a = new Antropometria();
        a.setPeso("70");
        assertEquals("70", a.getPeso());
    }

    @Test
    public void pesoIdeal_getterSetter() {
        Antropometria a = new Antropometria();
        a.setPesoIdeal("68");
        assertEquals("68", a.getPesoIdeal());
    }

    @Test
    public void idade_getterSetter() {
        Antropometria a = new Antropometria();
        a.setIdade(30);
        assertEquals(30, a.getIdade());
    }

    @Test
    public void imc_getterSetter() {
        Antropometria a = new Antropometria();
        a.setImc("22.9");
        assertEquals("22.9", a.getImc());
    }

    @Test
    public void circumferenciaBracoDir_getterSetter() {
        Antropometria a = new Antropometria();
        a.setCircumferenciaBracoDir("30");
        assertEquals("30", a.getCircumferenciaBracoDir());
    }

    @Test
    public void circumferenciaCoxaDir_getterSetter() {
        Antropometria a = new Antropometria();
        a.setCircumferenciaCoxaDir("50");
        assertEquals("50", a.getCircumferenciaCoxaDir());
    }

    @Test
    public void circumferenciaCintura_getterSetter() {
        Antropometria a = new Antropometria();
        a.setCircumferenciaCintura("80");
        assertEquals("80", a.getCircumferenciaCintura());
    }

    @Test
    public void circumferenciaAbdomen_getterSetter() {
        Antropometria a = new Antropometria();
        a.setCircumferenciaAbdomen("85");
        assertEquals("85", a.getCircumferenciaAbdomen());
    }

    @Test
    public void circumferenciaQuadril_getterSetter() {
        Antropometria a = new Antropometria();
        a.setCircumferenciaQuadril("95");
        assertEquals("95", a.getCircumferenciaQuadril());
    }

    @Test
    public void taxaMetabolica_getterSetter() {
        Antropometria a = new Antropometria();
        a.setTaxaMetabolica("1500");
        assertEquals("1500", a.getTaxaMetabolica());
    }

    @Test
    public void valorMetabolico_getterSetter() {
        Antropometria a = new Antropometria();
        a.setValorMetabolico("2000");
        assertEquals("2000", a.getValorMetabolico());
    }

    @Test
    public void regraBolso_getterSetter() {
        Antropometria a = new Antropometria();
        a.setRegraBolso("N/A");
        assertEquals("N/A", a.getRegraBolso());
    }

    @Test
    public void venta_getterSetter() {
        Antropometria a = new Antropometria();
        a.setVenta("valorVenta");
        assertEquals("valorVenta", a.getVenta());
    }

    @Test
    public void agua_getterSetter() {
        Antropometria a = new Antropometria();
        a.setAgua("2L");
        assertEquals("2L", a.getAgua());
    }

}
