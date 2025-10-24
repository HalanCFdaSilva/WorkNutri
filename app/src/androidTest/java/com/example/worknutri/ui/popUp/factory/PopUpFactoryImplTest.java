package com.example.worknutri.ui.popUp.factory;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.anthropometry.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.anthropometry.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.pathology.addPopUp.PathologyAddPopUp;
import com.example.worknutri.ui.popUp.pathology.viewPopUp.PathologyViewPopUp;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;

@RunWith(AndroidJUnit4.class)
public class PopUpFactoryImplTest {

    private Context context;
    private PopUpFactoryImpl popUpFactory;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        popUpFactory = new PopUpFactoryImpl(context);

    }

    @Test
    public void testGeneratePatologiaDetailPopUp() {
        PathologyViewPopUp datePickerPopUp = popUpFactory.generatePatologiaDetailPopUp();
        Assert.assertNotNull(datePickerPopUp);
    }

    @Test
    public void testGenerateAntropometriaDetailPopUp() {
        AntropometriaDetaillPopUp datePickerPopUp = popUpFactory.generateAntropometriaPopUp();
        Assert.assertNotNull(datePickerPopUp);
    }

    @Test
    public void testGenerateRemoveConfirmPopUp() {
        PopUpFragment removeConfirmPopUp = popUpFactory.generateRemoveConfirmPopUp();
        Assert.assertNotNull(removeConfirmPopUp);
    }

    @Test
    public void testGenerateDatePickerPopUp() {

        DatePickerPopUp datePickerPopUp = popUpFactory.generateDatePickerPopUp();
        Assert.assertNotNull(datePickerPopUp );
    }

    @Test
    public void testGeneratePopUpPatologiaAddWereReceiveAEmptyListOnParameter() {
        PathologyAddPopUp popUp = popUpFactory.generatePopUpPatologiaAdd(new ArrayList<>());
        Assert.assertNotNull(popUp);
    }

    @Test
    public void testGeneratePopUpPatologiaAddWereReceiveInParameterOneListWithAllPathologyTypes() {
        PathologyAddPopUp popUp = popUpFactory.generatePopUpPatologiaAdd(Arrays.asList(PathologyField.values()));
        Assert.assertNotNull(popUp);
    }

    @Test
    public void testGenerateActivityLevelDetail() {
        ActivityLevelDetailPopUp activityLevelPopUp = popUpFactory.generateActivityLevelDetailPopUp();
        Assert.assertNotNull(activityLevelPopUp);

    }
}
