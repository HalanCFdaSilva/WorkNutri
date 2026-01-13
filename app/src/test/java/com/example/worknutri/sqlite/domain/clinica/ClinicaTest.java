package com.example.worknutri.sqlite.domain.clinica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;



import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import org.junit.Test;

import java.util.Objects;

public class ClinicaTest {

    @Test
    public void defaultIdIsZero() {
        Clinica c = new Clinica();
        assertEquals(0L, c.getId());
    }

    @Test
    public void gettersAndSettersWork() {
        Clinica c = new Clinica();

        c.setId(42L);
        c.setNome("Nome Teste");
        c.setTelefone1("111222333");
        c.setEmail("teste@example.com");
        c.setRua("Rua A");
        c.setNumero(10);
        c.setComplemento("Apto 1");
        c.setCodigoPostal("12345-678");
        c.setBairro("Bairro");
        c.setCidade("Cidade");
        c.setEstado("Estado");

        assertEquals(42L, c.getId());
        assertEquals("Nome Teste", c.getNome());
        assertEquals("111222333", c.getTelefone1());
        assertEquals("teste@example.com", c.getEmail());
        assertEquals("Rua A", c.getRua());
        assertEquals(10, c.getNumero());
        assertEquals("Apto 1", c.getComplemento());
        assertEquals("12345-678", c.getCodigoPostal());
        assertEquals("Bairro", c.getBairro());
        assertEquals("Cidade", c.getCidade());
        assertEquals("Estado", c.getEstado());
    }

    @Test
    public void equalsReturnsTrueForSameId() {
        Clinica a = new Clinica();
        Clinica b = new Clinica();
        a.setId(5L);
        b.setId(5L);

        assertEquals(a, b);
        assertEquals(b, a);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalsReturnsFalseForDifferentId() {
        Clinica a = new Clinica();
        Clinica b = new Clinica();
        a.setId(1L);
        b.setId(2L);

        assertNotEquals(a, b);
        assertNotEquals(b, a);
        // hashCodes can collide but usually differ for different boxed longs
        assertNotEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void equalsHandlesNullAndDifferentClass() {
        Clinica a = new Clinica();
        a.setId(1L);

        assertNotEquals(null, a);
    }

    @Test
    public void hashCodeMatchesObjectsHashCodeOfId() {
        Clinica c = new Clinica();
        c.setId(123L);

        int expected = Objects.hashCode(123L);
        assertEquals(expected, c.hashCode());
    }


}

