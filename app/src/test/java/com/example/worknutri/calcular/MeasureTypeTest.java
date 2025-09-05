package com.example.worknutri.calcular;

import org.junit.Assert;
import org.junit.Test;

public class MeasureTypeTest {

    @Test
    public void methodGetValueReturnCorrectValue(){
        Assert.assertEquals(-3, MeasureTypes.KILO.getValue());
        Assert.assertEquals(-2, MeasureTypes.HECTO.getValue());
        Assert.assertEquals(-1, MeasureTypes.DECA.getValue());
        Assert.assertEquals(0, MeasureTypes.GRAM_METER.getValue());
        Assert.assertEquals(1, MeasureTypes.DECI.getValue());
        Assert.assertEquals(2, MeasureTypes.CENTI.getValue());
        Assert.assertEquals(3, MeasureTypes.MILI.getValue());
    }

    @Test
    public void methodFromValueReturnCorrectEnum(){
        Assert.assertEquals(MeasureTypes.KILO, MeasureTypes.fromValue(-3));
        Assert.assertEquals(MeasureTypes.HECTO, MeasureTypes.fromValue(-2));
        Assert.assertEquals(MeasureTypes.DECA, MeasureTypes.fromValue(-1));
        Assert.assertEquals(MeasureTypes.GRAM_METER, MeasureTypes.fromValue(0));
        Assert.assertEquals(MeasureTypes.DECI, MeasureTypes.fromValue(1));
        Assert.assertEquals(MeasureTypes.CENTI, MeasureTypes.fromValue(2));
        Assert.assertEquals(MeasureTypes.MILI, MeasureTypes.fromValue(3));
    }

    @Test
    public void methodFromValueThrowIllegalArgumentExceptionWhenValueIsInvalid(){
        var ref = new Object() {
            int value = 4;
        };
        Assert.assertThrows("Invalid measure type value: "+ ref.value,IllegalArgumentException.class,()->MeasureTypes.fromValue(ref.value));
        ref.value = -4;
        Assert.assertThrows("Invalid measure type value: "+ ref.value,IllegalArgumentException.class,()->MeasureTypes.fromValue(ref.value));
    }

    @Test
    public void numberOfMeasureTypesIsCorrect(){
        Assert.assertEquals(7, MeasureTypes.values().length);
    }
}
