package com.example.worknutri.ui.popUp.factory;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;


import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.detailsPopUp.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyType;
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
        PatologiaDetaillPopUp datePickerPopUp = popUpFactory.generatePatologiaDetailPopUp();
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
        ViewGroup viewGroup = new LinearLayout(context);
        DatePickerPopUp datePickerPopUp = popUpFactory.generateDatePickerPopUp(viewGroup);
        Assert.assertNotNull(datePickerPopUp );
    }

    @Test
    public void testGeneratePopUpPatologiaAddWereReceiveAEmptyListOnParameter() {
        PopUpFragment popUpPathologyAdd = popUpFactory.generatePopUpPatologiaAdd(new ArrayList<>());
        Assert.assertNotNull(popUpPathologyAdd);
    }

    @Test
    public void testGeneratePopUpPatologiaAddWereReceiveInParameterOneListWithAllPathologyTypes() {
        PopUpFragment popUpPathologyAdd = popUpFactory.generatePopUpPatologiaAdd(Arrays.asList(PathologyType.values()));
        Assert.assertNotNull(popUpPathologyAdd);
    }
}
