package com.example.worknutri.ui.agendasFragment.filter;

import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderFilterSelectedsByTest {

    @Test
    public void testNameAscValueIsCorrectly() {
        assertEquals("NOME_ASC", OrderFilterSelectedsBy.NAME_ASC.getValue());
    }

    @Test
    public void testNameDescValueIsCorrectly() {
        assertEquals("NOME_DESC", OrderFilterSelectedsBy.NAME_DESC.getValue());
    }

    @Test
    public void testImcCategoryValueIsCorrectly() {
        assertEquals("IMC_CATEGORY", OrderFilterSelectedsBy.IMC_CATEGORY.getValue());
    }

    @Test
    public void testHeightValueIsCorrectly() {
        assertEquals("ALTURA", OrderFilterSelectedsBy.HEIGHT.getValue());
    }

    @Test
    public void testWeightValueIsCorrectly() {
        assertEquals("PESO", OrderFilterSelectedsBy.WEIGHT.getValue());
    }

    @Test
    public void testAgeValueIsCorrectly() {
        assertEquals("IDADE", OrderFilterSelectedsBy.AGE.getValue());
    }

    @Test
    public void testDistrictValueIsCorrectly() {
        assertEquals("BAIRRO", OrderFilterSelectedsBy.DISTRICT.getValue());
    }

    @Test
    public void testCityValueIsCorrectly() {
        assertEquals("CIDADE", OrderFilterSelectedsBy.CITY.getValue());
    }

    @Test
    public void testDayOfWeekValueIsCorrectly() {
        assertEquals("DIA_DA_SEMANA", OrderFilterSelectedsBy.DAY_OF_WEEK.getValue());
    }

    @Test
    public void testNumberOfPatientsValueIsCorrectly() {
        assertEquals("NUMERO_DE_PACIENTES", OrderFilterSelectedsBy.NUMBER_OF_PATIENTS.getValue());
    }

    @Test
    public void testAllEnumValuesHaveValuesIsCorrectly() {
        for (OrderFilterSelectedsBy order : OrderFilterSelectedsBy.values()) {
            // Verifies that all enum values have non-null values
            assert order.getValue() != null : "Enum value " + order.name() + " has null getValue()";
            assert !order.getValue().isEmpty() : "Enum value " + order.name() + " has empty getValue()";
        }
    }
}

