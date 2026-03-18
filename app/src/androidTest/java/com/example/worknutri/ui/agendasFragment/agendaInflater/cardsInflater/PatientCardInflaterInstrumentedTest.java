package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import static org.junit.Assert.*;

import android.content.Context;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailPaciente.PacienteDescriptionActivity;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatientCardInflaterInstrumentedTest {


    @Test
    public void inflateCardPopulatesTextView() {
        Context context = TestUtil.getThemedContext();

        PatientCardInflater inflater = new PatientCardInflater(context);
        Paciente paciente = TestEntityFactory.generatePatient();

        ViewGroup card = inflater.inflateCard(paciente);
        assertNotNull(card);

        TextView textView = card.findViewById(R.id.paciente_card_fragment_textview);
        assertNotNull(textView);
        assertEquals(paciente.getNomePaciente(), textView.getText().toString());
    }

    @Test
    public void configureOnClickInCardStartsPatientDescriptionActivityWithExtra() {
        Context base = InstrumentationRegistry.getInstrumentation().getTargetContext();
        RecordingContext recordingContext = new RecordingContext(base, R.style.Theme_NutriCoop);

        PatientCardInflater inflater = new PatientCardInflater(recordingContext);
        Paciente paciente = TestEntityFactory.generatePatient();

        ViewGroup card = inflater.inflateCard(paciente);
        assertNotNull(card);

        inflater.configureOnClickInCard(card, paciente);

        card.performClick();

        Intent[] intents = recordingContext.getLastStartedIntents();
        assertNotNull("Expected startActivities to be called", intents);
        assertTrue("Expected at least one intent", intents.length >= 1);

        Intent started = intents[0];
        assertNotNull(started.getComponent());
        assertEquals(PacienteDescriptionActivity.class.getName(), started.getComponent().getClassName());
        assertTrue(started.hasExtra(ExtrasActivities.PACIENTE_EXTRA.getKey()));
    }
}
