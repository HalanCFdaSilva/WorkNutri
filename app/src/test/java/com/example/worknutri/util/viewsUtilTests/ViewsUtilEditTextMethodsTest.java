package com.example.worknutri.util.viewsUtilTests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.text.Editable;
import android.widget.EditText;


import com.example.worknutri.util.ViewsUtil;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ViewsUtilEditTextMethodsTest {


    @Mock
    EditText editText;


    @Before
    public void setup() {
        editText = mock(EditText.class);
    }


    @Test
    public void verifyIfInsertInEditTextMethodInsertTheStringInEditTextIfStringNotNull() {

        String stringToInsert = "Test String";
        ViewsUtil.insertInEditText(editText,stringToInsert);
        verify(editText).setText(stringToInsert);
    }

    @Test
    public void verifyIfInsertInEditTextMethodDoesNotInsertTheStringInEditTextIfStringIsNull() {

        ViewsUtil.insertInEditText(editText,null);
        verify(editText,never()).setText(null);
    }

    @Test
    public void verifyIfGetStringOfEditTextMethodReturnTheStringOfEditTextWhenItIsNotEmpty() {
        String expectedString = "Test String";
        makeMockReturnAString(expectedString);

        String actualString = ViewsUtil.getStringOfEditText(editText);
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void verifyIfGetStringOfEditTextMethodReturnEmptyStringWhenEditTextHasBlankString() {

        makeMockReturnAString("   ");
        String expectedString = "";
        String actualString = ViewsUtil.getStringOfEditText(editText);
        Assert.assertEquals(expectedString, actualString);
    }

    @Test
    public void verifyIfGetStringOfEditTextMethodReturnEmptyStringWhenEditTextHasEmptyString() {

        makeMockReturnAString("");
        String expectedString = "";
        String actualString = ViewsUtil.getStringOfEditText(editText);
        Assert.assertEquals(expectedString, actualString);
    }


    private void makeMockReturnAString(String expectedString) {
        Editable text = mock(Editable.class);
        when(text.toString()).thenReturn(expectedString);
        when(editText.getText()).thenReturn(text);
    }

}
