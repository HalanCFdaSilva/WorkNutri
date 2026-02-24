package com.example.worknutri.ui.forms.formInserters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.formularios.formInserters.PathologyFormInserter;
import com.example.worknutri.ui.formularios.InvalidViewGroupIdException;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PathologyFormInserterTest {

    private Context context;
    private ViewGroup viewGroup;
    private Patologia pathology;
    private PathologyFormInserter inserter;
    private List<PathologyField> pathologies;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);
        pathology = new Patologia();
        pathologies = new ArrayList<>(Arrays.asList(PathologyField.values()));
        inserter = PathologyFormInserter.create(viewGroup, pathologies);
    }

    @Test
    public void getViewGroupExpectedIdIsCorrect() {
        assertEquals(R.id.formulario_paciente_patologia_layout_content, PathologyFormInserter.getViewGroupIdExpected());
    }

    @Test
    public void createThrowsWhenViewGroupIdInvalid_andReturnsInstanceWhenValid() {
        // Inflar um container base
        ViewGroup root = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                new LinearLayout(context), false);
        checkNotThrowExceptionAndGenerateObject(R.id.formulario_paciente_patologia_layout, root);
        checkNotThrowExceptionAndGenerateObject(R.id.formulario_paciente_patologia_layout_content, root);
        checkNotThrowExceptionAndGenerateObject(R.id.form_patient_activity, root);
        root.setId(R.id.formulario_paciente_dados_pessoais_layout);
        assertThrows("create() not throws InvalidViewGroupIdException to viewGroup with id invalid",
                InvalidViewGroupIdException.class, () -> PathologyFormInserter.create(root, pathologies));

    }

    private void checkNotThrowExceptionAndGenerateObject(int id, ViewGroup root) {
        root.setId(id);
        try {
            PathologyFormInserter created = PathologyFormInserter.create(root, pathologies);
            assertNotNull("create should return one instance when ViewGroup id is válid (id=" + id + ")", created);
        } catch (InvalidViewGroupIdException e) {
            throw new AssertionError("create() throws InvalidViewGroupIdException to valid id: " + id, e);
        }
    }

    @Test
    public void insertViewGroupInEntityPopulatesPatologiaFromUiForAllValidIds() {
        int[] validIds = new int[] {
                R.id.formulario_paciente_patologia_layout_content,
                R.id.formulario_paciente_patologia_layout,
                R.id.form_patient_activity
        };

        Patologia expected = TestEntityFactory.generatePatologia();
        for (int id : validIds) {
            pathologies = new ArrayList<>(Arrays.asList(PathologyField.values()));
            ViewGroup viewInflated = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.formulario_paciente_activity,
                    new LinearLayout(context), false);
            ViewGroup root = viewInflated.findViewById(id);


            Patologia target = new Patologia();
            PathologyFormInserter localInserter = PathologyFormInserter.create(root, pathologies);

            ViewGroup viewGroupWithCards= root;
            if (root.getId() != R.id.formulario_paciente_patologia_layout_content)
                viewGroupWithCards = root.findViewById(R.id.formulario_paciente_patologia_layout_content);

            assertNotNull("content container must exist for id=" + id, viewGroupWithCards);

            // populate views and execute insertion
            generateViewsFrompathologyExpected(viewGroupWithCards, expected);
            localInserter.insertViewGroupInEntity(target);
            checkIfPathologyIsCorrect(expected,target);
        }
    }

    private void generateViewsFrompathologyExpected(ViewGroup viewGroupWithCards, Patologia pathologyExpected) {
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.MEDICATION,
                pathologyExpected.getMedicacao()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.SUPPLEMENT,
                pathologyExpected.getSuplemento()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.ACTIVITY,
                pathologyExpected.getAtividadeFisica()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.ACTUAL_PATHOLOGY,
                pathologyExpected.getPatologiaAtual()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.URINE,
                pathologyExpected.getUrina()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.STOOL,
                pathologyExpected.getFezes()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.SLUMBER,
                pathologyExpected.getHoraSono()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.ETHYLIC,
                pathologyExpected.getEtilico()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.SMOKER,
                pathologyExpected.getFumante()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.ALLERGY,
                pathologyExpected.getAlergiaAlimentar()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.WATER,
                pathologyExpected.getConsumoAgua()));
        viewGroupWithCards.addView(generateCardViewWithTitleAndText(PathologyField.SUGAR,
                pathologyExpected.getAcucar()));
    }

    private CardView generateCardViewWithTitleAndText( PathologyField pathologyField, String text) {
        CardView card = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.popup_patologia_description_formulario, new LinearLayout(context), false);
        TextView titleView = card.findViewById(R.id.pop_up_patologia_description_formulario_title);
        titleView.setText(pathologyField.getUpperName());
        MultiAutoCompleteTextView editText = card.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        editText.setText(text);
        return card;
    }
    private void checkIfPathologyIsCorrect(Patologia pathologyExpected, Patologia pathology) {
        assertEquals(pathologyExpected.getPatologiaAtual(), pathology.getPatologiaAtual());
        assertEquals(pathologyExpected.getUrina(), pathology.getUrina());
        assertEquals(pathologyExpected.getFezes(), pathology.getFezes());
        assertEquals(pathologyExpected.getHoraSono(), pathology.getHoraSono());
        assertEquals(pathologyExpected.getMedicacao(), pathology.getMedicacao());
        assertEquals(pathologyExpected.getSuplemento(), pathology.getSuplemento());
        assertEquals(pathologyExpected.getEtilico(), pathology.getEtilico());
        assertEquals(pathologyExpected.getFumante(), pathology.getFumante());
        assertEquals(pathologyExpected.getAlergiaAlimentar(), pathology.getAlergiaAlimentar());
        assertEquals(pathologyExpected.getConsumoAgua(), pathology.getConsumoAgua());
        assertEquals(pathologyExpected.getAcucar(), pathology.getAcucar());
        assertEquals(pathologyExpected.getAtividadeFisica(), pathology.getAtividadeFisica());
    }



    @Test
    public void insertEntityInViewGroupAddsViewsForNonEmptyFields() {
        Patologia expected = TestEntityFactory.generatePatologia();
        inserter.insertEntityInViewGroup(expected);

        ViewGroup content = viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content);
        assertNotNull(content);

        int matched = 0;
        for (int i = 0; i < content.getChildCount(); i++) {
            ViewGroup card = (ViewGroup) content.getChildAt(i);
            TextView title = card.findViewById(R.id.pop_up_patologia_description_formulario_title);
            String titleText = title.getText().toString();
            try {
                PathologyField field = PathologyField.from(titleText);
                MultiAutoCompleteTextView et = card.findViewById(R.id.pop_up_patologia_description_formulario_editText);
                String expectedValue = new PathologyFieldMapper(field).getValue(expected);
                assertNotNull("expected value for field " + field + " should not be null", expectedValue);
                assertEquals(expectedValue, et.getText().toString());
                matched++;
            } catch (IllegalArgumentException ignored) {
                // ignore unknown titles
            }
        }

        int expectedCount = 0;
        for (PathologyField field : PathologyField.values()) {
            String v = new PathologyFieldMapper(field).getValue(expected);
            if (v != null && !v.isEmpty()) expectedCount++;
        }
        assertEquals(expectedCount, matched);
    }

    @Test
    public void checkViewGroupIsCorrectlyReturnsTrueAndFalse() {
        // viewGroup id was set in setUp to formulario_paciente_patologia_layout
        assertTrue(PathologyFormInserter.checkViewGroupIsCorrectly(viewGroup));
        assertTrue(PathologyFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_patologia_layout)));
        assertTrue(PathologyFormInserter.checkViewGroupIsCorrectly(viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content)));

        viewGroup.setId(R.id.formulario_paciente_dados_pessoais_layout);
        assertFalse(PathologyFormInserter.checkViewGroupIsCorrectly(viewGroup));
    }

    @Test
    public void insertViewGroupInEntityIgnoresUnknownTitles() {
        Context context = TestUtil.getThemedContext();
        CardView unknownCard = (CardView) LayoutInflater.from(context)
                .inflate(R.layout.popup_patologia_description_formulario, new LinearLayout(context), false);
        TextView titleUnknown = unknownCard.findViewById(R.id.pop_up_patologia_description_formulario_title);
        titleUnknown.setText("UNKNOWN_TITLE");
        MultiAutoCompleteTextView etUnknown = unknownCard.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        etUnknown.setText("Should be ignored");
        ViewGroup viewGroupWithCards = viewGroup.findViewById(R.id.formulario_paciente_patologia_layout_content);
        viewGroupWithCards.addView(unknownCard);

        // This should not throw and should leave pathology fields null
        inserter.insertViewGroupInEntity(pathology);

        // all relevant fields should remain null
        List<String> values = Arrays.asList(
                pathology.getPatologiaAtual(), pathology.getFezes(), pathology.getAcucar(), pathology.getUrina(),
                pathology.getConsumoAgua(), pathology.getFumante(), pathology.getAlergiaAlimentar(), pathology.getEtilico(),
                pathology.getHoraSono(), pathology.getAtividadeFisica(), pathology.getMedicacao(), pathology.getSuplemento()
        );
        for (String v : values) {
            assertNull(v);
        }
    }




}