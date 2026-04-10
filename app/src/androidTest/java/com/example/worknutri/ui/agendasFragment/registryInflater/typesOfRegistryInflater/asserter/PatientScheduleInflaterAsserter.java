package com.example.worknutri.ui.agendasFragment.registryInflater.typesOfRegistryInflater.asserter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.view.ViewGroup;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;

public abstract class PatientScheduleInflaterAsserter {

    public static void assertLayout(ViewGroup layout, String[] categoriesNamesExpected,
                                    List<List<Paciente>> patientsExpected) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            ViewGroup category = (ViewGroup) layout.getChildAt(i);
            assertNotNull(category);
            assertCategory(category, categoriesNamesExpected[i]);
            assertCards(category, patientsExpected.get(i));
        }
    }

    private static void assertCategory(ViewGroup category, String categoryName) {
        assertNotNull(category);
        TextView title = category.findViewById(R.id.registry_letter_fragment_agenda_textview);
        assertEquals(categoryName, title.getText().toString());

    }

    private static void assertCards(ViewGroup category, List<Paciente> patientsExpectsInOrder) {
        ViewGroup layout = category.findViewById(R.id.registry_letter_fragment_linear_layout);
        assertNotNull(layout);
        assertEquals(patientsExpectsInOrder.size(), layout.getChildCount());
        for(int i = 0; i < layout.getChildCount(); i++) {
            Paciente patientExpected = patientsExpectsInOrder.get(i);
            ViewGroup card = (ViewGroup) layout.getChildAt(i);
            assertEquals(R.id.schedule_card_fragment_patient, card.getChildAt(0).getId());
            TextView textViewName = card.findViewById(R.id.registry_card_fragment_patient_textview);
            assertEquals(patientExpected.getNomePaciente(), textViewName.getText().toString());

        }
    }




    public static String buildExpectedTitle(Paciente paciente) {
        int categoryValor = AntropometricCalculator.getYearFromDate(paciente.getNascimento());
        int decimal = getDecimalValue(categoryValor);
        String decimalValue = String.valueOf(decimal);
        if (decimalValue.equals("0")) decimalValue = "";
        String title = String.format("%s0,00 - %s9,99 %s", decimalValue, decimalValue, "ANOS");
        // AgeScheduleInflater removes ",00" and ",99"
        return title.replace(",00", "").replace(",99", "");
    }

    private static int getDecimalValue(int ageCompare) {
        String compareString = String.valueOf(ageCompare);
        int decimal = 0;
        if (compareString.length() > 1)
            decimal = Integer.parseInt(compareString.substring(0,1));
        return decimal;
    }
}
