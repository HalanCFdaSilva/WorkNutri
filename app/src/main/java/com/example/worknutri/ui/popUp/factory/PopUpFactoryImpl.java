package com.example.worknutri.ui.popUp.factory;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.formsPopUp.PopUpPathologyAdd;
import com.example.worknutri.ui.popUp.hourDatePopUp.datePicker.DatePickerPopUp;

public class PopUpFactoryImpl implements PopUpFactory{
    public final LayoutInflater layoutInflater;

    public PopUpFactoryImpl(LayoutInflater layoutInflater) {
        this.layoutInflater = layoutInflater;
    }

    @Override
    public PatologiaDetaillPopUp generatePatologiaDetailPopUp(Patologia patologia) {
        PatologiaDetaillPopUp popUp = new PatologiaDetaillPopUp(layoutInflater);
        popUp.setText(patologia);
        popUp.removeMarginBottom();
        return popUp;
    }

    @Override
    public AntropometriaDetaillPopUp generateSmallAntropometriaPopUp(Antropometria antropometria) {
        AntropometriaDetaillPopUp popUp = new AntropometriaDetaillPopUp(layoutInflater,layoutInflater.getContext());
        popUp.generateSmall(antropometria);
        return popUp;
    }

    @Override
    public AntropometriaDetaillPopUp generateFullAntropometriaPopUp(Antropometria antropometria) {
        AntropometriaDetaillPopUp popUp = new AntropometriaDetaillPopUp(layoutInflater,layoutInflater.getContext());
        popUp.generateComplete(antropometria);
        return popUp;
    }

    @Override
    public RemoveConfirmPopUp generateRemoveConfirmPopUp() {
        return new RemoveConfirmPopUp(layoutInflater);
    }

    @Override
    public DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity) {
        return new DatePickerPopUp(layoutInflater,viewGroupRootOfActivity);
    }

    @Override
    public PopUpPathologyAdd generatePopUpPatologiaAdd(LayoutInflater layoutInflater) {
        return new PopUpPathologyAdd(layoutInflater);
    }
}
