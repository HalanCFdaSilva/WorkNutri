package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.support.TestUtil;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class CategoriesGeneratorUtilTest {

    private LayoutInflater layoutInflater;
    private CategoriesGeneratorUtil util;

    @Before
    public void setUp() {
        Context context = TestUtil.getContextWithFilterTheme();
        layoutInflater = LayoutInflater.from(context);
        util = new CategoriesGeneratorUtil(context);
    }

    // Testes para generateChip

    @Test
    public void testGenerateChipReturnsNotNull() {
        Chip chip = util.generateChip("Test Filter");
        assertNotNull(chip);
    }

    @Test
    public void testGenerateChipTextIsCorrect() {
        String filterText = "My Filter";
        Chip chip = util.generateChip(filterText);
        assertEquals(filterText, chip.getText().toString());
    }

    @Test
    public void testGenerateChipWithEmptyString() {
        Chip chip = util.generateChip("");
        assertNotNull(chip);
        assertEquals("", chip.getText().toString());
    }

    @Test
    public void testGenerateChipWithLongText() {
        String longText = "this is a text very large to a chip with can cause problems";
        Chip chip = util.generateChip(longText);
        assertNotNull(chip);
        assertEquals(longText, chip.getText().toString());
    }

    @Test
    public void testGenerateChipWithSpecialCharacters() {
        String specialText = "Chip @#$%&*!";
        Chip chip = util.generateChip(specialText);
        assertEquals(specialText, chip.getText().toString());
    }

    @Test
    public void testGenerateMultipleChipsWithDifferentTexts() {
        Chip chip1 = util.generateChip("Filter 1");
        Chip chip2 = util.generateChip("Filter 2");
        Chip chip3 = util.generateChip("Filter 3");

        assertNotNull(chip1);
        assertNotNull(chip2);
        assertNotNull(chip3);
        assertEquals("Filter 1", chip1.getText().toString());
        assertEquals("Filter 2", chip2.getText().toString());
        assertEquals("Filter 3", chip3.getText().toString());
    }

    @Test
    public void testGenerateChipWithNumberText() {
        Chip chip = util.generateChip("123");
        assertEquals("123", chip.getText().toString());
    }

    // Testes para generateCategory

    @Test
    public void testGenerateCategoryReturnsNotNull() {
        ViewGroup viewGroup = util.generateCategory(layoutInflater, "Test Category");
        assertNotNull(viewGroup);
    }

    @Test
    public void testGenerateCategoryReturnsCorrectlyLayout() {
        ViewGroup viewGroup = util.generateCategory(layoutInflater, "Test Category");
        assertEquals(R.id.registry_filter_category,viewGroup.getId());
    }

    @Test
    public void testGenerateCategoryTitleIsCorrect() {
        String title = "My category";
        ViewGroup viewGroup = util.generateCategory(layoutInflater, title);

        TextView titleView = viewGroup.findViewById(R.id.registry_filter_category_title);
        assertNotNull(titleView);
        assertEquals(title, titleView.getText().toString());
    }

    @Test
    public void testGenerateCategoryWithEmptyTitle() {
        ViewGroup viewGroup = util.generateCategory(layoutInflater, "");

        TextView titleView = viewGroup.findViewById(R.id.registry_filter_category_title);
        assertNotNull(titleView);
        assertEquals("", titleView.getText().toString());
    }

    @Test
    public void testGenerateCategoryContainsInternalLayout() {
        ViewGroup viewGroup = util.generateCategory(layoutInflater, "Test");

        ViewGroup internalLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        assertNotNull(internalLayout);
    }

    @Test
    public void testGenerateCategoryWithLongTitle() {
        String longTitle = "This is one category with title very long that could cause problems " +
                "if not handled properly by the generateCategory method";
        ViewGroup viewGroup = util.generateCategory(layoutInflater, longTitle);

        TextView titleView = viewGroup.findViewById(R.id.registry_filter_category_title);
        assertEquals(longTitle, titleView.getText().toString());
    }

    @Test
    public void testGenerateMultipleCategoriesWithDifferentTitles() {
        ViewGroup cat1 = util.generateCategory(layoutInflater, "Category 1");
        ViewGroup cat2 = util.generateCategory(layoutInflater, "Category 2");
        ViewGroup cat3 = util.generateCategory(layoutInflater, "Category 3");

        TextView title1 = cat1.findViewById(R.id.registry_filter_category_title);
        TextView title2 = cat2.findViewById(R.id.registry_filter_category_title);
        TextView title3 = cat3.findViewById(R.id.registry_filter_category_title);

        assertEquals("Category 1", title1.getText().toString());
        assertEquals("Category 2", title2.getText().toString());
        assertEquals("Category 3", title3.getText().toString());
    }

    // Testes para generateChipGroup

    @Test
    public void testGenerateChipGroupReturnsNotNull() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        assertNotNull(scrollView);
    }

    @Test
    public void testGenerateChipGroupReturnsHorizontalScrollView() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        assertTrue(scrollView instanceof HorizontalScrollView);
    }

    @Test
    public void testGenerateChipGroupContainsChipGroup() {
        HorizontalScrollView scrollView = util.generateChipGroup();

        // O ChipGroup deve ser o primeiro (e único) filho do HorizontalScrollView
        assertTrue(scrollView.getChildCount() > 0);
        assertTrue(scrollView.getChildAt(0) instanceof ChipGroup);
    }

    @Test
    public void testGenerateChipGroupChipGroupIsSelectionNotRequired() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        // selectionRequired deve ser false
        assertNotNull(chipGroup);
        assertFalse(chipGroup.isSelectionRequired());
    }

    @Test
    public void testGenerateChipGroupChipGroupHasCorrectId() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        assertEquals(R.id.filter_category_chipgroup, chipGroup.getId());
    }

    @Test
    public void testGenerateChipGroupCanAddChips() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        Chip chip = util.generateChip("Test Chip");
        chipGroup.addView(chip);

        assertEquals(1, chipGroup.getChildCount());
        assertTrue(chipGroup.getChildAt(0) instanceof Chip);
    }

    @Test
    public void testGenerateChipGroupCanAddMultipleChips() {
        HorizontalScrollView scrollView = util.generateChipGroup();
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        for (int i = 0; i < 5; i++) {
            Chip chip = util.generateChip("Chip " + i);
            chipGroup.addView(chip);
        }

        assertEquals(5, chipGroup.getChildCount());
    }

    @Test
    public void testGenerateMultipleChipGroups() {
        HorizontalScrollView scroll1 = util.generateChipGroup();
        HorizontalScrollView scroll2 = util.generateChipGroup();

        assertNotNull(scroll1);
        assertNotNull(scroll2);
        assertNotSame(scroll1, scroll2);
    }

    // Testes para generateCategoryWithChipGroup

    @Test
    public void testGenerateCategoryWithChipGroupReturnsNotNull() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "Test Category");
        assertNotNull(viewGroup);
    }



    @Test
    public void testGenerateCategoryWithChipGroupTitleIsCorrect() {
        String title = "Category with Chips";
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, title);

        TextView titleView = viewGroup.findViewById(R.id.registry_filter_category_title);
        assertNotNull(titleView);
        assertEquals(title, titleView.getText().toString());
    }

    @Test
    public void testGenerateCategoryWithChipGroupContainsScrollView() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "Test");

        ViewGroup internalLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        assertNotNull(internalLayout);

        // Deve conter um HorizontalScrollView
        boolean hasScrollView = false;
        for (int i = 0; i < internalLayout.getChildCount(); i++) {
            if (internalLayout.getChildAt(i) instanceof HorizontalScrollView) {
                hasScrollView = true;
                break;
            }
        }
        assertTrue(hasScrollView);
    }

    @Test
    public void testGenerateCategoryWithChipGroupContainsChipGroup() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "Test");

        ViewGroup internalLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        assertNotNull(internalLayout);

        HorizontalScrollView scrollView = (HorizontalScrollView) internalLayout.getChildAt(0);
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        assertNotNull(chipGroup);
        assertEquals(R.id.filter_category_chipgroup, chipGroup.getId());
    }

    @Test
    public void testGenerateCategoryWithChipGroupCanAddChips() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "Test");

        ViewGroup internalLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        HorizontalScrollView scrollView = (HorizontalScrollView) internalLayout.getChildAt(0);
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        Chip chip = util.generateChip("Test Chip");
        chipGroup.addView(chip);

        assertEquals(1, chipGroup.getChildCount());
        assertEquals("Test Chip", chip.getText().toString());
    }

    @Test
    public void testGenerateCategoryWithChipGroupWithEmptyTitle() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "");

        TextView titleView = viewGroup.findViewById(R.id.registry_filter_category_title);
        assertEquals("", titleView.getText().toString());
    }

    @Test
    public void testGenerateCategoryWithChipGroupWithMultipleChips() {
        ViewGroup viewGroup = util.generateCategoryWithChipGroup(layoutInflater, "Test");

        ViewGroup internalLayout = viewGroup.findViewById(R.id.registry_filter_category_intern_layout);
        HorizontalScrollView scrollView = (HorizontalScrollView) internalLayout.getChildAt(0);
        ChipGroup chipGroup = (ChipGroup) scrollView.getChildAt(0);

        for (int i = 0; i < 5; i++) {
            Chip chip = util.generateChip("Chip " + i);
            chipGroup.addView(chip);
        }

        assertEquals(5, chipGroup.getChildCount());
    }

    // Testes para generateRangeSlider

    @Test
    public void testGenerateRangeSliderReturnsNotNull() {
        RangeSlider slider = util.generateRangeSlider(0f, 100f);
        assertNotNull(slider);
    }



    @Test
    public void testGenerateRangeSliderMinValue() {
        RangeSlider slider = util.generateRangeSlider(10f, 100f);
        assertEquals(10f, slider.getValueFrom(), 0.01f);
    }

    @Test
    public void testGenerateRangeSliderMaxValue() {
        RangeSlider slider = util.generateRangeSlider(10f, 100f);
        assertEquals(100f, slider.getValueTo(), 0.01f);
    }

    @Test
    public void testGenerateRangeSliderWithDifferentRanges() {
        RangeSlider slider1 = util.generateRangeSlider(0f, 50f);
        RangeSlider slider2 = util.generateRangeSlider(20f, 80f);
        RangeSlider slider3 = util.generateRangeSlider(-10f, 10f);

        assertEquals(0f, slider1.getValueFrom(), 0.01f);
        assertEquals(50f, slider1.getValueTo(), 0.01f);

        assertEquals(20f, slider2.getValueFrom(), 0.01f);
        assertEquals(80f, slider2.getValueTo(), 0.01f);

        assertEquals(-10f, slider3.getValueFrom(), 0.01f);
        assertEquals(10f, slider3.getValueTo(), 0.01f);
    }

    @Test
    public void testGenerateRangeSliderHasCorrectId() {
        RangeSlider slider = util.generateRangeSlider(0f, 100f);
        assertEquals(R.id.filter_category_rangeslider, slider.getId());
    }

    @Test
    public void testGenerateRangeSliderTickNotVisible() {
        RangeSlider slider = util.generateRangeSlider(0f, 100f);
        assertNotNull(slider);
        // Check if the tick is invisible
        assertFalse(slider.isTickVisible());
    }

    @Test
    public void testGenerateRangeSliderWithZeroRange() {
        RangeSlider slider = util.generateRangeSlider(50f, 50f);
        assertEquals(50f, slider.getValueFrom(), 0.01f);
        assertEquals(50f, slider.getValueTo(), 0.01f);
    }

    @Test
    public void testGenerateRangeSliderWithNegativeValues() {
        RangeSlider slider = util.generateRangeSlider(-100f, -10f);
        assertEquals(-100f, slider.getValueFrom(), 0.01f);
        assertEquals(-10f, slider.getValueTo(), 0.01f);
    }

    @Test
    public void testGenerateRangeSliderCanSetValues() {
        RangeSlider slider = util.generateRangeSlider(0f, 100f);
        slider.setValues(25f, 75f);

        assertEquals(25f, slider.getValues().get(0), 0.01f);
        assertEquals(75f, slider.getValues().get(1), 0.01f);
    }

    @Test
    public void testGenerateMultipleRangeSlidersWithDifferentRanges() {
        RangeSlider slider1 = util.generateRangeSlider(0f, 10f);
        RangeSlider slider2 = util.generateRangeSlider(0f, 100f);
        RangeSlider slider3 = util.generateRangeSlider(0f, 1000f);

        assertNotNull(slider1);
        assertNotNull(slider2);
        assertNotNull(slider3);

        assertEquals(10f, slider1.getValueTo(), 0.01f);
        assertEquals(100f, slider2.getValueTo(), 0.01f);
        assertEquals(1000f, slider3.getValueTo(), 0.01f);
    }

}

