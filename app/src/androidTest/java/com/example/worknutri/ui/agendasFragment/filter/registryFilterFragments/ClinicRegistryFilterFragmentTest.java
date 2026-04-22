package com.example.worknutri.ui.agendasFragment.filter.registryFilterFragments;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_BUNDLE;
import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.CLINICA_FILTER_POJO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.os.Bundle;
import android.view.ViewGroup;


import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.ViewUtil;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.ClinicFilterCategories.ClinicsSelector;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterUiState;
import com.google.android.material.chip.Chip;
import com.google.android.material.slider.RangeSlider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


@RunWith(AndroidJUnit4.class)
public class ClinicRegistryFilterFragmentTest {

    private ClinicRegistryFilterFragment fragment;
    private ClinicFilterPojo clinicFilterPojo;
    private List<Clinica> clinicsList;
    private ActivityScenario<ActivityToTest> scenario;


    @Before
    public void setUp() {
        
        clinicsList = TestEntityFactory.generateClinicListToTest();
        List<DayOfWork> dayOfWorkList = createTestDaysOfWork();
        
        // Create and configure ClinicFilterPojo
        clinicFilterPojo = new ClinicFilterPojo();
        clinicFilterPojo.setClinicsList(clinicsList);
        clinicFilterPojo.setDayOfWorkList(dayOfWorkList);
        
        // Create fragment with Bundle
        fragment = new ClinicRegistryFilterFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(CLINICA_FILTER_POJO, clinicFilterPojo);
        fragment.setArguments(bundle);
        
        // Setup ActivityScenario for UI tests
        scenario = ActivityScenario.launch(ActivityToTest.class);
        showFragment();
    }

    private void showFragment() {
        scenario.onActivity(activity -> 
                fragment.show(activity.getSupportFragmentManager(), "ClinicaRegistryFilterFragmentTest")
        );
        Espresso.onView(withId(R.id.registry_filter)).check(matches(isDisplayed()));
    }

    private List<DayOfWork> createTestDaysOfWork() {
        List<DayOfWork> days = TestEntityFactory.getDaysOfWork();
        for(int i = 0; i < days.size(); i++) {
            days.get(i).setIdClinica(i);
        }

        return days;
    }
    // ==================== Tests on Initialization ====================

    @Test
    public void testFragmentInitializesCorrectly() {
        assertNotNull(fragment);
    }

