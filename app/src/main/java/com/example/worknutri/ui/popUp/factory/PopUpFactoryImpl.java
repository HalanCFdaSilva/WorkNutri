package com.example.worknutri.ui.popUp.factory;

import android.content.Context;
import android.view.LayoutInflater;
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

public class PopUpFactoryImpl implements PopUpFactory{

    private final Context context;
    public PopUpFactoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public PatologiaDetaillPopUp generatePatologiaDetailPopUp(Patologia patologia) {
        PatologiaDetaillPopUp popUp = new PatologiaDetaillPopUp(LayoutInflater.from(context));
        popUp.setText(patologia);
        popUp.removeMarginBottom();
        return popUp;
    }

    @Override
    public AntropometriaDetaillPopUp generateSmallAntropometriaPopUp(Antropometria antropometria) {
        AntropometriaDetaillPopUp popUp = new AntropometriaDetaillPopUp(LayoutInflater.from(context),context);
        popUp.generateSmall(antropometria);
        return popUp;
    }

    @Override
    public AntropometriaDetaillPopUp generateFullAntropometriaPopUp(Antropometria antropometria) {
        AntropometriaDetaillPopUp popUp = new AntropometriaDetaillPopUp(LayoutInflater.from(context),context);
        popUp.generateComplete(antropometria);
        return popUp;
    }

    @Override
    public RemoveConfirmPopUp generateRemoveConfirmPopUp() {
        return new RemoveConfirmPopUp(LayoutInflater.from(context));
    }

    @Override
    public DatePickerPopUp generateDatePickerPopUp(ViewGroup viewGroupRootOfActivity) {
        return new DatePickerPopUp(LayoutInflater.from(context),viewGroupRootOfActivity);
    }

    @Override
    public PopUpPathologyAdd generatePopUpPatologiaAdd(List<PathologyType> pathologyCategories) {
        return new PopUpPathologyAdd(context, pathologyCategories);
    }
}
