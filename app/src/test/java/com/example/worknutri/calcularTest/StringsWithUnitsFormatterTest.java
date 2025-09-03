package com.example.worknutri.calcularTest;

import com.example.worknutri.calcular.StringWithUnitsFormatter;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Modifier;

public class StringsWithUnitsFormatterTest {

    @Test
    public void withKcalMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 Kcal",StringWithUnitsFormatter.withKcal("70.5"));
    }

    @Test
    public void withCmMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 cm",StringWithUnitsFormatter.withCm("70.5"));
    }

    @Test
    public void withKgMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 kg",StringWithUnitsFormatter.withKg("70.5"));
    }

    @Test
    public void withMetersMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 m",StringWithUnitsFormatter.withMeters("70.5"));
    }

    @Test
    public void withMlMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 ml",StringWithUnitsFormatter.withMl("70.5"));
    }

    @Test
    public void withKcalDiaMethodReturnUnitCorrect() {
        Assert.assertEquals("70.5 Kcal/Dia",StringWithUnitsFormatter.withKcalDia("70.5"));
    }

    @Test
    public void StringWithUnitsFormatterIsAbstract() {
        Assert.assertTrue(Modifier.isAbstract(StringWithUnitsFormatter.class.getModifiers()));
    }




}
