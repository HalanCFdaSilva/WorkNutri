package com.example.worknutri.calcularTest;

import com.example.worknutri.calcular.MeasureConverter;
import com.example.worknutri.calcular.MeasureTypes;

import org.junit.Assert;
import org.junit.Test;

public class MeasureConverterTest {

    @Test
    public void methodConvertToGramOrMetersConvertCorrectTheValuesToGram() {
        Assert.assertEquals(0.001, MeasureConverter.convertToGramOrMeters(MeasureTypes.MILI, 1.0), 0.0001);
        Assert.assertEquals(0.01, MeasureConverter.convertToGramOrMeters(MeasureTypes.CENTI, 1.0), 0.0001);
        Assert.assertEquals(0.1, MeasureConverter.convertToGramOrMeters(MeasureTypes.DECI, 1.0), 0.0001);
        Assert.assertEquals(1.0, MeasureConverter.convertToGramOrMeters(MeasureTypes.GRAM_METER, 1.0), 0.0001);
        Assert.assertEquals(10.0, MeasureConverter.convertToGramOrMeters(MeasureTypes.DECA, 1.0), 0.0001);
        Assert.assertEquals(100.0, MeasureConverter.convertToGramOrMeters(MeasureTypes.HECTO, 1.0), 0.0001);
        Assert.assertEquals(1000.0, MeasureConverter.convertToGramOrMeters(MeasureTypes.KILO, 1.0), 0.0001);

    }

    @Test
    public void methodConvertToGramOrMetersDoesNotAlterTheOriginalValue() {
        double originalValue = 5.0;
        double valueConverter = MeasureConverter.convertToGramOrMeters(MeasureTypes.CENTI, originalValue);
        Assert.assertEquals(5.0, originalValue, 0.0001);
        Assert.assertNotEquals(originalValue, valueConverter);
    }
    @Test
    public void methodConvertToMiliConvertCorrectTheValuesToMili() {
        Assert.assertEquals(1.0, MeasureConverter.convertToMili(MeasureTypes.MILI, 1.0), 0.0001);
        Assert.assertEquals(0.1, MeasureConverter.convertToMili(MeasureTypes.CENTI, 1.0), 0.0001);
        Assert.assertEquals(0.01, MeasureConverter.convertToMili(MeasureTypes.DECI, 1.0), 0.0001);
        Assert.assertEquals(0.001, MeasureConverter.convertToMili(MeasureTypes.GRAM_METER, 1.0), 0.0001);
        Assert.assertEquals(0.0001, MeasureConverter.convertToMili(MeasureTypes.DECA, 1.0), 0.0001);
        Assert.assertEquals(0.00001, MeasureConverter.convertToMili(MeasureTypes.HECTO, 1.0), 0.0001);
        Assert.assertEquals(0.000001, MeasureConverter.convertToMili(MeasureTypes.KILO, 1.0), 0.0001);

    }

    @Test
    public void methodConvertToMiliDoesNotAlterTheOriginalValue() {
        double originalValue = 5.0;
        double valueConverter = MeasureConverter.convertToMili(MeasureTypes.CENTI, originalValue);
        Assert.assertEquals(5.0, originalValue, 0.0001);
        Assert.assertNotEquals(originalValue, valueConverter);
    }
}
