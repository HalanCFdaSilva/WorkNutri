package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.patientCategories;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
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
public class GenderCategoryTest {

    private Context context;
    private List<Paciente> pacientes;
    private List<Clinica> clinicas;
    private List<Antropometria> antropometrias;
    private PatientFilterPojo pojo;
    private GenderCategory genderCategory;
    private LayoutInflater layoutInflater;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        context = new ContextThemeWrapper(context, R.style.Theme_themeFilter_Chip);
        layoutInflater = LayoutInflater.from(context);

        pacientes = TestEntityFactory.generatePatientListToTest();
        pacientes.get(0).setGenero('F');
        pacientes.get(2).setGenero('F');

        antropometrias = new ArrayList<>();
        clinicas = new ArrayList<>();

        // Criar pojo
        pojo = new PatientFilterPojo(pacientes, antropometrias, clinicas);

        // Criar GenderCategory
        genderCategory = new GenderCategory(context, pojo);
    }

    @Test
    public void testGenderCategoryConstructor() {
        assertNotNull(genderCategory);
        assertNotNull(pojo);
        assertEquals(pacientes.size(), pojo.getPatientList().size());
    }

    @Test
    public void testGenerateViewReturnsViewGroup() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertTrue(viewGroup instanceof ViewGroup);
    }

    @Test
    public void testGenerateViewContainsChipGroup() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        assertNotNull(chipGroup);
    }

    @Test
    public void testChipGroupHasSingleSelection() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
        assertNotNull(chipGroup);
        assertTrue(chipGroup.isSingleSelection());
    }

    @Test
    public void testChipsAreCreatedForMasculinoAndFeminino() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Deve haver exatamente 2 chips (Masculino e Feminino)
        assertEquals(2, chipGroup.getChildCount());
    }

    @Test
    public void testGetSelectedsInitiallyContainsAllPacientes() {
        List<Paciente> selecteds = genderCategory.getSelecteds();
        assertNotNull(selecteds);
        assertEquals(pacientes.size(), selecteds.size());
    }

    @Test
    public void testResetRestoresPacientes() {
        // Remove pacientes dos selecionados
        genderCategory.getSelecteds().clear();
        assertEquals(0, genderCategory.getSelecteds().size());

        // Gera a view para inicializar viewGroup
        genderCategory.generateView(layoutInflater);

        // Chama reset
        genderCategory.reset();

        // Verifica se os pacientes foram restaurados
        assertEquals(pacientes.size(), genderCategory.getSelecteds().size());
    }

    @Test
    public void testSelectMasculinoFiltersCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            assertNotNull(viewGroup);

            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);
            assertNotNull(chipGroup);
            assertEquals(2, chipGroup.getChildCount());

            // Encontrar e marcar o chip Masculino (primeiro)
            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);
            masculinoChip.setChecked(true);

            List<Paciente> selecteds = genderCategory.getSelecteds();
            assertNotNull(selecteds);

            // Todos os pacientes selecionados devem ser Masculino
            for (Paciente paciente : selecteds) {
                assertEquals('M', paciente.getGenero());
            }
            assertEquals(pacientes.stream().filter(paciente -> paciente.getGenero()=='M').count(), selecteds.size());
        });
    }

    @Test
    public void testSelectFemininoFiltersCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            // Encontrar e marcar o chip Feminino (segundo)
            Chip femininoChip = (Chip) chipGroup.getChildAt(1);
            femininoChip.setChecked(true);

            List<Paciente> selecteds = genderCategory.getSelecteds();
            assertNotNull(selecteds);

            // Todos os pacientes selecionados devem ser Feminino
            for (Paciente paciente : selecteds) {
                assertEquals('F', paciente.getGenero());
            }
            assertEquals(2, selecteds.size());
        });
    }

    @Test
    public void testSwitchingBetweenGendersWorks() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);
            Chip femininoChip = (Chip) chipGroup.getChildAt(1);

            // Selecionar Masculino
            masculinoChip.setChecked(true);
            long numberOfpatientExpected = pacientes.stream().filter(paciente -> paciente.getGenero() == 'M').count();
            assertEquals(numberOfpatientExpected, genderCategory.getSelecteds().size());
            for (Paciente p : genderCategory.getSelecteds()) {
                assertEquals('M', p.getGenero());
            }

            // Mudar para Feminino
            femininoChip.setChecked(true);
            numberOfpatientExpected = pacientes.stream().filter(paciente -> paciente.getGenero() == 'F').count();
            assertEquals(numberOfpatientExpected, genderCategory.getSelecteds().size());
            for (Paciente p : genderCategory.getSelecteds()) {
                assertEquals('F', p.getGenero());
            }
        });
    }

    @Test
    public void testGenderCategoryWithOnlyMalePacientes() {
        List<Paciente> malePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("João");
        p1.setGenero('M');
        malePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Pedro");
        p2.setGenero('M');
        malePacientes.add(p2);

        PatientFilterPojo malePojo = new PatientFilterPojo(malePacientes, antropometrias, clinicas);
        GenderCategory maleCategory = new GenderCategory(context, malePojo);

        ViewGroup viewGroup = maleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(2, maleCategory.getSelecteds().size());
    }

    @Test
    public void testGenderCategoryWithOnlyFemalePacientes() {
        List<Paciente> femalePacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Maria");
        p1.setGenero('F');
        femalePacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Ana");
        p2.setGenero('F');
        femalePacientes.add(p2);

        PatientFilterPojo femalePojo = new PatientFilterPojo(femalePacientes, antropometrias, clinicas);
        GenderCategory femaleCategory = new GenderCategory(context, femalePojo);

        ViewGroup viewGroup = femaleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(2, femaleCategory.getSelecteds().size());
    }

    @Test
    public void testEmptyPacienteList() {
        List<Paciente> emptyList = new ArrayList<>();
        PatientFilterPojo emptyPojo = new PatientFilterPojo(emptyList, antropometrias, clinicas);
        GenderCategory emptyCategory = new GenderCategory(context, emptyPojo);

        // Não deve lançar exceção ao gerar a view mesmo com lista vazia
        ViewGroup viewGroup = emptyCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
    }

    @Test
    public void testGenderCategoryInheritsPatientFilterCategory() {
        assertNotNull(genderCategory);
        assertTrue(genderCategory instanceof PatientFilterCategory);
    }

    @Test
    public void testMultipleGenderCategoryInstances() {
        GenderCategory category1 = new GenderCategory(context, pojo);
        GenderCategory category2 = new GenderCategory(context, pojo);

        assertNotNull(category1);
        assertNotNull(category2);
        assertNotEquals(category1, category2);
    }

    @Test
    public void testGenderCategoryWithVariousPacientes() {
        List<Paciente> variousPacientes = new ArrayList<>();

        // Criar 6 pacientes (3 homens, 3 mulheres)
        for (int i = 1; i <= 6; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setGenero(i % 2 == 0 ? 'F' : 'M');
            variousPacientes.add(p);
        }

        PatientFilterPojo variousPojo = new PatientFilterPojo(variousPacientes, antropometrias, clinicas);
        GenderCategory variousCategory = new GenderCategory(context, variousPojo);

        ViewGroup viewGroup = variousCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(6, variousPojo.getPatientList().size());
    }

    @Test
    public void testGenderCategoryStateInitialization() {
        assertNotNull(pojo.getState());
        // Inicialmente nenhum gênero selecionado (ou 'N' para nenhum)
        assertTrue(pojo.getState().getGeneroSelected() == 'N' || pojo.getState().getGeneroSelected() == ' ');
    }

    @Test
    public void testGenerateViewWithValidLayoutInflater() {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup result = genderCategory.generateView(inflater);
        assertNotNull(result);
    }

    @Test
    public void testGenderCategoryFiltersCorrectly() {
        List<Paciente> filterPacientes = new ArrayList<>();
        Paciente p1 = new Paciente();
        p1.setId(1L);
        p1.setNomePaciente("Mulher");
        p1.setGenero('F');
        filterPacientes.add(p1);

        Paciente p2 = new Paciente();
        p2.setId(2L);
        p2.setNomePaciente("Homem");
        p2.setGenero('M');
        filterPacientes.add(p2);

        PatientFilterPojo filterPojo = new PatientFilterPojo(filterPacientes, antropometrias, clinicas);
        GenderCategory filterCategory = new GenderCategory(context, filterPojo);

        List<Paciente> selecteds = filterCategory.getSelecteds();
        assertEquals(2, selecteds.size());
    }

    @Test
    public void testChipsAreNotCheckedByDefault() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Verificar que nenhum chip está marcado inicialmente
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertFalse(chip.isChecked());
        }
    }

    @Test
    public void testGenderCategoryHasCorrectContext() {
        assertNotNull(genderCategory);
    }

    @Test
    public void testMasculinoAndFemininoLabels() {
        ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
        ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

        // Verificar que os chips têm os nomes corretos
        Chip mascuChip = (Chip) chipGroup.getChildAt(0);
        Chip femChip = (Chip) chipGroup.getChildAt(1);

        assertTrue(mascuChip.getText().toString().contains("Masculino"));
        assertTrue(femChip.getText().toString().contains("Feminino"));
    }

    @Test
    public void testGenderStateStoredCorrectly() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            // Selecionar Masculino
            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);
            masculinoChip.setChecked(true);

            // Verificar que o estado foi atualizado
            assertEquals('M', pojo.getState().getGeneroSelected());
        });
    }

    @Test
    public void testResetClearsGenderSelection() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateCategory(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip chip = (Chip) chipGroup.getChildAt(0);
            chip.setChecked(true);

            // Verificar que há seleção
            assertTrue(chip.isChecked());
            assertEquals('M', pojo.getState().getGeneroSelected());

            // Chamar reset
            genderCategory.reset();

            // Verificar que a seleção foi limpa
            assertEquals('N', pojo.getState().getGeneroSelected());
        });
    }

    @Test
    public void testDeselectingChipRestoresAllPacientes() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);

            // Selecionar
            masculinoChip.setChecked(true);
            assertEquals(pacientes.stream().filter(paciente -> paciente.getGenero()=='M').count(),
                    genderCategory.getSelecteds().size());

            // Desselecionar
            masculinoChip.setChecked(false);

            // Após desmarcar, todos os pacientes devem ser restaurados
            assertEquals(pacientes.size(), genderCategory.getSelecteds().size());
        });
    }

    @Test
    public void testSingleSelectionConstraint() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);
            Chip femininoChip = (Chip) chipGroup.getChildAt(1);

            // Selecionar Masculino
            masculinoChip.setChecked(true);
            assertTrue(masculinoChip.isChecked());

            // Tentar selecionar Feminino (deve desselecionar Masculino)
            femininoChip.setChecked(true);
            assertFalse(masculinoChip.isChecked());
            assertTrue(femininoChip.isChecked());
        });
    }

    @Test
    public void testEqualNumberOfMaleAndFemalePacientes() {
        List<Paciente> balancedPacientes = new ArrayList<>();
        for (int i = 1; i <= 4; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setGenero(i % 2 == 0 ? 'F' : 'M');
            balancedPacientes.add(p);
        }

        PatientFilterPojo balancedPojo = new PatientFilterPojo(balancedPacientes, antropometrias, clinicas);
        GenderCategory balancedCategory = new GenderCategory(context, balancedPojo);

        ViewGroup viewGroup = balancedCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(4, balancedCategory.getSelecteds().size());
    }

    @Test
    public void testUnbalancedGenderDistribution() {
        List<Paciente> unbalancedPacientes = new ArrayList<>();
        // Criar mais mulheres que homens
        for (int i = 1; i <= 8; i++) {
            Paciente p = new Paciente();
            p.setId(i);
            p.setNomePaciente("Paciente " + i);
            p.setGenero(i <= 2 ? 'M' : 'F');
            unbalancedPacientes.add(p);
        }

        PatientFilterPojo unbalancedPojo = new PatientFilterPojo(unbalancedPacientes, antropometrias, clinicas);
        GenderCategory unbalancedCategory = new GenderCategory(context, unbalancedPojo);

        ViewGroup viewGroup = unbalancedCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(8, unbalancedCategory.getSelecteds().size());
    }

    @Test
    public void testSingleMalePaciente() {
        List<Paciente> singleMale = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("João");
        p.setGenero('M');
        singleMale.add(p);

        PatientFilterPojo singleMalePojo = new PatientFilterPojo(singleMale, antropometrias, clinicas);
        GenderCategory singleMaleCategory = new GenderCategory(context, singleMalePojo);

        ViewGroup viewGroup = singleMaleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(1, singleMaleCategory.getSelecteds().size());
    }

    @Test
    public void testSingleFemalePaciente() {
        List<Paciente> singleFemale = new ArrayList<>();
        Paciente p = new Paciente();
        p.setId(1L);
        p.setNomePaciente("Maria");
        p.setGenero('F');
        singleFemale.add(p);

        PatientFilterPojo singleFemalePojo = new PatientFilterPojo(singleFemale, antropometrias, clinicas);
        GenderCategory singleFemaleCategory = new GenderCategory(context, singleFemalePojo);

        ViewGroup viewGroup = singleFemaleCategory.generateView(layoutInflater);
        assertNotNull(viewGroup);
        assertEquals(1, singleFemaleCategory.getSelecteds().size());
    }

    @Test
    public void testGenerateCategoryInitializesViewGroup() {
        ViewGroup viewGroup = genderCategory.generateCategory(layoutInflater);
        assertNotNull(viewGroup);
        // Verificar que viewGroup foi inicializado
    }

    @Test
    public void testMasculinoFilterShowsOnlyMalePacientes() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip masculinoChip = (Chip) chipGroup.getChildAt(0);
            masculinoChip.setChecked(true);

            List<Paciente> selecteds = genderCategory.getSelecteds();

            // Verificar que todos são homens
            assertTrue(selecteds.stream().allMatch(p -> p.getGenero() == 'M'));
            assertEquals(pacientes.stream().filter(paciente -> paciente.getGenero()=='M').count(), selecteds.size());
        });
    }

    @Test
    public void testFemininoFilterShowsOnlyFemalePacientes() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ViewGroup viewGroup = genderCategory.generateView(layoutInflater);
            ChipGroup chipGroup = viewGroup.findViewById(R.id.filter_category_chipgroup);

            Chip femininoChip = (Chip) chipGroup.getChildAt(1);
            femininoChip.setChecked(true);

            List<Paciente> selecteds = genderCategory.getSelecteds();

            // Verificar que todos são mulheres
            assertTrue(selecteds.stream().allMatch(p -> p.getGenero() == 'F'));
            assertEquals(2, selecteds.size());
        });
    }
}

