package com.example.worknutri.ui.popUp.factory;


import android.view.ViewGroup;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.formsPopUp.PathologyType;
import com.example.worknutri.ui.popUp.formsPopUp.PopUpPathologyAdd;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

import java.util.List;

public interface PopUpFactory {

    PatologiaDetaillPopUp generatePatologiaDetailPopUp( Patologia patologia);

    AntropometriaDetaillPopUp generateSmallAntropometriaPopUp(Antropometria antropometria);

    AntropometriaDetaillPopUp generateFullAntropometriaPopUp(Antropometria antropometria);

    RemoveConfirmPopUp generateRemoveConfirmPopUp();
    DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity);

    PopUpPathologyAdd generatePopUpPatologiaAdd( List<PathologyType> pathologyCategories);



}
