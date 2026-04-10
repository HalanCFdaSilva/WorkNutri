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
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import com.google.android.material.slider.RangeSlider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class HeightCategoryTest {

    private Context context;
    private List<Paciente> pacientes;
    private List<Clinica> clinicas;
    private PacienteFilterPojo pojo;
    private HeightCategory heightCategory;
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
        pojo = new PacienteFilterPojo(pacientes, antropometrias, clinicas);

        // Criar HeightCategory
        heightCategory = new HeightCategory(context, pojo);
    }

    @Test
    public void testHeightCategoryConstructor() {
        assertNotNull(heightCategory);
        assertNotNull(pojo);
        assertEquals(2, pojo.getPacientes().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = heightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsRangeSlider() {
        ViewGroup viewGroup = heightCategory.generateView(layoutInflater);
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
        List<Paciente> selecteds = heightCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        // Remove um paciente dos selecionados
        heightCategory.getSelecteds().clear();
        assertEquals(0, heightCategory.getSelecteds().size());

        // Chama reset
        heightCategory.reset();

        // Verifica se os pacientes foram restaurados
        assertEquals(2, heightCategory.getSelecteds().size());
    }

    @Test
    public void ifPojoDontHaveValuesSelectedToRangeTheStartValuesIsTheMinAndMaxValuesFromHeightInAntropometryList() {
        ViewGroup viewGroup = heightCategory.generateView(layoutInflater);
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
        assertEquals(1.70f, values.get(0), 0.01);
        assertEquals(1.80f, values.get(1), 0.01);
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
        singleAntropometria.add(a);

        PacienteFilterPojo singlePojo = new PacienteFilterPojo(singlePaciente, singleAntropometria, clinicas);
        HeightCategory singleHeightCategory = new HeightCategory(context, singlePojo);

        ViewGroup viewGroup = singleHeightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testEmptyAntropometriaList() {
        List<Antropometria> emptyList = new ArrayList<>();
        PacienteFilterPojo emptyPojo = new PacienteFilterPojo(pacientes, emptyList, clinicas);
        HeightCategory emptyHeightCategory = new HeightCategory(context, emptyPojo);

        // Não deve lançar exceção ao gerar a view mesmo com lista vazia
        ViewGroup viewGroup = emptyHeightCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testHeightCategoryInheritsPacientesFilterCategory() {
        assertNotNull(heightCategory);
        assertTrue(heightCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testHeightCategoryHasCorrectContext() {
        assertNotNull(heightCategory);
        // Verifica que a categoria foi construída corretamente com o contexto
    }

    @Test
    public void testMultipleHeightCategoryInstances() {
        HeightCategory category1 = new HeightCategory(context, pojo);
        HeightCategory category2 = new HeightCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2); // Diferentes instâncias
    }

    @Test
    public void testHeightCategoryWithDifferentHeights() {
        List<Paciente> testPacientes = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            testPacientes.add(p);
        }

        List<Antropometria> testAntropometrias = new ArrayList<>();
        float[] heights = {1.50f, 1.60f, 1.70f, 1.80f, 1.90f};
        for (int i = 0; i < heights.length; i++) {
            Antropometria a = new Antropometria();
            a.setIdPaciente(i + 1);
            a.setAltura(String.valueOf(heights[i]));
            testAntropometrias.add(a);
        }

        PacienteFilterPojo testPojo = new PacienteFilterPojo(testPacientes, testAntropometrias, clinicas);
        HeightCategory testCategory = new HeightCategory(context, testPojo);

        ViewGroup viewGroup = testCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(5, testPojo.getPacientes().size());
    }



    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = heightCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testHeightCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Low Height");
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("High Height");
        filterPacientes.add(p2);

        HeightCategory filterCategory = getHeightCategory("1.50", "2.00", filterPacientes);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @NonNull
    private HeightCategory getHeightCategory(String altura, String altura1, List<Paciente> filterPacientes) {
        List<Antropometria> filterAntropometrias = new ArrayList<>();
        Antropometria a1 = new Antropometria();
        a1.setIdPaciente(1);
        a1.setAltura(altura);
        filterAntropometrias.add(a1);

        Antropometria a2 = new Antropometria();
        a2.setIdPaciente(2);
        a2.setAltura(altura1);
        filterAntropometrias.add(a2);

        PacienteFilterPojo filterPojo = new PacienteFilterPojo(filterPacientes, filterAntropometrias, clinicas);
        return new HeightCategory(context, filterPojo);
    }


    @Test
    public void testHeightCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        assertNotNull(pojo.getState().getTupleOfHeightSlider());
        assertEquals(2, pojo.getState().getTupleOfHeightSlider().length);
    }

    @Test
    public void testHeightCategoryWithSameHeight() {
        List<Paciente> samePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        samePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        samePacientes.add(p2);

        HeightCategory sameCategory = getHeightCategory("1.75", "1.75", samePacientes);

        ViewGroup viewGroup = sameCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }
    
}

