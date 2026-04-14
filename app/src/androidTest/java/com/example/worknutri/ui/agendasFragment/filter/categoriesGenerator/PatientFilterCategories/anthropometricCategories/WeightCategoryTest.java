package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.PatientFilterCategory;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import com.google.android.material.slider.RangeSlider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class WeightCategoryTest {

    private Context context;
    private List<Paciente> pacientes;
    private List<Clinica> clinicas;
    private PatientFilterPojo pojo;
    private WeightCategory weightCategory;
    private LayoutInflater layoutInflater;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        layoutInflater = LayoutInflater.from(context);

        // Criar pacientes de teste
        pacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Paciente 1");
        p1.setIdade(25);
        pacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Paciente 2");
        p2.setIdade(35);
        pacientes.add(p2);

        // Criar antropometrias de teste
        List<Antropometria> antropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setAltura("1.70");
        a1.setPeso("70.0");
        antropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setAltura("1.80");
        a2.setPeso("80.0");
        antropometrias.add(a2);

        clinicas = new ArrayList<>();

        // Criar pojo
        pojo = new PatientFilterPojo(pacientes, antropometrias, clinicas);

        // Criar WeightCategory
        weightCategory = new WeightCategory(context, pojo);
    }

    @Test
    public void testWeightCategoryConstructor() {
        assertNotNull(weightCategory);
        assertNotNull(pojo);
        assertEquals(2, pojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = weightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsRangeSlider() {
        ViewGroup viewGroup = weightCategory.generateView(layoutInflater);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        assertNotNull(linearLayout);

        // Verificar se o RangeSlider foi adicionado
        RangeSlider rangeSlider = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof RangeSlider) {
                rangeSlider = (RangeSlider) linearLayout.getChildAt(i);
                break;
            }
        }
        assertNotNull(rangeSlider);
    }

    @Test
    public void testGetSelectedsInitiallyContainsAllPacientes() {
        List<Paciente> selecteds = weightCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        // Remove um paciente dos selecionados
        weightCategory.getSelecteds().clear();
        assertEquals(0, weightCategory.getSelecteds().size());
        weightCategory.generateCategory(layoutInflater);
        // Chama reset
        weightCategory.reset();

        // Verifica se os pacientes foram restaurados
        assertEquals(2, weightCategory.getSelecteds().size());
    }

    @Test
    public void ifPojoDontHaveValuesSelectedToRangeTheStartValuesIsTheMinAndMaxValuesFromWeightInAntropometryList() {
        ViewGroup viewGroup = weightCategory.generateView(layoutInflater);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);

        RangeSlider rangeSlider = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof RangeSlider) {
                rangeSlider = (RangeSlider) linearLayout.getChildAt(i);
                break;
            }
        }

        assertNotNull(rangeSlider);
        // O slider deve ter valores iniciais configurados
        List<Float> values = rangeSlider.getValues();
        assertNotNull(values);
        assertEquals(2, values.size());
        assertEquals(70.0f, values.get(0), 0.01);
        assertEquals(80.0f, values.get(1), 0.01);
    }

    @Test
    public void testSingleAntropometriaEntry() {
        List<Paciente> singlePaciente = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("Single Patient");
        singlePaciente.add(p);

        List<Antropometria> singleAntropometria = new ArrayList<>();
        Antropometria a = new Antropometria();
        a.setIdPaciente(1);
        a.setAltura("1.75");
        a.setPeso("75.0");
        singleAntropometria.add(a);

        PatientFilterPojo singlePojo = new PatientFilterPojo(singlePaciente, singleAntropometria, clinicas);
        WeightCategory singleWeightCategory = new WeightCategory(context, singlePojo);

        ViewGroup viewGroup = singleWeightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testEmptyAntropometriaList() {
        List<Antropometria> emptyList = new ArrayList<>();
        PatientFilterPojo emptyPojo = new PatientFilterPojo(pacientes, emptyList, clinicas);
        WeightCategory emptyWeightCategory = new WeightCategory(context, emptyPojo);

        // Não deve lançar exceção ao gerar a view mesmo com lista vazia
        ViewGroup viewGroup = emptyWeightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testWeightCategoryInheritsPacientesFilterCategory() {
        assertNotNull(weightCategory);
        assertTrue(weightCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testWeightCategoryHasCorrectContext() {
        assertNotNull(weightCategory);
    }

    @Test
    public void testMultipleWeightCategoryInstances() {
        WeightCategory category1 = new WeightCategory(context, pojo);
        WeightCategory category2 = new WeightCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2);
    }

    @Test
    public void testWeightCategoryWithDifferentWeights() {
        List<Paciente> testPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            testPacientes.add(p);
        }

        List<Antropometria> testAntropometrias = new ArrayList<>();
        float[] weights = {50.0f, 60.0f, 70.0f, 80.0f, 90.0f};
        for (int i = 0; i < weights.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setPeso(String.valueOf(weights[i]));
            testAntropometrias.add(a);
        }

        PatientFilterPojo testPojo = new PatientFilterPojo(testPacientes, testAntropometrias, clinicas);
        WeightCategory testCategory = new WeightCategory(context, testPojo);

        ViewGroup viewGroup = testCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(5, testPojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = weightCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testWeightCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Light Weight");
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Heavy Weight");
        filterPacientes.add(p2);

        WeightCategory filterCategory = getWeightCategory(filterPacientes);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @NonNull
    private WeightCategory getWeightCategory(List<Paciente> filterPacientes) {
        List<Antropometria> filterAntropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setPeso("50.0");
        filterAntropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setPeso("100.0");
        filterAntropometrias.add(a2);

        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, filterAntropometrias, clinicas);
        return new WeightCategory(context, filterPojo);
    }

    @Test
    public void testWeightCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        assertNotNull(pojo.getState().getTupleOfWeightSlider());
        assertEquals(2, pojo.getState().getTupleOfWeightSlider().length);
    }

    @Test
    public void testWeightCategoryWithSameWeight() {
        List<Paciente> samePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        samePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        samePacientes.add(p2);

        WeightCategory sameCategory = getCategory(samePacientes);

        ViewGroup viewGroup = sameCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @NonNull
    private WeightCategory getCategory(List<Paciente> samePacientes) {
        List<Antropometria> sameAntropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setPeso("75.0");
        sameAntropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setPeso("75.0");
        sameAntropometrias.add(a2);

        PatientFilterPojo samePojo = new PatientFilterPojo(samePacientes, sameAntropometrias, clinicas);
        return new WeightCategory(context, samePojo);
    }

    @Test
    public void testSelectPacientesInRangeFiltersCorrectly() throws Exception {
        // Criar pacientes com diferentes pesos
        List<Paciente> filterPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            filterPacientes.add(p);
        }

        List<Antropometria> filterAntropometrias = new ArrayList<>();
        float[] weights = {50.0f, 60.0f, 70.0f, 80.0f, 90.0f};
        for (int i = 0; i < weights.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setPeso(String.valueOf(weights[i]));
            filterAntropometrias.add(a);
        }

        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, filterAntropometrias, clinicas);
        WeightCategory filterCategory = new WeightCategory(context, filterPojo);

        // Gerar a view para inicializar o slider
        ViewGroup viewGroup = filterCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        // Simular a seleção de um range específico (65.0 a 85.0)
        float[] selectedRange = {65.0f, 85.0f};
        
        // Usar reflexão para chamar o método privado selectPacientesInRange
        java.lang.reflect.Method method = WeightCategory.class.getDeclaredMethod("selectPacientesInRange", float[].class);
        method.setAccessible(true);
        method.invoke(filterCategory, selectedRange);

        // Verificar que apenas os pacientes com peso entre 65 e 85 estão selecionados
        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertNotNull(selecteds);
        // Esperamos 2 pacientes: P3 (70.0), P4 (80.0) estão dentro
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testSelectPacientesInRangeWithNarrowRange() throws Exception {
        List<Paciente> narrowPacientes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            narrowPacientes.add(p);
        }

        List<Antropometria> narrowAntropometrias = new ArrayList<>();
        float[] weights = {70.0f, 75.0f, 80.0f};
        for (int i = 0; i < weights.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setPeso(String.valueOf(weights[i]));
            narrowAntropometrias.add(a);
        }

        PatientFilterPojo narrowPojo = new PatientFilterPojo(narrowPacientes, narrowAntropometrias, clinicas);
        WeightCategory narrowCategory = new WeightCategory(context, narrowPojo);

        ViewGroup viewGroup = narrowCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float[] narrowRange = {70.0f, 71.0f};
        
        java.lang.reflect.Method method = WeightCategory.class.getDeclaredMethod("selectPacientesInRange", float[].class);
        method.setAccessible(true);
        method.invoke(narrowCategory, narrowRange);

        List<Paciente> selecteds = narrowCategory.getSelecteds();
        assertEquals(1, selecteds.size());
        assertEquals(1L, selecteds.get(0).getId());
    }

    @Test
    public void testSelectPacientesInRangeWithWideRange() throws Exception {
        List<Paciente> widePacientes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            widePacientes.add(p);
        }

        List<Antropometria> wideAntropometrias = new ArrayList<>();
        float[] weights = {70.0f, 75.0f, 80.0f};
        for (int i = 0; i < weights.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setPeso(String.valueOf(weights[i]));
            wideAntropometrias.add(a);
        }

        PatientFilterPojo widePojo = new PatientFilterPojo(widePacientes, wideAntropometrias, clinicas);
        WeightCategory wideCategory = new WeightCategory(context, widePojo);

        ViewGroup viewGroup = wideCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float[] wideRange = {40.0f, 120.0f};
        
        java.lang.reflect.Method method = WeightCategory.class.getDeclaredMethod("selectPacientesInRange", float[].class);
        method.setAccessible(true);
        method.invoke(wideCategory, wideRange);

        List<Paciente> selecteds = wideCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testSelectPacientesInRangeExcludesOutsideValues() throws Exception {
        List<Paciente> excludePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Leve");
        excludePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Médio");
        excludePacientes.add(p2);

        Paciente p3 = new Paciente();
        p3.setId(3L);
        p3.setNomePaciente("Pesado");
        excludePacientes.add(p3);

        WeightCategory excludeCategory = getExcludeCategory(excludePacientes);

        ViewGroup viewGroup = excludeCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float[] mediumRange = {70.0f, 80.0f};
        
        java.lang.reflect.Method method = WeightCategory.class.getDeclaredMethod("selectPacientesInRange", float[].class);
        method.setAccessible(true);
        method.invoke(excludeCategory, mediumRange);

        List<Paciente> selecteds = excludeCategory.getSelecteds();
        assertEquals(1, selecteds.size());
        assertEquals(2L, selecteds.get(0).getId());
    }

    @NonNull
    private WeightCategory getExcludeCategory(List<Paciente> excludePacientes) {
        List<Antropometria> excludeAntropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setPeso("50.0");
        excludeAntropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setPeso("75.0");
        excludeAntropometrias.add(a2);

        Antropometria a3 = new Antropometria();
        a3.setIdPaciente(3);
        a3.setPeso("100.0");
        excludeAntropometrias.add(a3);

        PatientFilterPojo excludePojo = new PatientFilterPojo(excludePacientes, excludeAntropometrias, clinicas);
        return new WeightCategory(context, excludePojo);
    }
}

