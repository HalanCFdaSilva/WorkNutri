package com.example.worknutri.ui.formularios.formularioPaciente.insertionsOfPacienteFormulario;

import android.view.ViewGroup;
import android.widget.Spinner;

public abstract class InsertionPacienteForm {

    protected final ViewGroup viewGroup;

    public InsertionPacienteForm(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
    }

    protected static int getPositionOfSpinner(Spinner spinner) {
        return spinner.getSelectedItemPosition();
    }
}
