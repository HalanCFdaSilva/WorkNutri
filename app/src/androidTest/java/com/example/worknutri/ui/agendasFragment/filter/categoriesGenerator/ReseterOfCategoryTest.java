package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ReseterOfCategoryTest {

    private Context context;
    private ChipGroup chipGroup;
    private RangeSlider rangeSlider;

    @Before
    public void setUp() {
        context = TestUtil.getContextWithFilterTheme();


        chipGroup = new ChipGroup(context);

        rangeSlider = new RangeSlider(context);
        rangeSlider.setValueFrom(0f);
        rangeSlider.setValueTo(100f);
        rangeSlider.setValues(25f, 75f);
    }

    // Testes para resetChipGroup

    @Test
    public void testResetChipGroupResetChipsToUnchecked() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            chipGroup.addView(generateChip(true));
            chipGroup.addView(generateChip(false));
            chipGroup.addView(generateChip(true));

            ReseterOfCategory.resetChipGroup(chipGroup);

            for (int i = 0; i < chipGroup.getChildCount(); i++) {
                Chip chip = (Chip) chipGroup.getChildAt(i);
                assertFalse(chip.isChecked());
            }
        });
    }

    @NonNull
    private Chip generateChip(boolean checked) {
        Chip chip = new Chip(context);
        chip.setText("Chip 1");
        chip.setCheckable(true);
        chip.setChecked(checked);
        return chip;
    }

    @Test
    public void testResetChipGroupDontInsertChips() {
        ActivityScenario<ActivityToTest> scenario = ActivityScenario.launch(ActivityToTest.class);
        scenario.onActivity(activity -> {
            ArrayList<Chip> chips = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                Chip chip = generateChip(true);
                chip.setId(i + 1);
                chips.add(chip);
                chipGroup.addView(chip);
            }
            assertHasOnlyTheChipsInserted(chips);

            ReseterOfCategory.resetChipGroup(chipGroup);

            assertHasOnlyTheChipsInserted(chips);
        });

    }

    private void assertHasOnlyTheChipsInserted(ArrayList<Chip> chipsExpected) {
        assertEquals(chipsExpected.size(),chipGroup.getChildCount());
        for (int i = 0; i < chipGroup.getChildCount(); i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            assertTrue(chipsExpected.contains(chip));
        }
    }


    // Testes para resetSlider

    @Test
    public void testResetSliderResetRangeSliderToMinAndMaxValues() {
        float[] valuesInState = new float[2];
        rangeSlider.setValues(25f, 75f);

        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);

        assertRangeSliderIsResetedCorrectly(rangeSlider);
    }



    @Test
    public void testResetSliderMultipleTimesRunPerfectly() {
        // Arrange
        float[] valuesInState = new float[2];

        // Act - resetar múltiplas vezes
        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);
        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);
        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);

        assertRangeSliderIsResetedCorrectly(rangeSlider);
    }

    @Test
    public void testResetSliderWithDifferentRangesResetToActualRange() {
        RangeSlider customRangeSlider = new RangeSlider(context);
        customRangeSlider.setValueFrom(10f);
        customRangeSlider.setValueTo(50f);
        customRangeSlider.setValues(15f, 45f);

        assertNotEquals(rangeSlider.getValueFrom(),customRangeSlider.getValueFrom(), 0.0);
        assertNotEquals(rangeSlider.getValueTo(),customRangeSlider.getValueTo(), 0.0);
        float[] valuesInState = new float[2];

        ReseterOfCategory.resetSlider(customRangeSlider, valuesInState);

        // Assert
        assertEquals(customRangeSlider.getValueFrom(), valuesInState[0], 0.0);
        assertEquals(customRangeSlider.getValueTo(), valuesInState[1], 0.0);
        assertRangeSliderIsResetedCorrectly(customRangeSlider);
    }

    @Test
    public void testResetSliderWithNegativeValues() {
        // Arrange
        RangeSlider negativeRangeSlider = new RangeSlider(context);
        negativeRangeSlider.setValueFrom(-50f);
        negativeRangeSlider.setValueTo(50f);
        negativeRangeSlider.setValues(-25f, 25f);

        float[] valuesInState = new float[2];

        ReseterOfCategory.resetSlider(negativeRangeSlider, valuesInState);

        assertRangeSliderIsResetedCorrectly(negativeRangeSlider);
    }

    private void assertRangeSliderIsResetedCorrectly(RangeSlider rangeSlider) {
        List<Float> valuesInRangeSlider = rangeSlider.getValues();
        assertEquals(valuesInRangeSlider.get(0), rangeSlider.getValueFrom(), 0.0);
        assertEquals(valuesInRangeSlider.get(1), rangeSlider.getValueTo(), 0.0);
    }

    @Test
    public void testResetSliderUpdatesValuesArray() {
        // Arrange
        float[] valuesInState = new float[2];
        valuesInState[0] = 50f;
        valuesInState[1] = 50f;

        // Act
        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);

        // Assert
        assertEquals(rangeSlider.getValueFrom(), valuesInState[0], 0.0); // valueFrom
        assertEquals(rangeSlider.getValueTo(), valuesInState[1], 0.0); // valueTo
    }

    @Test
    public void testResetSliderPreservesRangeSliderProperties() {
        // Arrange
        float originalValueFrom = rangeSlider.getValueFrom();
        float originalValueTo = rangeSlider.getValueTo();
        float originalStepSize = rangeSlider.getStepSize();

        float[] valuesInState = new float[2];

        // Act
        ReseterOfCategory.resetSlider(rangeSlider, valuesInState);

        // Assert
        assertEquals(rangeSlider.getValueFrom(), originalValueFrom, 0.0);
        assertEquals(rangeSlider.getValueTo(), originalValueTo, 0.0);
        assertEquals(rangeSlider.getStepSize(), originalStepSize, 0.0);
    }



}

