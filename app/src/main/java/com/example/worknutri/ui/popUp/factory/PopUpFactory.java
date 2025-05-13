package com.example.worknutri.ui.popUp.factory;

import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

public interface PopUpFactory {

    PatologiaDetaillPopUp generatePatologiaDetailPopUp( Patologia patologia);

    AntroPometriaDetaillPopUp generateSmallAntropometriaPopUp( Antropometria antropometria);

    AntroPometriaDetaillPopUp generateFullAntropometriaPopUp( Antropometria antropometria);

    RemoveConfirmPopUp generateRemoveConfirmPopUp();
    DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity);


}
