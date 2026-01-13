package com.example.worknutri.sqlite.domain.paciente;

import org.junit.Test;
import static org.junit.Assert.*;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;

public class PacienteTest {

    @Test
    public void defaultValues_areEmptyOrZero() {
        Paciente p = new Paciente();

        assertEquals(0L, p.getId());
        assertNull(p.getNomePaciente());
        assertEquals(0, p.getIdade());
        assertNull(p.getTelefone());
        assertNull(p.getEmail());
        assertNull(p.getNascimento());
        assertEquals(0, p.getPesoIdeal());
        assertEquals('\u0000', p.getGenero());
        assertEquals(0L, p.getClinicaId());
        assertNull(p.getObservacoes());
    }

    @Test
    public void testIdGetterSetter() {
        Paciente p = new Paciente();
        p.setId(123L);
        assertEquals(123L, p.getId());
    }

    @Test
    public void testNomePacienteGetterSetter() {
        Paciente p = new Paciente();
        p.setNomePaciente("João");
        assertEquals("João", p.getNomePaciente());
    }

    @Test
    public void testIdadeGetterSetter() {
        Paciente p = new Paciente();
        p.setIdade(45);
        assertEquals(45, p.getIdade());
    }

    @Test
    public void testTelefoneGetterSetter() {
        Paciente p = new Paciente();
        p.setTelefone("123456789");
        assertEquals("123456789", p.getTelefone());
    }

    @Test
    public void testEmailGetterSetter() {
        Paciente p = new Paciente();
        p.setEmail("joao@test.com");
        assertEquals("joao@test.com", p.getEmail());
    }

    @Test
    public void testNascimentoGetterSetter() {
        Paciente p = new Paciente();
        p.setNascimento("1980-01-01");
        assertEquals("1980-01-01", p.getNascimento());
    }

    @Test
    public void testPesoIdealGetterSetter() {
        Paciente p = new Paciente();
        p.setPesoIdeal(75);
        assertEquals(75, p.getPesoIdeal());
    }

    @Test
    public void testGeneroGetterSetter() {
        Paciente p = new Paciente();
        p.setGenero('M');
        assertEquals('M', p.getGenero());
    }

    @Test
    public void testClinicaIdGetterSetter() {
        Paciente p = new Paciente();
        p.setClinicaId(9L);
        assertEquals(9L, p.getClinicaId());
    }

    @Test
    public void testObservacoesGetterSetter() {
        Paciente p = new Paciente();
        p.setObservacoes("Observação de teste");
        assertEquals("Observação de teste", p.getObservacoes());
    }
}
