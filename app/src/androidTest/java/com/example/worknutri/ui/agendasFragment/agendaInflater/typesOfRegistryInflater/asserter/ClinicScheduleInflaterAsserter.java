package com.example.worknutri.ui.agendasFragment.agendaInflater.typesOfRegistryInflater.asserter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestEntityFactory;

import java.util.Arrays;
import java.util.List;

public abstract class ClinicScheduleInflaterAsserter {

    public static void assertLayout(ViewGroup layout, String[] categoriesNamesExpected, List<List<Clinica>> clinicsExpected) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            ViewGroup category = (ViewGroup) layout.getChildAt(i);
            assertNotNull(category);
            assertCategory(category, categoriesNamesExpected[i]);
            assertCards(category, clinicsExpected.get(i));
        }
    }

    private static void assertCategory(ViewGroup category, String categoryName) {
        assertNotNull(category);
        TextView title = category.findViewById(R.id.registry_letter_fragment_agenda_textview);
        assertEquals(categoryName, title.getText().toString());

    }

    private static void assertCards(ViewGroup category, List<Clinica> clinicsExpectsInOrder) {
        ViewGroup layout = category.findViewById(R.id.registry_letter_fragment_linear_layout);
        assertNotNull(layout);
        assertEquals(clinicsExpectsInOrder.size(), layout.getChildCount());
        for(int i = 0; i < layout.getChildCount(); i++) {
            Clinica clinicExpected = clinicsExpectsInOrder.get(i);
            ViewGroup card = (ViewGroup) layout.getChildAt(i);
            assertEquals(R.id.registry_card_fragment_clinic, card.getChildAt(0).getId());
            TextView textViewName = card.findViewById(R.id.registry_card_fragment_clinic_name);
            assertEquals(clinicExpected.getNome(), textViewName.getText().toString());
            TextView textViewCity = card.findViewById(R.id.registry_card_fragment_clinic_city);
            assertEquals(clinicExpected.getCidade(), textViewCity.getText().toString());
            TextView textViewNeighborHood = card.findViewById(R.id.registry_card_fragment_clinic_neighborhood);
            assertEquals(clinicExpected.getBairro(), textViewNeighborHood.getText().toString());

        }
    }

    public static List<Clinica> generateClinicsToTest(){
        Clinica c1 = TestEntityFactory.generateClinic();
        c1.setId(1); c1.setBairro("Boa Viagem"); c1.setCidade("Recife"); c1.setNome("Alpha Clinic");
        Clinica c2 = TestEntityFactory.generateClinic();
        c2.setId(2); c2.setBairro("boa viagem"); c2.setCidade("Ramá"); c2.setNome("Beta Clinic");
        Clinica c3 = TestEntityFactory.generateClinic();
        c3.setId(3); c3.setBairro("Casa Amarela"); c3.setCidade("Olinda"); c3.setNome("Gamma Clinic");

        return Arrays.asList(c1, c2, c3);
    }
}
