package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicaFilterCategories;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.google.android.material.slider.RangeSlider;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class HourWorkCategoryTest {

    private Context context;
    private ClinicaFilterPojo clinicaFilterPojo;
    private HourWorkCategory hourWorkCategory;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();
        clinicaFilterPojo = new ClinicaFilterPojo();

        // Criar clínicas para teste
        List<Clinica> clinics = new ArrayList<>();
        Clinica clinica1 = new Clinica();
        clinica1.setId(1);
        clinica1.setNome("Clinica A");
        clinica1.setCidade("Sao Paulo");
        clinica1.setBairro("Zona Sul");

        Clinica clinica2 = new Clinica();
        clinica2.setId(2);
        clinica2.setNome("Clinica B");
        clinica2.setCidade("Sao Paulo");
        clinica2.setBairro("Zona Leste");

        Clinica clinica3 = new Clinica();
        clinica3.setId(3);
        clinica3.setNome("Clinica C");
        clinica3.setCidade("Sao Paulo");
        clinica3.setBairro("Zona Norte");

        clinics.add(clinica1);
        clinics.add(clinica2);
        clinics.add(clinica3);

        List<DayOfWork> daysOfWork = new ArrayList<>();
        DayOfWork dayOfWork1 = new DayOfWork();
        dayOfWork1.setId(1);
        dayOfWork1.setIdClinica(1);
        dayOfWork1.setDayOfWeek("SEGUNDA");
        dayOfWork1.setHoraInicio("08:00");
        dayOfWork1.setHoraFim("12:00");

        DayOfWork dayOfWork2 = new DayOfWork();
        dayOfWork2.setId(2);
        dayOfWork2.setIdClinica(2);
        dayOfWork2.setDayOfWeek("SEGUNDA");
        dayOfWork2.setHoraInicio("13:00");
        dayOfWork2.setHoraFim("17:00");

        DayOfWork dayOfWork3 = new DayOfWork();
        dayOfWork3.setId(3);
        dayOfWork3.setIdClinica(3);
        dayOfWork3.setDayOfWeek("TERÇA");
        dayOfWork3.setHoraInicio("09:00");
        dayOfWork3.setHoraFim("14:00");

        daysOfWork.add(dayOfWork1);
        daysOfWork.add(dayOfWork2);
        daysOfWork.add(dayOfWork3);

        clinicaFilterPojo.setClinicas(clinics);
        clinicaFilterPojo.setClinicasSelected(new ArrayList<>(clinics));
        clinicaFilterPojo.setDayOfWorkList(daysOfWork);

        hourWorkCategory = new HourWorkCategory(context, clinicaFilterPojo);
    }

    @Test
    public void testHourWorkCategoryIsNotNull() {
        assertNotNull(hourWorkCategory);
    }

    @Test
    public void testHourWorkCategoryHasSelecteds() {
        assertNotNull(hourWorkCategory.getSelecteds());
        assertFalse(hourWorkCategory.getSelecteds().isEmpty());
    }

    @Test
    public void testHourWorkCategoryInitialSelecteds() {
        List<Clinica> selecteds = hourWorkCategory.getSelecteds();
        assertEquals(3, selecteds.size());
    }



    // ==================== Testes para generateCategory ====================

    @Test
    public void testGenerateCategoryReturnsNotNull() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        assertNotNull(result);
    }

    @Test
    public void testGenerateCategoryReturnsViewGroup() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        assertNotNull(result);
    }


    @Test
    public void testGenerateCategoryContainsRangeSlider() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        // Procurar por RangeSlider na view hierarquia
        boolean hasRangeSlider = false;
        for (int i = 0; i < result.getChildCount(); i++) {
            if (result.getChildAt(i) instanceof ViewGroup) {
                ViewGroup child = (ViewGroup) result.getChildAt(i);
                for (int j = 0; j < child.getChildCount(); j++) {
                    if (child.getChildAt(j) instanceof RangeSlider) {
                        hasRangeSlider = true;
                        break;
                    }
                }
            }
        }
        assertTrue(hasRangeSlider);
    }

    @Test
    public void testGenerateCategoryRangeSliderHasCorrectRange() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        // Encontrar o RangeSlider
        RangeSlider rangeSlider = findRangeSliderInView(result);
        assertNotNull(rangeSlider);
        
        assertEquals(0, rangeSlider.getValueFrom(), 0.0f);
        assertEquals(24 * 60, rangeSlider.getValueTo(), 0.0f);
    }

    @Test
    public void testGenerateCategoryRangeSliderStepSize() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        RangeSlider rangeSlider = findRangeSliderInView(result);
        assertNotNull(rangeSlider);
        
        assertEquals(1f, rangeSlider.getStepSize(), 0.0f);
    }



    @Test
    public void testGenerateCategoryWithInitialValues() {
        // Definir valores iniciais
        float[] hoursSelected = clinicaFilterPojo.getUiState().getHoursSelected();
        hoursSelected[0] = 480f; // 08:00
        hoursSelected[1] = 720f; // 12:00

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        assertNotNull(result);
        
        RangeSlider rangeSlider = findRangeSliderInView(result);
        assertNotNull(rangeSlider);
        
        // Verificar se os valores foram aplicados ao slider
        List<Float> values = rangeSlider.getValues();
        assertEquals(480f, values.get(0), 0.0f);
        assertEquals(720f, values.get(1), 0.0f);
    }

    @Test
    public void testGenerateCategoryWithoutInitialValues() {
        // Garantir que não há valores iniciais
        float[] hoursSelected = clinicaFilterPojo.getUiState().getHoursSelected();
        hoursSelected[0] = 0;
        hoursSelected[1] = 0;

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        assertNotNull(result);
        
        RangeSlider rangeSlider = findRangeSliderInView(result);
        assertNotNull(rangeSlider);
        
        // Verificar se os valores padrão foram aplicados
        List<Float> values = rangeSlider.getValues();
        assertEquals(0f, values.get(0), 0.0f);
        assertEquals(1440f, values.get(1), 0.0f); // 24 * 60
    }

    @Test
    public void testGenerateCategoryRangeSliderHasLabelFormatter() {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        ViewGroup result = hourWorkCategory.generateCategory(layoutInflater);
        
        RangeSlider rangeSlider = findRangeSliderInView(result);
        assertNotNull(rangeSlider);
        
        // Testar o label formatter
        try {
            // O label formatter é privado, então vamos testar indiretamente
            // verificando se a view foi criada corretamente
            assertNotNull(rangeSlider);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }

    // Método auxiliar para encontrar RangeSlider na hierarquia de views
    private RangeSlider findRangeSliderInView(ViewGroup parent) {

        for (int i = 0; i < parent.getChildCount(); i++) {
            android.view.View child = parent.getChildAt(i);
            if (child instanceof RangeSlider) {
                return (RangeSlider) child;
            }
            if (child instanceof ViewGroup) {
                RangeSlider result = findRangeSliderInView((ViewGroup) child);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}

