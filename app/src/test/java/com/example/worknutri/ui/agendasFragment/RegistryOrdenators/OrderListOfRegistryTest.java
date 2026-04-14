package com.example.worknutri.ui.agendasFragment.RegistryOrdenators;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OrderListOfRegistryTest {

    private Paciente newPaciente(long id, String nome, int idade) {
        Paciente p = new Paciente();
        p.setId(id);
        p.setNomePaciente(nome);
        p.setIdade(idade);
        p.setClinicaId(1);
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
    public void orderByNameAscending() {
        List<Paciente> list = new ArrayList<>();
        list.add(newPaciente(1, "Beta", 30));
        list.add(newPaciente(2, "Alpha", 25));

        PatientFilterPojo pojo = new PatientFilterPojo(list, new ArrayList<>(), new ArrayList<>());
        pojo.getState().setOrderBy(OrderFilterSelectedsBy.NAME_ASC);

        OrderListOfRegistry orderer = new OrderListOfRegistry();
        List<Paciente> result = orderer.orderToAgendaPacientes(pojo);

        assertEquals(2, result.size());
        assertEquals("Alpha", result.get(0).getNomePaciente());
        assertEquals("Beta", result.get(1).getNomePaciente());
    }

    @Test
    public void orderByNameDescending() {
        List<Paciente> list = new ArrayList<>();
        list.add(newPaciente(1, "Alpha", 30));
        list.add(newPaciente(2, "Beta", 25));

        PatientFilterPojo pojo = new PatientFilterPojo(list, new ArrayList<>(), new ArrayList<>());
        pojo.getState().setOrderBy(OrderFilterSelectedsBy.NAME_DESC);

        OrderListOfRegistry orderer = new OrderListOfRegistry();
        List<Paciente> result = orderer.orderToAgendaPacientes(pojo);

        assertEquals(2, result.size());
        assertEquals("Beta", result.get(0).getNomePaciente());
        assertEquals("Alpha", result.get(1).getNomePaciente());
    }

    @Test
    public void orderByAgeAscending() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 50));
        pacientes.add(newPaciente(2, "P2", 20));
        pacientes.add(newPaciente(3, "P3", 35));

        PatientFilterPojo pojo = new PatientFilterPojo(pacientes, new ArrayList<>(), new ArrayList<>());
        pojo.getState().setOrderBy(OrderFilterSelectedsBy.AGE);

        OrderListOfRegistry orderer = new OrderListOfRegistry();
        List<Paciente> result = orderer.orderToAgendaPacientes(pojo);

        assertEquals(3, result.size());
        assertEquals(2, result.get(0).getId());
        assertEquals(3, result.get(1).getId());
        assertEquals(1, result.get(2).getId());
    }

    @Test
    public void orderByBMICategoryDelegatesToOrdainer() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 20)); // DEFICIT
        pacientes.add(newPaciente(2, "P2", 22)); // NORMAL
        pacientes.add(newPaciente(3, "P3", 30)); // SOBREPESO
        pacientes.add(newPaciente(4, "P4", 40)); // no antropometria -> MAX

        List<Antropometria> antropometrias = new ArrayList<>();
        antropometrias.add(newAntropometria(1, "17.0", "1.70", "60.0"));
        antropometrias.add(newAntropometria(2, "22.0", "1.60", "55.0"));
        antropometrias.add(newAntropometria(3, "27.0", "1.80", "90.0"));

        PatientFilterPojo pojo = new PatientFilterPojo(pacientes, antropometrias, new ArrayList<>());
        pojo.getState().setOrderBy(OrderFilterSelectedsBy.IMC_CATEGORY);

        OrderListOfRegistry orderer = new OrderListOfRegistry();
        List<Paciente> result = orderer.orderToAgendaPacientes(pojo);

        assertEquals(4, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
        assertEquals(3, result.get(2).getId());
        assertEquals(4, result.get(3).getId());
    }

    @Test
    public void orderByHeightAndWeightDelegatesToOrdainer() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.add(newPaciente(1, "P1", 20));
        pacientes.add(newPaciente(2, "P2", 20));
        pacientes.add(newPaciente(3, "P3", 20));

        List<Antropometria> antropometrias = new ArrayList<>();
        antropometrias.add(newAntropometria(1, "22.0", "1.90", "90.0")); // tallest / heaviest
        antropometrias.add(newAntropometria(2, "22.0", "1.60", "60.0")); // shortest / lightest
        antropometrias.add(newAntropometria(3, "22.0", "1.75", "75.0")); // middle

        // Test height ordering
        PatientFilterPojo pojoHeight = new PatientFilterPojo(pacientes, antropometrias, new ArrayList<>());
        pojoHeight.getState().setOrderBy(OrderFilterSelectedsBy.HEIGHT);

        OrderListOfRegistry orderer = new OrderListOfRegistry();
        List<Paciente> resultHeight = orderer.orderToAgendaPacientes(pojoHeight);

        assertEquals(3, resultHeight.size());
        // shortest first (by design of PatientOrdainer.byHeight returns ascending by value)
        assertEquals(2, resultHeight.get(0).getId());
        assertEquals(3, resultHeight.get(1).getId());
        assertEquals(1, resultHeight.get(2).getId());

        // Test weight ordering
        PatientFilterPojo pojoWeight = new PatientFilterPojo(pacientes, antropometrias, new ArrayList<>());
        pojoWeight.getState().setOrderBy(OrderFilterSelectedsBy.WEIGHT);

        List<Paciente> resultWeight = orderer.orderToAgendaPacientes(pojoWeight);

        assertEquals(3, resultWeight.size());
        // lightest first
        assertEquals(2, resultWeight.get(0).getId());
        assertEquals(3, resultWeight.get(1).getId());
        assertEquals(1, resultWeight.get(2).getId());
    }
}
