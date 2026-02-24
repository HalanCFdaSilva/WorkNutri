package com.example.worknutri.ui.forms.clinicForm;

import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.editTextKeysListener.CepKeyListener;
import com.example.worknutri.ui.editTextKeysListener.PhoneKeyListener;
import com.example.worknutri.ui.forms.FormularioAdapter;
import com.example.worknutri.ui.forms.FormValidator;
import com.example.worknutri.ui.forms.formInserters.ClinicFormInserter;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;

import java.util.List;

public class ClinicFormAdapter extends FormularioAdapter {


    private final ClinicFormInserter insertion;
    private Clinica clinica;
    private final DayOfWorkUiService dayOfWorkUiService;
    private final ViewGroup viewRootActivity;

    public ClinicFormAdapter(ViewGroup viewGroupRoot) {
        super(viewGroupRoot.getContext());
        dayOfWorkUiService = new DayOfWorkUiService(viewGroupRoot,
                viewGroupRoot.findViewById(R.id.clinic_form_horario_atendimento_layout));
        insertion = ClinicFormInserter.create(viewGroupRoot);
        this.clinica = new Clinica();
        viewRootActivity = viewGroupRoot;

    }

    public void insertClinicInlayout(Clinica clinica) {
        this.clinica = clinica;
        insertion.insertEntityInViewGroup(clinica);
        DayOfWorkDao dayOfWorkDao = getDataBase().dayOfWorkDao();
        dayOfWorkUiService.insertAllDayOfWork(dayOfWorkDao, clinica.getId());


    }


    public boolean validateForm(TextView textViewError) {
        if (validaPreenchimentoObrigatorio(textViewError)) {
            if (FormValidator.validatePhoneNumber(viewRootActivity.findViewById(R.id.clinic_form_general_data_phone),
                    textViewError)) {
                return FormValidator.validateEmailAdress(viewRootActivity.findViewById(R.id.clinic_form_general_data_email),
                        textViewError);
            }
        }
        return false;
    }

    private boolean validaPreenchimentoObrigatorio(TextView textViewError) {
        boolean formularioPreenchido = !FormValidator.editTextIsEmpty(viewRootActivity.findViewById(R.id.clinic_form_general_data_name),
                viewRootActivity.findViewById(R.id.clinic_form_general_data_name_mandatory), textViewError);
        if (FormValidator.editTextIsEmpty(viewRootActivity.findViewById(R.id.clinic_form_address_street),
                viewRootActivity.findViewById(R.id.clinic_form_address_street_mandatory), textViewError)) {
            formularioPreenchido = false;

        }
        if (FormValidator.editTextIsEmpty(viewRootActivity.findViewById(R.id.clinic_form_address_number),
                viewRootActivity.findViewById(R.id.clinic_form_address_number_mandatory), textViewError)) {
            formularioPreenchido = false;
        }
        if (FormValidator.editTextIsEmpty(viewRootActivity.findViewById(R.id.clinic_form_address_neighborhood),
                viewRootActivity.findViewById(R.id.clinic_form_address_neighborhood_mandatory), textViewError)) {
            formularioPreenchido = false;
        }

        if (FormValidator.editTextIsEmpty(viewRootActivity.findViewById(R.id.clinic_form_address_city),
                viewRootActivity.findViewById(R.id.clinic_form_address_city_mandatory), textViewError)) {
            formularioPreenchido = false;
        }
        return formularioPreenchido;
    }


    public void saveInDataBase() {
        List<DayOfWork> dayOfWorks = dayOfWorkUiService.getAllDayOfWork();
        if (clinica.getId() == 0) {
            insertion.insertViewGroupInEntity( clinica);
            getDataBase().clinicaDao().insertAll(clinica);
            int id = getDataBase().clinicaDao().findIdByName(clinica.getNome());
            saveDayofWork(dayOfWorks, id);
        } else {
            insertion.insertViewGroupInEntity(clinica);
            getDataBase().clinicaDao().update(clinica);
            saveDayofWork(dayOfWorks, clinica.getId());
        }
    }

    private void saveDayofWork(List<DayOfWork> dayOfWorkList, long clinicaId) {
        for (DayOfWork dayOfWork : dayOfWorkList) {

            if (dayOfWork.getId() != 0) {
                getDataBase().dayOfWorkDao().update(dayOfWork);
            } else {
                dayOfWork.setIdClinica(clinicaId);
                getDataBase().dayOfWorkDao().insert(dayOfWork);
            }

        }
    }

    public void configureKeyListeners() {
        EditText viewById = viewRootActivity.findViewById(R.id.clinic_form_general_data_phone);
        viewById.setOnKeyListener(new PhoneKeyListener());

        viewById = viewRootActivity.findViewById(R.id.clinic_form_address_zip_postal);
        viewById.setOnKeyListener(new CepKeyListener());
    }

    public DayOfWorkUiService getDayOfWorkUiSave() {
        return dayOfWorkUiService;
    }
}
