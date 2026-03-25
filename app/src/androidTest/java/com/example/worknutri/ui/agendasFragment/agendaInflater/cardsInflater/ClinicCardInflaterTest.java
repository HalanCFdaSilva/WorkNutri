package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailClinica.ClinicDescriptionActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ClinicCardInflaterTest {

    private ClinicCardInflater clinicCardInflater;



    @Before
    public void setUp() {
        Context context = TestUtil.getThemedContext();
        clinicCardInflater = new ClinicCardInflater(context);
    }

    @Test
    public void inflateCardPopulatesFieldsAndHidesCommaWhenNeighborhoodOrCityIsBlank() {

        Clinica clinicExpected = TestEntityFactory.generateClinic();

        clinicExpected.setBairro("");
        ViewGroup card = clinicCardInflater.inflateCard(clinicExpected);
        compareClinicCardWithClinicData(clinicExpected, card);

        // now make city blank too and verify virgula still gone
        clinicExpected.setBairro("Bairro");
        clinicExpected.setCidade("");
        ViewGroup card2 = clinicCardInflater.inflateCard(clinicExpected);
        compareClinicCardWithClinicData(clinicExpected,card2);

    }

    private void compareClinicCardWithClinicData(Clinica clinic, ViewGroup card) {
        TextView name = card.findViewById(R.id.card_fragment_clinic_name);
        assertEquals(clinic.getNome(), name.getText().toString());

        TextView street = card.findViewById(R.id.card_fragment_clinic_street);
        assertEquals(clinic.getRua(), street.getText().toString());

        TextView neighborhood = card.findViewById(R.id.card_fragment_clinic_neighborhood);
        assertEquals(clinic.getBairro(), neighborhood.getText().toString());

        TextView city = card.findViewById(R.id.card_fragment_clinic_city);
        assertEquals(clinic.getCidade(), city.getText().toString());

        View comma = card.findViewById(R.id.card_fragment_clinic_comma);
        assertEquals(View.GONE, comma.getVisibility());
    }

    @Test
    public void configureOnclickInCardOnClickStartsClinicDescriptionActivityWithExtra() {
        Context base = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RecordingContext recordingContext = new RecordingContext(base, R.style.Theme_NutriCoop);

        ClinicCardInflater inflater = new ClinicCardInflater(recordingContext);

        Clinica clinic = TestEntityFactory.generateClinic();


        ViewGroup card = inflater.inflateCard( clinic);
        assertNotNull(card);
        inflater.configureOnClickInCard(card, clinic);



        card.performClick();

        Intent[] intents = recordingContext.getLastStartedIntents();
        assertNotNull("Expected startActivities to be called", intents);
        assertTrue("Expected at least one intent", intents.length >= 1);

        Intent started = intents[0];
        assertNotNull(started.getComponent());
        assertEquals(ClinicDescriptionActivity.class.getName(), started.getComponent().getClassName());
        assertTrue(started.hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey()));
    }
}
