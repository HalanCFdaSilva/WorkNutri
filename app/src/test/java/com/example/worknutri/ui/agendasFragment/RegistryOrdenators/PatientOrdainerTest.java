package com.example.worknutri.ui.agendasFragment.RegistryOrdenators;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class PatientOrdainerTest {

    private Paciente newPaciente(long id, String nome, int idade, long clinicaId) {
        Paciente p = new Paciente();
        p.setId(id);
        p.setNomePaciente(nome);
        p.setIdade(idade);
        p.setClinicaId(clinicaId);
        return p;
    }

    private Antropometria newAntropometria(int idPaciente, String imc, String altura, String peso) {
        Antropometria a = new Antropometria();
        a.setIdPaciente(idPaciente);
        a.setImc(imc);
        a.setAltura(altura);
        a.setPeso(peso);
        return a;
    }

    @Test
    public void byNameSortedAscending() {
        List<Paciente> list = new ArrayList<>();
        list.add(newPaciente(1, "Beta", 30, 1));
        list.add(newPaciente(2, "Alpha", 25, 1));

        PatientOrdainer ord = new PatientOrdainer(list);
        List<Paciente> result = ord.byName(false);

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getNomePaciente());
        assertEquals("Beta", result.get(1).getNomePaciente());
    }

    @Test
    public void byNameSortedDescending() {
        List<Paciente> list = new ArrayList<>();
        list.add(newPaciente(1, "Alpha", 30, 1));
        list.add(newPaciente(2, "Beta", 25, 1));

        PatientOrdainer ord = new PatientOrdainer(list);
        List<Paciente> result = ord.byName(true);

        assertEquals(2, result.size());
        assertEquals("Beta", result.get(0).getNomePaciente());
        assertEquals("Alpha", result.get(1).getNomePaciente());
    }

    @Test
    public void byBMICategoryVariedPriorities() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 20, 1)); // DEFICIT
        pacientes.add(newPaciente(2, "P2", 22, 1)); // NORMAL
        pacientes.add(newPaciente(3, "P3", 30, 1)); // SOBREPESO
        pacientes.add(newPaciente(4, "P4", 40, 1)); // no antropometria -> MAX

        List<Antropometria> antropometrias = new ArrayList<>();
        antropometrias.add(newAntropometria(1, "17.0", "1.70", "60.0"));
        antropometrias.add(newAntropometria(2, "22.0", "1.60", "55.0"));
        antropometrias.add(newAntropometria(3, "27.0", "1.80", "90.0"));

        PatientOrdainer ord = new PatientOrdainer(new ArrayList<>(pacientes));
        List<Paciente> result = ord.byBMICategory(antropometrias);

        assertEquals(4, result.size());
        // Expected priority order: DEFICIT(-1), NORMAL(0), SOBREPESO(1), MAX
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(4, result.get(3).getId());
    }

    @Test
    public void byBMICategoryEmptyAnthropometryKeepsOrder() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "First", 20, 1));
        pacientes.add(newPaciente(2, "Second", 25, 1));

        List<Antropometria> antropometrias = new ArrayList<>();

        PatientOrdainer ord = new PatientOrdainer(new ArrayList<>(pacientes));
        List<Paciente> result = ord.byBMICategory(antropometrias);

        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    public void byHeightSortedByValue() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 20, 1));
        pacientes.add(newPaciente(2, "P2", 20, 1));
        pacientes.add(newPaciente(3, "P3", 20, 1));

        List<Antropometria> antropometrias = new ArrayList<>();
        antropometrias.add(newAntropometria(1, "22.0", "1.90", "90.0")); // tallest
        antropometrias.add(newAntropometria(2, "22.0", "1.60", "60.0")); // shortest
        antropometrias.add(newAntropometria(3, "22.0", "1.75", "75.0")); // middle

        PatientOrdainer ord = new PatientOrdainer(new ArrayList<>(pacientes));
        List<Paciente> result = ord.byHeight(antropometrias);

        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    public void byWeightSortedByValue() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 20, 1));
        pacientes.add(newPaciente(2, "P2", 20, 1));
        pacientes.add(newPaciente(3, "P3", 20, 1));

        List<Antropometria> antropometrias = new ArrayList<>();
        antropometrias.add(newAntropometria(1, "22.0", "1.80", "80.0"));
        antropometrias.add(newAntropometria(2, "22.0", "1.70", "60.0"));
        antropometrias.add(newAntropometria(3, "22.0", "1.75", "70.0"));

        PatientOrdainer ord = new PatientOrdainer(new ArrayList<>(pacientes));
        List<Paciente> result = ord.byWeight(antropometrias);

        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    public void byAgeSortedAscending() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 50, 1));
        pacientes.add(newPaciente(2, "P2", 20, 1));
        pacientes.add(newPaciente(3, "P3", 35, 1));

        PatientOrdainer ord = new PatientOrdainer(new ArrayList<>(pacientes));
        List<Paciente> result = ord.byAge();

        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

}

