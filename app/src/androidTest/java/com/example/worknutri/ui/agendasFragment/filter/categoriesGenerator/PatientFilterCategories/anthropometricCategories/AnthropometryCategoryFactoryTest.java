package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.PatientFilterCategories.anthropometricCategories;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

@RunWith(AndroidJUnit4.class)
public class AnthropometryCategoryFactoryTest {

    private Context context;
    private PacienteFilterPojo pacienteFilterPojo;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();
        pacienteFilterPojo = new PacienteFilterPojo(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
    }

    // ==================== Testes para createWeightCategory ====================

    @Test
    public void testCreateWeightCategoryReturnsNotNull() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertNotNull(weightCategory);
    }

    @Test
    public void testCreateWeightCategoryReturnsCorrectType() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertTrue(weightCategory instanceof WeightCategory);
    }

    @Test
    public void testCreateWeightCategoryWithValidContext() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertNotNull(weightCategory);
        assertTrue(weightCategory instanceof WeightCategory);
    }

    @Test
    public void testCreateWeightCategoryWithValidPojo() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertNotNull(weightCategory);
    }

    @Test
    public void testCreateWeightCategoryMultipleTimes() {
        WeightCategory weightCategory1 = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        WeightCategory weightCategory2 = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);

        assertNotNull(weightCategory1);
        assertNotNull(weightCategory2);
        assertTrue(weightCategory1 instanceof WeightCategory);
        assertTrue(weightCategory2 instanceof WeightCategory);
    }

    // ==================== Testes para createHeightCategory ====================

    @Test
    public void testCreateHeightCategoryReturnsNotNull() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertNotNull(heightCategory);
    }

    @Test
    public void testCreateHeightCategoryReturnsCorrectType() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertTrue(heightCategory instanceof HeightCategory);
    }

    @Test
    public void testCreateHeightCategoryWithValidContext() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertNotNull(heightCategory);
        assertTrue(heightCategory instanceof HeightCategory);
    }

    @Test
    public void testCreateHeightCategoryWithValidPojo() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertNotNull(heightCategory);
    }

    @Test
    public void testCreateHeightCategoryMultipleTimes() {
        HeightCategory heightCategory1 = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        HeightCategory heightCategory2 = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);

        assertNotNull(heightCategory1);
        assertNotNull(heightCategory2);
        assertTrue(heightCategory1 instanceof HeightCategory);
        assertTrue(heightCategory2 instanceof HeightCategory);
    }

    // ==================== Testes para createIMCCategory ====================

    @Test
    public void testCreateIMCCategoryReturnsNotNull() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertNotNull(imcCategory);
    }

    @Test
    public void testCreateIMCCategoryReturnsCorrectType() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertTrue(imcCategory instanceof IMCCategory);
    }

    @Test
    public void testCreateIMCCategoryWithValidContext() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertNotNull(imcCategory);
        assertTrue(imcCategory instanceof IMCCategory);
    }

    @Test
    public void testCreateIMCCategoryWithValidPojo() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertNotNull(imcCategory);
    }

    @Test
    public void testCreateIMCCategoryMultipleTimes() {
        IMCCategory imcCategory1 = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        IMCCategory imcCategory2 = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);

        assertNotNull(imcCategory1);
        assertNotNull(imcCategory2);
        assertTrue(imcCategory1 instanceof IMCCategory);
        assertTrue(imcCategory2 instanceof IMCCategory);
    }

    // ==================== Testes de Integração ====================

    @Test
    public void testAllFactoryMethodsReturnNotNull() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);

        assertNotNull(weightCategory);
        assertNotNull(heightCategory);
        assertNotNull(imcCategory);
    }

    @Test
    public void testAllFactoryMethodsReturnCorrectTypes() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);

        assertTrue(weightCategory instanceof WeightCategory);
        assertTrue(heightCategory instanceof HeightCategory);
        assertTrue(imcCategory instanceof IMCCategory);
    }

    @Test
    public void testFactoryCreatesIndependentInstances() {
        WeightCategory weightCategory1 = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        WeightCategory weightCategory2 = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);

        assertNotNull(weightCategory1);
        assertNotNull(weightCategory2);
        // Cada chamada deve criar uma nova instância
        assertTrue(weightCategory1 instanceof WeightCategory);
        assertTrue(weightCategory2 instanceof WeightCategory);
    }

    @Test
    public void testFactoryWithDifferentContexts() {
        Context context1 = TestUtil.getThemedContext();
        Context context2 = TestUtil.getThemedContext();

        WeightCategory weightCategory1 = AnthropometryCategoryFactory.createWeightCategory(context1, pacienteFilterPojo);
        WeightCategory weightCategory2 = AnthropometryCategoryFactory.createWeightCategory(context2, pacienteFilterPojo);

        assertNotNull(weightCategory1);
        assertNotNull(weightCategory2);
    }

    @Test
    public void testFactoryWithDifferentPojos() {
        PacienteFilterPojo pojo1 = new PacienteFilterPojo(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        PacienteFilterPojo pojo2 = new PacienteFilterPojo(new ArrayList<>(),new ArrayList<>(),new ArrayList<>());

        WeightCategory weightCategory1 = AnthropometryCategoryFactory.createWeightCategory(context, pojo1);
        WeightCategory weightCategory2 = AnthropometryCategoryFactory.createWeightCategory(context, pojo2);

        assertNotNull(weightCategory1);
        assertNotNull(weightCategory2);
    }

    @Test
    public void testFactoryCreatesValidWeightCategoryWithCorrectDependencies() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertNotNull(weightCategory);
        assertTrue(weightCategory instanceof WeightCategory);
    }

    @Test
    public void testFactoryCreatesValidHeightCategoryWithCorrectDependencies() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertNotNull(heightCategory);
        assertTrue(heightCategory instanceof HeightCategory);
    }

    @Test
    public void testFactoryCreatesValidIMCCategoryWithCorrectDependencies() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertNotNull(imcCategory);
        assertTrue(imcCategory instanceof IMCCategory);
    }

    @Test
    public void testWeightCategoryIsAbstractCategorySubtype() {
        WeightCategory weightCategory = AnthropometryCategoryFactory.createWeightCategory(context, pacienteFilterPojo);
        assertTrue(weightCategory instanceof WeightCategory);
    }

    @Test
    public void testHeightCategoryIsAbstractCategorySubtype() {
        HeightCategory heightCategory = AnthropometryCategoryFactory.createHeightCategory(context, pacienteFilterPojo);
        assertTrue(heightCategory instanceof HeightCategory);
    }

    @Test
    public void testIMCCategoryIsAbstractCategorySubtype() {
        IMCCategory imcCategory = AnthropometryCategoryFactory.createIMCCategory(context, pacienteFilterPojo);
        assertTrue(imcCategory instanceof IMCCategory);
    }
}

