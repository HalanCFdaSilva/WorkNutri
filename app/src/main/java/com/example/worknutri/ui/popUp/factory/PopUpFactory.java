package com.example.worknutri.ui.popUp.factory;


import android.view.ViewGroup;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.anthropometry.ActivityLevelDetailPopUp;
import com.example.worknutri.ui.popUp.anthropometry.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.pathology.viewPopUp.PathologyViewPopUp;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.addPopUp.PathologyAddPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

import java.util.List;

public interface PopUpFactory {

    PathologyViewPopUp generatePatologiaDetailPopUp();

    AntropometriaDetaillPopUp generateAntropometriaPopUp();

    RemoveConfirmPopUp generateRemoveConfirmPopUp();

    DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity);

    PathologyAddPopUp generatePopUpPatologiaAdd(List<PathologyField> pathologyCategories);
    ActivityLevelDetailPopUp generateActivityLevelDetailPopUp();



}