    @Test
    public void testGetPojoRetrievesCorrectPojo() {
        Bundle bundle = fragment.getArguments();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(CLINICA_FILTER_POJO));
        ClinicFilterPojo retrievedPojo = (ClinicFilterPojo) bundle.getSerializable(CLINICA_FILTER_POJO);
        Assert.assertEquals(clinicFilterPojo, retrievedPojo);
    }

    @Test
    public void testUiStateRetrievedCorrectly() {

        UiState uiState = fragment.getUiState();
        assertNotNull(uiState);
        assertTrue(uiState instanceof ClinicFilterUiState);
    }

    @Test
    public void testGenerateBundleContainsCorrectKey() {
        Bundle bundle = fragment.generateBundle();
        assertNotNull(bundle);
        assertTrue(bundle.containsKey(CLINICA_FILTER_POJO));
    }

    @Test
    public void testGenerateBundleContainsSerializedPojo() {
        Bundle bundle = fragment.generateBundle();
        ClinicFilterPojo bundledPojo = (ClinicFilterPojo) bundle.getSerializable(CLINICA_FILTER_POJO);
        assertNotNull(bundledPojo);
        Assert.assertEquals(clinicFilterPojo.getClinicsList().size(), bundledPojo.getClinicsList().size());
    }

    @Test
    public void testGetRequestKeyReturnsCorrectKey() {
        String key = fragment.getRequestKey();
        Assert.assertEquals(CLINICA_FILTER_BUNDLE, key);
    }


    @Test
    public void fragmentInflatesCorrectlyLayout() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot); 
        Assert.assertEquals(R.id.registry_filter,viewRoot.getId());
    }


    @Test
    public void testLayoutContainsCorrectlyNumberOfCategories() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        ViewGroup categoryLayouts = viewRoot.findViewById(R.id.registry_filter_layout_categories);
        Assert.assertEquals(2, categoryLayouts.getChildCount());

    }

    // ==================== Tests to HourWorkCategory integration ====================
    @Test
    public void testIfContainHourWorkCategoryInLayout() {
        Espresso.onView(withId(R.id.filter_category_clinic_hour)).check(matches(isDisplayed()));

    }

    @Test
    public void testIfSelectOnerangeInHourWorkCategorySelectOnlyTheClinicsWithDayOfWorkIsWithDayInsideARange() {

        checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());

        Espresso.onView(withId(R.id.filter_category_rangeslider)).perform(
                ViewUtil.setSliderValue(9 * 60f / (24 * 60f)),
                ViewUtil.setSliderValue(16 * 60f / (24 * 60f))
        );

        Espresso.onView(withId(R.id.registry_filter_buton_confirm)).perform(click());

        List<Clinica> clinicsExpected = new ClinicsSelector(clinicsList).byRangeOfHourWork(List.of(9 * 60f, 16 * 60f), clinicFilterPojo.getDayOfWorkList());
        checkClinicsSelectedContainsOnlyClinicsExpected(clinicsExpected);
    }

    @Test
    public void testIfSelectOneRangeInHourWorkCategorySetValuesOnPojoCorrectly() {

        checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());


        Espresso.onView(withId(R.id.filter_category_rangeslider)).perform(
                ViewUtil.setSliderValue(9 * 60f / (24 * 60f)),
                ViewUtil.setSliderValue(16 * 60f / (24 * 60f))
        );

        RangeSlider rangeSlider = Objects.requireNonNull(fragment.getView()).findViewById(R.id.filter_category_rangeslider);
        Espresso.onView(withId(R.id.registry_filter_buton_confirm)).perform(click());

        List<Float> valuesSelectedsOnRange = rangeSlider.getValues();
        checkRangeIsCorrect(valuesSelectedsOnRange.get(0), valuesSelectedsOnRange.get(1));
    }

    private void checkRangeIsCorrect(float startOfRangeExpected, float endOfRangeExpected) {
        float[] hoursSelected = clinicFilterPojo.getUiState().getHoursSelected();
        Assert.assertEquals(startOfRangeExpected,hoursSelected[0],0.001);
        assertEquals(endOfRangeExpected,hoursSelected[1],0.001);

    }
    

    // ==================== Tests to DayInClinicCategory integration ====================

    @Test
    public void testIfContainDayInClinicCategoryInLayout() {
        Espresso.onView(withId(R.id.filter_category_clinic_day)).check(matches(isDisplayed()));

    }

    @Test
    public void testIfSelectOneChipFromClinicOfDaysSelectOnlyAllClinicsWithDayOfWorkIsWithDay() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        // Simulate selecting a day of the week
        scenario.onActivity(activity -> {
            checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());
            Chip chip = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),0);
            chip.performClick();
            viewRoot.findViewById(R.id.registry_filter_buton_confirm).performClick();

            List<Clinica> clinicsExpected = new ClinicsSelector(clinicsList).byDayOfWeek(chip.getText().toString(), clinicFilterPojo.getDayOfWorkList());
            checkClinicsSelectedContainsOnlyClinicsExpected(clinicsExpected);
        });
    }

    //
    @Test
    public void testIfSelectOneChipFromClinicOfDaysWereNoHasDayOfWorkWithThisDayCauseclinicsSelectedIsEmpty() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        // Simulate selecting a day of the week
        scenario.onActivity(activity -> {
            clinicFilterPojo.setClinicsSelected(new ArrayList<>(clinicsList));
            checkClinicsSelectedContainsOnlyClinicsExpected(clinicsList);
            Chip chip = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),4);
            chip.performClick();
            viewRoot.findViewById(R.id.registry_filter_buton_confirm).performClick();

            checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());
        });
    }

    @Test
    public void testIfSelectMultipleChipFromClinicOfDaysSelectOnlyAllClinicsWithDayOfWorkIsWithDay() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        // Simulate selecting a day of the week
        scenario.onActivity(activity -> {
            checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());

            Chip chip1 = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),0);
            chip1.performClick();
            Chip chip2 = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),1);
            chip2.performClick();

            viewRoot.findViewById(R.id.registry_filter_buton_confirm).performClick();

            List<Clinica> clinicsExpectedofChip1 = new ClinicsSelector(clinicsList).byDayOfWeek(chip1.getText().toString(), clinicFilterPojo.getDayOfWorkList());
            List<Clinica> clinicsExpectedofChip2 = new ClinicsSelector(clinicsList).byDayOfWeek(chip2.getText().toString(), clinicFilterPojo.getDayOfWorkList());
            List<Clinica> clinicsExpected = new ArrayList<>(clinicsExpectedofChip1);
            clinicsExpected.addAll(clinicsExpectedofChip2);

            checkClinicsSelectedContainsOnlyClinicsExpected(clinicsExpected);
        });
    }




    // ==================== Tests to resetAllCategories ====================

    @Test
    public void testResetAllCategoriesClearsSelection() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        // Simulate selecting a day of the week
        scenario.onActivity(activity -> {
            assertSizeOfDayOfWeekSelectedIsCorrectly(0);
            Chip chip = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),1);
            chip.performClick();
            viewRoot.findViewById(R.id.registry_filter_buton_confirm).performClick();

        });

        // Now click the reset button and verify that the selection is cleared
        showFragment();
        scenario.onActivity(activity -> {
            assertSizeOfDayOfWeekSelectedIsCorrectly(2);
            viewRoot.findViewById(R.id.registry_filter_button_reset).performClick();
            assertSizeOfDayOfWeekSelectedIsCorrectly(0);
        });
    }
    private void assertSizeOfDayOfWeekSelectedIsCorrectly(int sizeExpected) {
        List<String> daysOfWeekSelected = clinicFilterPojo.getUiState().getDaysOfWeekSelected();
        Assert.assertEquals(sizeExpected,daysOfWeekSelected.size());

    }

    @Test
    public void testResetAllCategoriesClearsSelectionOfHour() {

        checkClinicsSelectedContainsOnlyClinicsExpected(Collections.emptyList());


        Espresso.onView(withId(R.id.filter_category_rangeslider)).perform(
                ViewUtil.setSliderValue(9 * 60f / (24 * 60f)),
                ViewUtil.setSliderValue(16 * 60f / (24 * 60f))
        );

        RangeSlider rangeSlider = Objects.requireNonNull(fragment.getView()).findViewById(R.id.filter_category_rangeslider);
        Espresso.onView(withId(R.id.registry_filter_buton_confirm)).perform(click());

        List<Float> valuesSelectedsOnRange = rangeSlider.getValues();
        checkRangeIsCorrect(valuesSelectedsOnRange.get(0), valuesSelectedsOnRange.get(1));

        showFragment();
        Espresso.onView(withId(R.id.registry_filter_button_reset)).perform(click());

        checkRangeIsCorrect(0, 0);
    }

    @Test
    public void testResetAllCategoriesSelectAllClinicToClinicsSelected() {
        ViewGroup viewRoot = (ViewGroup) fragment.getView();
        Assert.assertNotNull(viewRoot);
        // Simulate selecting a day of the week
        scenario.onActivity(activity -> {
            Assert.assertEquals(0,clinicFilterPojo.getClinicsSelected().size());
            Chip chip = ViewUtil.getChipFromCategory(viewRoot.findViewById(R.id.filter_category_clinic_day),0);
            chip.performClick();
            viewRoot.findViewById(R.id.registry_filter_buton_confirm).performClick();

            List<Clinica> clinicsExpected = new ClinicsSelector(clinicsList).
                    byDayOfWeek(chip.getText().toString(), clinicFilterPojo.getDayOfWorkList());
            Assert.assertEquals(clinicsExpected.size(),clinicFilterPojo.getClinicsSelected().size());
        });

        // Now click the reset button and verify that the selection is cleared
        showFragment();
        scenario.onActivity(activity -> {
            viewRoot.findViewById(R.id.registry_filter_button_reset).performClick();
            Assert.assertEquals(clinicsList.size(),clinicFilterPojo.getClinicsSelected().size());
        });
    }

    private void checkClinicsSelectedContainsOnlyClinicsExpected(List<Clinica> ListOfClinicsExpected) {
        List<Clinica> clinicsSelected = clinicFilterPojo.getClinicsSelected();
        Assert.assertEquals(ListOfClinicsExpected.size(), clinicsSelected.size());
        for (Clinica clinica : ListOfClinicsExpected) {
            assertTrue(clinicsSelected.contains(clinica));
        }

    }

   

}

