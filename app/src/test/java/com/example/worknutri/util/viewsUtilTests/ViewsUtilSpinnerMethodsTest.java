package com.example.worknutri.util.viewsUtilTests;

import android.widget.Spinner;

import com.example.worknutri.util.ViewsUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ViewsUtilSpinnerMethodsTest {

    @Mock
    Spinner spinner;
    @Before
    public void setup() {
        spinner = Mockito.mock(Spinner.class);
    }
    @Test
    public void verifyIfGetStringOfSpinnerMethodReturnTheCorrectStringFromArray() {
        String[] arrayOfStringSpinner = {"Option 1", "Option 2", "Option 3"};
        int selectedPosition = 1;
        mockReturnPositionOfItem(selectedPosition);

        String expectedString = arrayOfStringSpinner[selectedPosition];
        String actualString = ViewsUtil.getStringOfSpinner(spinner, arrayOfStringSpinner);

        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void verifyIfGetStringOfSpinnerMethodReturnTheFirstStringWhenPositionIsZero() {
        String[] arrayOfStringSpinner = {"Option 1", "Option 2", "Option 3"};
        int selectedPosition = 0;
        mockReturnPositionOfItem(selectedPosition);

        String expectedString = arrayOfStringSpinner[selectedPosition];
        String actualString = ViewsUtil.getStringOfSpinner(spinner, arrayOfStringSpinner);

        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void verifyIfGetStringOfSpinnerMethodReturnTheLastStringWhenPositionIsLastIndex() {
        String[] arrayOfStringSpinner = {"Option 1", "Option 2", "Option 3"};
        int selectedPosition = arrayOfStringSpinner.length - 1;
        mockReturnPositionOfItem(selectedPosition);

        String expectedString = arrayOfStringSpinner[selectedPosition];
        String actualString = ViewsUtil.getStringOfSpinner(spinner, arrayOfStringSpinner);

        Assert.assertEquals(expectedString, actualString);
    }
    private void mockReturnPositionOfItem(int selectedPosition) {
        Mockito.when(spinner.getSelectedItemPosition()).thenReturn(selectedPosition);
    }
}
