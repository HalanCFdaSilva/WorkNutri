package com.example.worknutri.sqlite.domain.paciente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import org.junit.Test;

public class PatologiaTest {
    @Test
    public void defaultValues_onNewInstance() {
        Patologia p = new Patologia();

        // campos numéricos devem ser 0 por padrão
        assertEquals(0, p.getId());
        assertEquals(0, p.getIdPaciente());

        // campos de texto devem ser null por padrão
        assertNull(p.getPatologiaAtual());
        assertNull(p.getUrina());
        assertNull(p.getFezes());
        assertNull(p.getHoraSono());
        assertNull(p.getMedicacao());
        assertNull(p.getSuplemento());
        assertNull(p.getEtilico());
        assertNull(p.getFumante());
        assertNull(p.getAlergiaAlimentar());
        assertNull(p.getConsumoAgua());
        assertNull(p.getAcucar());
        assertNull(p.getAtividadeFisica());
    }

    @Test
    public void id_getterSetter() {
        Patologia p = new Patologia();
        p.setId(123);
        assertEquals(123, p.getId());
    }

    @Test
    public void idPaciente_getterSetter() {
        Patologia p = new Patologia();
        p.setIdPaciente(456);
        assertEquals(456, p.getIdPaciente());
    }

    @Test
    public void patologiaAtual_getterSetter() {
        Patologia p = new Patologia();
        p.setPatologiaAtual("Diabetes");
        assertEquals("Diabetes", p.getPatologiaAtual());
    }

    @Test
    public void urina_getterSetter() {
        Patologia p = new Patologia();
        p.setUrina("Normal");
        assertEquals("Normal", p.getUrina());
    }

    @Test
    public void fezes_getterSetter() {
        Patologia p = new Patologia();
        p.setFezes("Normal");
        assertEquals("Normal", p.getFezes());
    }

    @Test
    public void horaSono_getterSetter() {
        Patologia p = new Patologia();
        p.setHoraSono("22:30");
        assertEquals("22:30", p.getHoraSono());
    }

    @Test
    public void medicacao_getterSetter() {
        Patologia p = new Patologia();
        p.setMedicacao("Metformina");
        assertEquals("Metformina", p.getMedicacao());
    }

    @Test
    public void suplemento_getterSetter() {
        Patologia p = new Patologia();
        p.setSuplemento("Vitamina D");
        assertEquals("Vitamina D", p.getSuplemento());
    }

    @Test
    public void etilico_getterSetter() {
        Patologia p = new Patologia();
        p.setEtilico("Ocasional");
        assertEquals("Ocasional", p.getEtilico());
    }

    @Test
    public void fumante_getterSetter() {
        Patologia p = new Patologia();
        p.setFumante("Não");
        assertEquals("Não", p.getFumante());
    }

    @Test
    public void alergiaAlimentar_getterSetter() {
        Patologia p = new Patologia();
        p.setAlergiaAlimentar("Amendoim");
        assertEquals("Amendoim", p.getAlergiaAlimentar());
    }

    @Test
    public void consumoAgua_getterSetter() {
        Patologia p = new Patologia();
        p.setConsumoAgua("2L");
        assertEquals("2L", p.getConsumoAgua());
    }

    @Test
    public void acucar_getterSetter() {
        Patologia p = new Patologia();
        p.setAcucar("Baixo");
        assertEquals("Baixo", p.getAcucar());
    }

    @Test
    public void atividadeFisica_getterSetter() {
        Patologia p = new Patologia();
        p.setAtividadeFisica("Caminhada");
        assertEquals("Caminhada", p.getAtividadeFisica());
    }
}

