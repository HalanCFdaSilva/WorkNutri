package com.example.worknutri.ui.forms;

import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.R;
import com.example.worknutri.support.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class FormValidatorTest {


    private Context context;

    @Before
    public void setUp() {
        context = TestUtil.getThemedContext();

    }

    @Test
    public void editTextIsEmptyNotModifyTheErrorStringWhenEditTextIsNotEmpty() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        TextView asterisk = new TextView(context);
        et.setText("Is not Empty");
        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.VISIBLE);
        checkIfNotModifyTextViewError(error, View.VISIBLE,
                FormValidator.editTextIsEmpty(et, asterisk, error),false);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.INVISIBLE);
        checkIfNotModifyTextViewError(error, View.INVISIBLE,
                FormValidator.editTextIsEmpty(et, asterisk, error),false);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.GONE);
        checkIfNotModifyTextViewError(error, View.GONE,
                FormValidator.editTextIsEmpty(et, asterisk, error),false);

    }



    @Test
    public void editTextIsEmptyWhenBlankReturnsFalseAndHidesAsterisk() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        error.setVisibility(View.INVISIBLE);
        TextView asterisk = new TextView(context);


        et.setText("Some value");
        resetErrorState(error);
        boolean result = FormValidator.editTextIsEmpty(et, asterisk, error);

        assertFalse("Expected no error for non-blank text", result);
        assertEquals(View.INVISIBLE, asterisk.getVisibility());
        assertNotEquals(View.VISIBLE, error.getVisibility());
    }

    @Test
    public void editTextIsEmptyWhenBlankShowsErrorAndAsterisk() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        TextView asterisk = new TextView(context);

        resetErrorState(error);
        et.setText("   ");
        boolean result = FormValidator.editTextIsEmpty(et, asterisk, error);

        assertTrue("Expected error for blank text", result);
        assertEquals(View.VISIBLE, asterisk.getVisibility());
        assertEquals(View.VISIBLE, error.getVisibility());
        assertEquals(context.getString(R.string.error_blank), error.getText().toString());
    }


    @Test
    public void validatePhoneNumberNotModifyTheTextViewVisibilityWhenEditTextIsNotEmpty() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        et.setText("(11) 91234-5678");
        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.VISIBLE);
        checkIfNotModifyTextViewError(error, View.VISIBLE,
                FormValidator.validatePhoneNumber(et, error),true);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.INVISIBLE);
        checkIfNotModifyTextViewError(error, View.INVISIBLE,
                FormValidator.validatePhoneNumber(et, error),true);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.GONE);
        checkIfNotModifyTextViewError(error, View.GONE,
                FormValidator.validatePhoneNumber(et, error),true);

    }

    @Test
    public void validatePhoneNumberReturnFalseIfInvalidAndModifyTextViewToShowError() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        resetErrorState(error);
        et.setText("123");
        assertFalse(FormValidator.validatePhoneNumber(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_fone);

        resetErrorState(error);
        et.setText("1234567890123");
        assertFalse(FormValidator.validatePhoneNumber(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_fone);

        resetErrorState(error);
        et.setText("1234567");
        assertFalse(FormValidator.validatePhoneNumber(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_fone);
    }

    @Test
    public void validatePhoneNumberReturnTrueIfValidateEditText() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        resetErrorState(error);

        et.setText("(11) 91234-5678");
        assertTrue(FormValidator.validatePhoneNumber(et, error));
        assertNotEquals(View.VISIBLE, error.getVisibility());
    }



    @Test
    public void validateEmailAddressNotModifyTheTextViewVisibilityWhenEditTextIsNotEmpty() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        et.setText("user@example.com");
        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.VISIBLE);
        checkIfNotModifyTextViewError(error, View.VISIBLE,
                FormValidator.validateEmailAdress(et, error),true);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.INVISIBLE);
        checkIfNotModifyTextViewError(error, View.INVISIBLE,
                FormValidator.validateEmailAdress(et, error),true);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.GONE);
        checkIfNotModifyTextViewError(error, View.GONE,
                FormValidator.validateEmailAdress(et, error),true);

    }

    @Test
    public void validateEmailAddressReturnTrueIfValidateEditText() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        resetErrorState(error);

        et.setText("user@example.com");
        assertTrue(FormValidator.validateEmailAdress(et, error));
        assertNotEquals(View.VISIBLE, error.getVisibility());
    }

    @Test
    public void validateEmailAddressReturnFalseIfInvalidAndModifyTextViewToShowError() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        resetErrorState(error);
        et.setText("not-an-email");
        assertFalse(FormValidator.validateEmailAdress(et, error));
        checkIfTextViewOfErrorIsCorrectly(error, R.string.error_email);

        resetErrorState(error);
        et.setText("email@domain");
        assertFalse(FormValidator.validateEmailAdress(et, error));
        checkIfTextViewOfErrorIsCorrectly(error, R.string.error_email);

        resetErrorState(error);
        et.setText("email@domain.c");
        assertFalse(FormValidator.validateEmailAdress(et, error));
        checkIfTextViewOfErrorIsCorrectly(error, R.string.error_email);
    }



    @Test
    public void validateDateNotModifyTheTextViewVisibilityWhenEditTextIsNotEmpty() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        et.setText("31/12/2000");
        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.VISIBLE);
        checkIfNotModifyTextViewError(error, View.VISIBLE,
                FormValidator.validateDate(et, error),true);

        resetErrorState(error,View.INVISIBLE);
        assertFalse(et.getText().toString().isBlank());
        checkIfNotModifyTextViewError(error, View.INVISIBLE,
                FormValidator.validateDate(et, error),true);

        assertFalse(et.getText().toString().isBlank());
        resetErrorState(error,View.GONE);
        checkIfNotModifyTextViewError(error, View.GONE,
                FormValidator.validateDate(et, error),true);

    }

    @Test
    public void validateDateReturnTrueIfValidateEditText() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);
        resetErrorState(error);

        et.setText("31/12/2000");
        assertTrue(FormValidator.validateDate(et, error));
        assertNotEquals(View.VISIBLE, error.getVisibility());
    }

    @Test
    public void validateDateReturnFalseIfInvalidAndModifyTextViewToShowError() {
        EditText et = new EditText(context);
        TextView error = new TextView(context);

        resetErrorState(error);
        et.setText("31/12/2999");
        assertFalse(FormValidator.validateDate(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_data);

        resetErrorState(error);
        et.setText("01/01/1800");
        assertFalse(FormValidator.validateDate(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_data);

        resetErrorState(error);
        et.setText("31-12-2000");
        assertFalse(FormValidator.validateDate(et, error));
        checkIfTextViewOfErrorIsCorrectly( error, R.string.error_data);
    }



    private static void checkIfNotModifyTextViewError(TextView error, int visibilityToTextView,
                                                      boolean resultValidator, boolean resultExpected) {
        assertEquals("Expected no error for non-blank text",resultExpected, resultValidator);
        assertEquals(visibilityToTextView, error.getVisibility());
    }

    private void resetErrorState(TextView error,int visibilityToInsert) {
        error.setVisibility(visibilityToInsert);
        error.setText("");
    }

    private void checkIfTextViewOfErrorIsCorrectly( TextView error, int idStringError) {
        assertEquals(View.VISIBLE, error.getVisibility());
        assertEquals(context.getString(idStringError), error.getText().toString());
    }

    private void resetErrorState(TextView error) {
        error.setVisibility(View.INVISIBLE);
        error.setText("");
    }

}
