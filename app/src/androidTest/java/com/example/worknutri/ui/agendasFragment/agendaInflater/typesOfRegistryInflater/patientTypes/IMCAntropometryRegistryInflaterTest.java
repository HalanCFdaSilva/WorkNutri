package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.patientTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class IMCAntropometryRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaCreatesCategoriesAndAddsCards() {
        List<Paciente>  patientList  = PatientScheduleInflaterAsserter.generatePatientToTest();

        List<Antropometria> antropometriaList = PatientScheduleInflaterAsserter.generateAnthropometryToTest(patientList);


        // Prepare patients grouped by their IMC classification
        List<Paciente> normalList = List.of( patientList .get(0));
        List<Paciente> obesidadeList = List.of( patientList .get(1));
        List<Paciente> sobrepesoList = List.of( patientList .get(2));

        String[] categoriesNamesExpected = new String[] {
                ClassificacaoImc.tipoImc(Double.parseDouble(antropometriaList.get(0).getImc())).toString(),
                ClassificacaoImc.tipoImc(Double.parseDouble(antropometriaList.get(1).getImc())).toString(),
                ClassificacaoImc.tipoImc(Double.parseDouble(antropometriaList.get(2).getImc())).toString()
        };

        List<List<Paciente>> patientsExpectedInCards = List.of(normalList, obesidadeList, sobrepesoList);

        LinearLayout parent = new LinearLayout(context);
        IMCAntropometryRegistryInflater inflater = new IMCAntropometryRegistryInflater(context, antropometriaList);
        inflater.generateAgenda(parent,  patientList );

        assertEquals(3, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);
    }

    @Test
    public void ifMoreThenOneRegistryHasSameIMCClassificationTheyAreAddedInTheSameCategory() {
        List<Paciente>  patientList  = PatientScheduleInflaterAsserter.generatePatientToTest();

        List<Antropometria> antropometriaList = PatientScheduleInflaterAsserter.generateAnthropometryToTest(patientList);
        antropometriaList.get(0).setImc("18.5");
        antropometriaList.get(1).setImc("20.0");
        antropometriaList.get(2).setImc("24.99");

        String[] categoriesNamesExpected = new String[] {
                ClassificacaoImc.tipoImc(Double.parseDouble(antropometriaList.get(0).getImc())).toString()
        };

        List<List<Paciente>> patientsExpectedInCards = List.of( patientList );

        LinearLayout parent = new LinearLayout(context);
        IMCAntropometryRegistryInflater inflater = new IMCAntropometryRegistryInflater(context, antropometriaList);
        inflater.generateAgenda(parent,  patientList );

        assertEquals(1, parent.getChildCount());
        PatientScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, patientsExpectedInCards);
    }
}

