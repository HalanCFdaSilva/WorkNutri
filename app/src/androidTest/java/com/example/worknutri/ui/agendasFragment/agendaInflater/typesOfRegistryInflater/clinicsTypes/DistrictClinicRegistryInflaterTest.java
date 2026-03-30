package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.clinicsTypes;

import android.content.Context;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DistrictClinicRegistryInflaterTest {

    private final Context context = TestUtil.getThemedContext();

    @Test
    public void generateAgendaGroupsByDistrictAndAddsCards() {

        List<Clinica> list = ClinicScheduleInflaterAsserter.generateClinicsToTest();
        String[] categoriesNamesExpected = {list.get(0).getBairro().toUpperCase(), list.get(2).getBairro().toUpperCase()};
        List<List<Clinica>> clinicsExpectedInCards = new ArrayList<>();
        clinicsExpectedInCards.add(Arrays.asList(list.get(0),list.get(1)));
        clinicsExpectedInCards.add(List.of(list.get(2)));

        DistrictClinicRegistryInflater inflater = new DistrictClinicRegistryInflater(context);
        LinearLayout parent = new LinearLayout(context);
        inflater.generateAgenda(parent, list);

        assertEquals(2, parent.getChildCount());
        ClinicScheduleInflaterAsserter.assertLayout(parent, categoriesNamesExpected, clinicsExpectedInCards);
    }

}

