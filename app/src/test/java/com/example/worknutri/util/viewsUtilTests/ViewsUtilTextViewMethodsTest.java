package com.example.worknutri.util.viewsUtilTests;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.widget.TextView;

import com.example.worknutri.util.ViewsUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class ViewsUtilTextViewMethodsTest {


    @Mock
    TextView textView;


    @Before
    public void setup() {
        textView = mock(TextView.class);
    }

    @Test
    public void verifyIfInsertInTextViewOrTextViewGoneMethodInsertTheStringInTextView() {

        String stringToInsert = "Test String";
        ViewsUtil.insertInTextViewOrTextViewGone(textView,stringToInsert);
        verify(textView).setText(stringToInsert);
    }

    @Test
    public void verifyIfInsertInTextViewOrTextViewGoneMethodSetTextViewGoneWhenStringIsBlank() {

        String stringToInsert = "   ";
        ViewsUtil.insertInTextViewOrTextViewGone(textView,stringToInsert);
        verify(textView).setVisibility(TextView.GONE);
    }

    @Test
    public void verifyIfInsertInTextViewOrTextViewGoneMethodSetTextViewGoneWhenStringIsNull() {

        ViewsUtil.insertInTextViewOrTextViewGone(textView,null);
        verify(textView).setVisibility(TextView.GONE);
    }

    @Test
    public void verifyIfInsertInTextViewMethodInsertTheStringInTextView() {

        String stringToInsert = "Test String";
        ViewsUtil.insertInTextView(textView,stringToInsert);
        verify(textView).setText(stringToInsert);
    }

    @Test
    public void verifyIfInsertInTextViewMethodInsertTheBlankStringInTextViewIfStringNull() {


        ViewsUtil.insertInTextView(textView,null);
        verify(textView).setText("");
    }


}
