package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;

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


}
