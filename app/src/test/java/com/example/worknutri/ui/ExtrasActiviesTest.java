package com.example.worknutri.ui;

import org.junit.Assert;
import org.junit.Test;

public class ExtrasActiviesTest {

    @Test
    public void testExtrasActivitiesEnum() {
        Assert.assertEquals("IS_PACIENTE", ExtrasActivities.SCHEDULE_EXTRA.getKey());
        Assert.assertEquals("PACIENTE", ExtrasActivities.PACIENTE_EXTRA.getKey());
        Assert.assertEquals("CLINICA", ExtrasActivities.CLINICA_EXTRA.getKey());
    }

    @Test
    public void verifyIfValuesMethodIsInCorrectOrder() {
        ExtrasActivities[] values = ExtrasActivities.values();
        Assert.assertEquals(ExtrasActivities.SCHEDULE_EXTRA, values[0]);
        Assert.assertEquals(ExtrasActivities.PACIENTE_EXTRA, values[1]);
        Assert.assertEquals(ExtrasActivities.CLINICA_EXTRA, values[2]);
    }

    @Test
    public void verifyIfValuesMethodHasCorrectSize() {
        Assert.assertEquals(3, ExtrasActivities.values().length);
    }
    @Test
    public void testExtrasActivitiesKeyIsNotEmpty() {
        for (ExtrasActivities activity : ExtrasActivities.values()) {
            Assert.assertNotNull(activity.getKey());
            Assert.assertFalse(activity.getKey().isEmpty());
        }
    }

}
