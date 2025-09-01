package com.example.worknutri.ui.popUp.factory;


import android.view.ViewGroup;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyType;
import com.example.worknutri.ui.popUp.formsPopUp.PopUpPathologyAdd;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

import java.util.List;

public interface PopUpFactory {

    PatologiaDetaillPopUp generatePatologiaDetailPopUp();

    AntropometriaDetaillPopUp generateAntropometriaPopUp();

    RemoveConfirmPopUp generateRemoveConfirmPopUp();

    DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity);

    PopUpPathologyAdd generatePopUpPatologiaAdd( List<PathologyType> pathologyCategories);
    ActivityLevelDetailPopUp generateActivityLevelDetailPopUp();



}
