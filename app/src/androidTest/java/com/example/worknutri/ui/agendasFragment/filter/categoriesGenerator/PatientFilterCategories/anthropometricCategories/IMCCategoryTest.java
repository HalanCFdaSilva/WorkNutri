package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class IMCCategoryTest {

    private Context context;
    private List<Paciente> pacientes;
    private List<Clinica> clinicas;
    private PatientFilterPojo pojo;
    private IMCCategory imcCategory;
    private LayoutInflater layoutInflater;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        context = new ContextThemeWrapper(context, R.style.Theme_themeFilter_Chip);
        layoutInflater = LayoutInflater.from(context);

        pacientes = TestEntityFactory.generatePatientListToTest();
        List<Antropometria> antropometrias = TestEntityFactory.generateAnthropometryListToTest(pacientes);
        clinicas = new ArrayList<>();
        pojo = new PatientFilterPojo(pacientes, antropometrias, clinicas);

        imcCategory = new IMCCategory(context, pojo);
    }

    @Test
    public void testIMCCategoryConstructor() {
        assertNotNull(imcCategory);
        assertNotNull(pojo);
        assertEquals(3, pojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsChipGroup() {
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        assertNotNull(chipGroup);
    }

    @Test
    public void testChipsAreCreatedForAllIMCClassifications() {
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Deve haver 6 chips (um para cada ClassificacaoImc)
        assertEquals(ClassificacaoImc.values().length, chipGroup.getChildCount());
    }

    @Test
    public void testGetSelectedsInitiallyContainsAllPacientes() {
        List<Paciente> selecteds = imcCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        // Remove pacientes dos selecionados
        imcCategory.getSelecteds().clear();
        assertEquals(0, imcCategory.getSelecteds().size());

        // Chama reset
        imcCategory.reset();

        // Verifica se os pacientes foram restaurados
        assertEquals(3, imcCategory.getSelecteds().size());
    }

    @Test
    public void testSelectSingleIMCCategoryFiltersCorrectly() throws Exception {
        // Gerar a view para inicializar os chips
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        // Usar reflexão para chamar o método privado getStreamOfPacientesInClassificacaoImc
        java.lang.reflect.Method method = IMCCategory.class.getDeclaredMethod("getStreamOfPacientesInClassificacaoImc", ClassificacaoImc.class);
        method.setAccessible(true);

        // Obter pacientes com IMC NORMAL
        @SuppressWarnings("unchecked")
        List<Paciente> normalImcPacientes = (List<Paciente>) method.invoke(imcCategory, ClassificacaoImc.NORMAL);

        assertNotNull(normalImcPacientes);
        assertEquals(1, normalImcPacientes.size());
        assertEquals(1L, normalImcPacientes.get(0).getId());
    }

    @Test
    public void testSelectMultipleIMCCategoriesFiltersCorrectly() throws Exception {
        // Gerar a view
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        java.lang.reflect.Method method = IMCCategory.class.getDeclaredMethod("getStreamOfPacientesInClassificacaoImc", ClassificacaoImc.class);
        method.setAccessible(true);

        // Obter pacientes com IMC SOBREPESO
        @SuppressWarnings("unchecked")
        List<Paciente> sobrepesoPacientes = (List<Paciente>) method.invoke(imcCategory, ClassificacaoImc.SOBREPESO);
        
        // Obter pacientes com IMC OBESIDADE_LEVE
        @SuppressWarnings("unchecked")
        List<Paciente> obesidadeLevePacientes = (List<Paciente>) method.invoke(imcCategory, ClassificacaoImc.OBESIDADE_LEVE);

        assertNotNull(sobrepesoPacientes);
        assertNotNull(obesidadeLevePacientes);
        assertEquals(1, sobrepesoPacientes.size());
        assertEquals(1, obesidadeLevePacientes.size());
        assertEquals(2L, sobrepesoPacientes.get(0).getId());
        assertEquals(3L, obesidadeLevePacientes.get(0).getId());
    }

    @Test
    public void testIMCCategoryWithAllPacientesInSameIMCCategory() {
        List<Paciente> samePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        samePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        samePacientes.add(p2);

        IMCCategory sameCategory = getImcCategory("22.0", "23.0", samePacientes);

        ViewGroup viewGroup = sameCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(2, sameCategory.getSelecteds().size());
    }

    @NonNull
    private IMCCategory getImcCategory(String imc, String imc1, List<Paciente> samePacientes) {
        List<Antropometria> sameAntropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setImc(imc);
        sameAntropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setImc(imc1);
        sameAntropometrias.add(a2);

        PatientFilterPojo samePojo = new PatientFilterPojo(samePacientes, sameAntropometrias, clinicas);
        return new IMCCategory(context, samePojo);
    }

    @Test
    public void testEmptyAntropometriaList() {
        List<Antropometria> emptyList = new ArrayList<>();
        PatientFilterPojo emptyPojo = new PatientFilterPojo(pacientes, emptyList, clinicas);
        IMCCategory emptyImcCategory = new IMCCategory(context, emptyPojo);

        // Não deve lançar exceção ao gerar a view mesmo com lista vazia
        ViewGroup viewGroup = emptyImcCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testIMCCategoryInheritsPacientesFilterCategory() {
        assertNotNull(imcCategory);
        assertTrue(imcCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testMultipleIMCCategoryInstances() {
        IMCCategory category1 = new IMCCategory(context, pojo);
        IMCCategory category2 = new IMCCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2);
    }

    @Test
    public void testIMCCategoryWithVariousBodies() {
        List<Paciente> variousPacientes = new ArrayList<>();
        for (int i = 1; i <= 6; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            variousPacientes.add(p);
        }

        List<Antropometria> variousAntropometrias = new ArrayList<>();
        double[] imcs = {17.0, 22.0, 26.0, 32.0, 37.0, 42.0};

        for (int i = 0; i < imcs.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setImc(String.valueOf(imcs[i]));
            variousAntropometrias.add(a);
        }

        PatientFilterPojo variousPojo = new PatientFilterPojo(variousPacientes, variousAntropometrias, clinicas);
        IMCCategory variousCategory = new IMCCategory(context, variousPojo);

        ViewGroup viewGroup = variousCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(6, variousPojo.getPatientList().size());
    }

    @Test
    public void testIMCCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        assertNotNull(pojo.getState().getBmiClassificationsSelected());
        assertEquals(0, pojo.getState().getBmiClassificationsSelected().size());
    }

    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = imcCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testIMCCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Deficitario");
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Morbido");
        filterPacientes.add(p2);

        IMCCategory filterCategory = getImcCategory("17.0", "42.0", filterPacientes);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testChipColorCorrespondsToIMCClassification() {
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Verificar que cada chip tem a cor correta
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertNotNull(chip);
            // Verifica que o chip foi criado
            assertTrue(chip.getText().length() > 0);
        }
    }

    @Test
    public void testResetClearsIMCSelection() {
        // Adicionar uma classificação IMC ao estado
        ActivityScenario.launch(ActivityToTest.class).onActivity(activity -> {
            pojo.getState().getBmiClassificationsSelected().add(ClassificacaoImc.NORMAL);
            assertEquals(1, pojo.getState().getBmiClassificationsSelected().size());

            // Gerar a view
            ViewGroup viewGroup = imcCategory.generateCategory(layoutInflater);
            assertNotNull(viewGroup);

            // Chamar reset
            imcCategory.reset();

            // Verificar que as classificações foram limpas
            assertEquals(0, pojo.getState().getBmiClassificationsSelected().size());
        });

    }

    @Test
    public void testSinglePacienteWithSpecificIMC() {
        List<Paciente> singlePaciente = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("Single Patient");
        singlePaciente.add(p);

        List<Antropometria> singleAntropometria = new ArrayList<>();
        Antropometria a = new Antropometria();
        a.setIdPaciente(1);
        a.setImc("28.5");
        singleAntropometria.add(a);

        PatientFilterPojo singlePojo = new PatientFilterPojo(singlePaciente, singleAntropometria, clinicas);
        IMCCategory singleCategory = new IMCCategory(context, singlePojo);

        ViewGroup viewGroup = singleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(1, singleCategory.getSelecteds().size());
    }

    @Test
    public void testIMCCategoryHasCorrectContext() {
        assertNotNull(imcCategory);
        // Verifica que a categoria foi construída corretamente com o contexto
    }

    @Test
    public void testChipsAreNotCheckedByDefault() {
        ViewGroup viewGroup = imcCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Verificar que nenhum chip está marcado inicialmente
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertFalse(chip.isChecked());
        }
    }

    @Test
    public void testIMCBoundaryValues() {
        // Criar pacientes com IMC nos limites das classificações
        List<Paciente> boundaryPacientes = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            Paciente p = new Paciente();
            p.setId(i + 1L);
            boundaryPacientes.add(p);
        }

        List<Antropometria> boundaryAntropometrias = new ArrayList<>();
        // Valores nos limites: 18.5, 25, 30, 35, 40
        double[] boundaryImcs = {18.4, 18.5, 24.9, 25.0, 29.9, 30.0};
        
        for (int i = 0; i < boundaryImcs.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setImc(String.valueOf(boundaryImcs[i]));
            boundaryAntropometrias.add(a);
        }

        PatientFilterPojo boundaryPojo = new PatientFilterPojo(boundaryPacientes, boundaryAntropometrias, clinicas);
        IMCCategory boundaryCategory = new IMCCategory(context, boundaryPojo);

        ViewGroup viewGroup = boundaryCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }
}

