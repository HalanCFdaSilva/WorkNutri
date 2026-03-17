package com.example.worknutri.ui.agendasFragment.scheduleOrdenators;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ClinicOrdainerTest {

    private Clinica newClinica(long id, String nome, String bairro, String cidade) {
        Clinica c = new Clinica();
        c.setId(id);
        c.setNome(nome);
        c.setBairro(bairro);
        c.setCidade(cidade);
        return c;
    }

    private Paciente newPaciente(long id, long clinicaId) {
        Paciente p = new Paciente();
        p.setId(id);
        p.setClinicaId(clinicaId);
        return p;
    }

    private DayOfWork newDayOfWork(long idClinica, String dayOfWeek) {
        DayOfWork d = new DayOfWork();
        d.setIdClinica(idClinica);
        d.setDayOfWeek(dayOfWeek);
        return d;
    }

    @Test
    public void byNameSortedAscending() {
        List<Clinica> list = new ArrayList<>();
        list.add(newClinica(1, "Beta", "B1", "C1"));
        list.add(newClinica(2, "Alpha", "B2", "C2"));

        ClinicOrdainer ord = new ClinicOrdainer(list);
        List<Clinica> result = ord.byName(false);

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getNome());
        assertEquals("Beta", result.get(1).getNome());
    }

    @Test
    public void byName_sortedDescending() {
        List<Clinica> list = new ArrayList<>();
        list.add(newClinica(1, "Alpha", "B1", "C1"));
        list.add(newClinica(2, "Beta", "B2", "C2"));

        ClinicOrdainer ord = new ClinicOrdainer(list);
        List<Clinica> result = ord.byName(true);

        assertEquals(2, result.size());
        assertEquals("Beta", result.get(0).getNome());
        assertEquals("Alpha", result.get(1).getNome());
    }

    @Test
    public void byDistrict_sortedAscending() {
        List<Clinica> list = new ArrayList<>();
        list.add(newClinica(1, "C1", "Zebra", "CityA"));
        list.add(newClinica(2, "C2", "Alpha", "CityB"));

        ClinicOrdainer ord = new ClinicOrdainer(list);
        List<Clinica> result = ord.byDistrict();

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getBairro());
        assertEquals("Zebra", result.get(1).getBairro());
    }

    @Test
    public void byCity_sortedAscending() {
        List<Clinica> list = new ArrayList<>();
        list.add(newClinica(1, "C1", "B1", "ZooCity"));
        list.add(newClinica(2, "C2", "B2", "AlphaCity"));

        ClinicOrdainer ord = new ClinicOrdainer(list);
        List<Clinica> result = ord.byCity();

        assertEquals(2, result.size());
        assertEquals("AlphaCity", result.get(0).getCidade());
        assertEquals("ZooCity", result.get(1).getCidade());
    }

    @Test
    public void byDayOfWeek_singleDayFilter() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(newClinica(1, "C1", "B1", "City1"));
        clinicas.add(newClinica(2, "C2", "B2", "City2"));

        List<DayOfWork> days = new ArrayList<>();
        days.add(newDayOfWork(1, "Monday"));
        days.add(newDayOfWork(2, "Tuesday"));

        ClinicOrdainer ord = new ClinicOrdainer(clinicas);
        List<Clinica> result = ord.byDayOfWeek(Collections.singletonList("Monday"), days);

        assertEquals(1, result.size());
        assertEquals(1, result.get(0).getId());
    }

    @Test
    public void byDayOfWeek_multipleDaysFilter() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(newClinica(1, "C1", "B1", "City1"));
        clinicas.add(newClinica(2, "C2", "B2", "City2"));
        clinicas.add(newClinica(3, "C3", "B3", "City3"));

        List<DayOfWork> days = new ArrayList<>();
        days.add(newDayOfWork(1, "Mon"));
        days.add(newDayOfWork(3, "Wed"));

        ClinicOrdainer ord = new ClinicOrdainer(clinicas);
        List<Clinica> result = ord.byDayOfWeek(Arrays.asList("Mon", "Wed"), days);

        assertEquals(2, result.size());
        List<Long> ids = Arrays.asList(result.get(0).getId(), result.get(1).getId());
        assertTrue(ids.contains(1L));
        assertTrue(ids.contains(3L));
    }

    @Test
    public void byDayOfWeek_noMatches_returnsEmpty() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(newClinica(1, "C1", "B1", "City1"));

        List<DayOfWork> days = new ArrayList<>();
        days.add(newDayOfWork(1, "Friday"));

        ClinicOrdainer ord = new ClinicOrdainer(clinicas);
        List<Clinica> result = ord.byDayOfWeek(Collections.singletonList("Monday"), days);

        assertTrue(result.isEmpty());
    }

    @Test
    public void byNumberPatients_emptyPatientList_keepsOrder() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(newClinica(1, "First", "B1", "City1"));
        clinicas.add(newClinica(2, "Second", "B2", "City2"));

        List<Paciente> pacientes = new ArrayList<>();

        ClinicOrdainer ord = new ClinicOrdainer(new ArrayList<>(clinicas));
        List<Clinica> result = ord.byNumberPatients(pacientes);

        // With empty pacientes the comparator returns 0 for all comparisons; sort is stable, so order should remain
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    public void byNumberPatients_variedCounts_sortedByCount() {
        List<Clinica> clinicas = new ArrayList<>();
        clinicas.add(newClinica(1, "C1", "B1", "City1"));
        clinicas.add(newClinica(2, "C2", "B2", "City2"));
        clinicas.add(newClinica(3, "C3", "B3", "City3"));

        List<Paciente> pacientes = new ArrayList<>();
        // Clinica 1 -> 2 patients
        pacientes.add(newPaciente(1, 1));
        pacientes.add(newPaciente(2, 1));
        // Clinica 2 -> 1 patient
        pacientes.add(newPaciente(3, 2));
        // Clinica 3 -> 0 patients

        ClinicOrdainer ord = new ClinicOrdainer(new ArrayList<>(clinicas));
        List<Clinica> result = ord.byNumberPatients(pacientes);

        assertEquals(3, result.size());
        // Sorted by count ascending: clinica 3 (0), clinica 2 (1), clinica 1 (2)
        assertEquals(3, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

}

