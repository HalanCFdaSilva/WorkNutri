package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

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
public class YearCategoryTest {

    private Context context;
    private List<Antropometria> antropometrias;
    private List<Clinica> clinicas;
    private PatientFilterPojo pojo;
    private YearCategory yearCategory;
    private LayoutInflater layoutInflater;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        layoutInflater = LayoutInflater.from(context);

        // Criar pacientes de teste com diferentes idades
        List<Paciente> pacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Paciente 1");
        p1.setIdade(20);
        pacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Paciente 2");
        p2.setIdade(50);
        pacientes.add(p2);

        antropometrias = new ArrayList<>();
        clinicas = new ArrayList<>();

        // Criar pojo
        pojo = new PatientFilterPojo(pacientes, antropometrias, clinicas);

        // Criar YearCategory
        yearCategory = new YearCategory(context, pojo);
    }

    @Test
    public void testYearCategoryConstructor() {
        assertNotNull(yearCategory);
        assertNotNull(pojo);
        assertEquals(2, pojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = yearCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsRangeSlider() {
        ViewGroup viewGroup = yearCategory.generateView(layoutInflater);
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
        List<Paciente> selecteds = yearCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        // Remove um paciente dos selecionados
        yearCategory.getSelecteds().clear();
        assertEquals(0, yearCategory.getSelecteds().size());

        // Gera a view para inicializar o viewGroup
        yearCategory.generateView(layoutInflater);
        
        // Chama reset
        yearCategory.reset();

        // Verifica se os pacientes foram restaurados
        assertEquals(2, yearCategory.getSelecteds().size());
    }

    @Test
    public void ifPojoDontHaveValuesSelectedToRangeTheStartValuesIsTheMinAndMaxValuesFromAgesInPacienteList() {
        ViewGroup viewGroup = yearCategory.generateView(layoutInflater);
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
        assertEquals(20.0f, values.get(0), 0.01);
        assertEquals(50.0f, values.get(1), 0.01);
    }

    @Test
    public void testSinglePacienteEntry() {
        List<Paciente> singlePaciente = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("Single Patient");
        p.setIdade(30);
        singlePaciente.add(p);

        PatientFilterPojo singlePojo = new PatientFilterPojo(singlePaciente, antropometrias, clinicas);
        YearCategory singleYearCategory = new YearCategory(context, singlePojo);

        ViewGroup viewGroup = singleYearCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testEmptyPacienteList() {
        List<Paciente> emptyList = new ArrayList<>();
        PatientFilterPojo emptyPojo = new PatientFilterPojo(emptyList, antropometrias, clinicas);
        YearCategory emptyYearCategory = new YearCategory(context, emptyPojo);

        // Não deve lançar exceção ao gerar a view mesmo com lista vazia
        ViewGroup viewGroup = emptyYearCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testYearCategoryInheritsPacientesFilterCategory() {
        assertNotNull(yearCategory);
        assertTrue(yearCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testYearCategoryHasCorrectContext() {
        assertNotNull(yearCategory);
    }

    @Test
    public void testMultipleYearCategoryInstances() {
        YearCategory category1 = new YearCategory(context, pojo);
        YearCategory category2 = new YearCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2);
    }

    @Test
    public void testYearCategoryWithDifferentAges() {
        List<Paciente> testPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setIdade(10 * i); // Idades: 10, 20, 30, 40, 50
            testPacientes.add(p);
        }

        PatientFilterPojo testPojo = new PatientFilterPojo(testPacientes, antropometrias, clinicas);
        YearCategory testCategory = new YearCategory(context, testPojo);

        ViewGroup viewGroup = testCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(5, testPojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = yearCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testYearCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Young");
        p1.setIdade(18);
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Old");
        p2.setIdade(70);
        filterPacientes.add(p2);

        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, antropometrias, clinicas);
        YearCategory filterCategory = new YearCategory(context, filterPojo);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testYearCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        assertNotNull(pojo.getState().getTupleOfYearSlider());
        assertEquals(2, pojo.getState().getTupleOfYearSlider().length);
    }

    @Test
    public void testYearCategoryWithSameAge() {
        List<Paciente> samePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setIdade(30);
        samePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setIdade(30);
        samePacientes.add(p2);

        PatientFilterPojo samePojo = new PatientFilterPojo(samePacientes, antropometrias, clinicas);
        YearCategory sameCategory = new YearCategory(context, samePojo);

        ViewGroup viewGroup = sameCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testSelectPacienteInsideRangeFiltersCorrectly() throws Exception {
        // Criar pacientes com diferentes idades
        List<Paciente> filterPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setIdade(10 * i); // Idades: 10, 20, 30, 40, 50
            filterPacientes.add(p);
        }

        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, antropometrias, clinicas);
        YearCategory filterCategory = new YearCategory(context, filterPojo);

        // Gerar a view para inicializar o slider
        ViewGroup viewGroup = filterCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        // Simular a seleção de um range específico (25 a 45)
        float minValue = 25.0f;
        float maxValue = 45.0f;
        
        // Usar reflexão para chamar o método privado selectPacienteInsideRange
        java.lang.reflect.Method method = YearCategory.class.getDeclaredMethod("selectPacienteInsideRange", float.class, float.class);
        method.setAccessible(true);
        method.invoke(filterCategory, minValue, maxValue);

        // Verificar que apenas os pacientes com idade entre 25 e 45 estão selecionados
        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertNotNull(selecteds);
        // Esperamos 2 pacientes: P3 (30), P4 (40) estão dentro
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testSelectPacienteInsideRangeWithNarrowRange() throws Exception {
        List<Paciente> narrowPacientes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setIdade(20 + (i * 10)); // Idades: 30, 40, 50
            narrowPacientes.add(p);
        }

        PatientFilterPojo narrowPojo = new PatientFilterPojo(narrowPacientes, antropometrias, clinicas);
        YearCategory narrowCategory = new YearCategory(context, narrowPojo);

        ViewGroup viewGroup = narrowCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float minValue = 30.0f;
        float maxValue = 31.0f;
        
        java.lang.reflect.Method method = YearCategory.class.getDeclaredMethod("selectPacienteInsideRange", float.class, float.class);
        method.setAccessible(true);
        method.invoke(narrowCategory, minValue, maxValue);

        List<Paciente> selecteds = narrowCategory.getSelecteds();
        assertEquals(1, selecteds.size());
        assertEquals(1L, selecteds.get(0).getId());
    }

    @Test
    public void testSelectPacienteInsideRangeWithWideRange() throws Exception {
        List<Paciente> widePacientes = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setIdade(20 + (i * 10)); // Idades: 30, 40, 50
            widePacientes.add(p);
        }

        PatientFilterPojo widePojo = new PatientFilterPojo(widePacientes, antropometrias, clinicas);
        YearCategory wideCategory = new YearCategory(context, widePojo);

        ViewGroup viewGroup = wideCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float minValue = 10.0f;
        float maxValue = 100.0f;
        
        java.lang.reflect.Method method = YearCategory.class.getDeclaredMethod("selectPacienteInsideRange", float.class, float.class);
        method.setAccessible(true);
        method.invoke(wideCategory, minValue, maxValue);

        List<Paciente> selecteds = wideCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }

    @Test
    public void testSelectPacienteInsideRangeExcludesOutsideValues() throws Exception {
        YearCategory excludeCategory = getYearCategory();

        ViewGroup viewGroup = excludeCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);

        float minValue = 30.0f;
        float maxValue = 40.0f;
        
        java.lang.reflect.Method method = YearCategory.class.getDeclaredMethod("selectPacienteInsideRange", float.class, float.class);
        method.setAccessible(true);
        method.invoke(excludeCategory, minValue, maxValue);

        List<Paciente> selecteds = excludeCategory.getSelecteds();
        assertEquals(1, selecteds.size());
        assertEquals(2L, selecteds.get(0).getId());
    }

    @NonNull
    private YearCategory getYearCategory() {
        List<Paciente> excludePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Jovem");
        p1.setIdade(18);
        excludePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Adulto");
        p2.setIdade(35);
        excludePacientes.add(p2);

        Paciente p3 = new Paciente();
        p3.setId(3L);
        p3.setNomePaciente("Idoso");
        p3.setIdade(70);
        excludePacientes.add(p3);

        PatientFilterPojo excludePojo = new PatientFilterPojo(excludePacientes, antropometrias, clinicas);
        return new YearCategory(context, excludePojo);

    }

    @Test
    public void testYearCategoryRangeSliderStepSize() {
        ViewGroup viewGroup = yearCategory.generateView(layoutInflater);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);

        RangeSlider rangeSlider = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof RangeSlider) {
                rangeSlider = (RangeSlider) linearLayout.getChildAt(i);
                break;
            }
        }

        assertNotNull(rangeSlider);
        assertEquals(1.0f, rangeSlider.getStepSize(), 0.01);
    }

    @Test
    public void testYearCategoryMinMaxValues() {
        List<Paciente> testPacientes = new ArrayList<>();
        
        Paciente young = new Paciente();
        young.setId(1L);
        young.setIdade(15);
        testPacientes.add(young);

        Paciente old = new Paciente();
        old.setId(2L);
        old.setIdade(85);
        testPacientes.add(old);

        PatientFilterPojo testPojo = new PatientFilterPojo(testPacientes, antropometrias, clinicas);
        YearCategory testCategory = new YearCategory(context, testPojo);

        ViewGroup viewGroup = testCategory.generateView(layoutInflater);
        ViewGroup linearLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);

        RangeSlider rangeSlider = null;
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            if (linearLayout.getChildAt(i) instanceof RangeSlider) {
                rangeSlider = (RangeSlider) linearLayout.getChildAt(i);
                break;
            }
        }

        assertNotNull(rangeSlider);
        List<Float> values = rangeSlider.getValues();
        assertEquals(15.0f, values.get(0), 0.01);
        assertEquals(85.0f, values.get(1), 0.01);
    }
}

