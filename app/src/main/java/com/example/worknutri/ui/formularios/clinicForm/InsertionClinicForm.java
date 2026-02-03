package com.example.worknutri.ui.formularios.clinicForm;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.util.ViewsUtil;


public class InsertionClinicForm {

    private final Context context;

    public InsertionClinicForm(Context context) {
        this.context = context;
    }

    public void InsertInClinica(ViewGroup viewGroup, Clinica clinica) {

        if (isViewGroupWithCorrectlyLayout(viewGroup)){
            String stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_name));
            clinica.setNome(stringOfEditText);

            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_email));
            clinica.setEmail(stringOfEditText);

            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_phone));
            clinica.setTelefone1(stringOfEditText);

            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_street));
            clinica.setRua(stringOfEditText);
            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_number));
            clinica.setNumero(Integer.parseInt(stringOfEditText));
            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_complement));
            clinica.setComplemento(stringOfEditText);
            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_neighborhood));
            clinica.setBairro(stringOfEditText);
            stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_city));
            clinica.setCidade(stringOfEditText);

            String stringOfSpinner = ViewsUtil.getStringOfSpinner(viewGroup.findViewById(R.id.clinic_form_address_state_spinner),
                    context.getResources().getStringArray(R.array.estado_brasil));
            clinica.setEstado(stringOfSpinner);
        }
    }

    public void InsertInFormulario(ViewGroup viewGroup, Clinica clinica) {
        if (isViewGroupWithCorrectlyLayout(viewGroup)){
            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_name), clinica.getNome());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_email), clinica.getEmail());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_phone), clinica.getTelefone1());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_zip_postal), clinica.getCodigoPostal());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_street), clinica.getRua());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_number), String.valueOf(clinica.getNumero()));

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_complement), clinica.getComplemento());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_neighborhood), clinica.getBairro());

            ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_city), clinica.getCidade());

            if (!clinica.getEstado().isBlank()) {
                Spinner spinner = viewGroup.findViewById(R.id.clinic_form_address_state_spinner);

                String[] stringArray = context.getResources().getStringArray(R.array.estado_brasil);
                int i = 0;
                for (String estado : stringArray) {
                    if (estado.equals(clinica.getEstado())) {
                        break;
                    }
                    i++;
                }
                spinner.setSelection(i);
            }
        }
    }

 private boolean isViewGroupWithCorrectlyLayout(ViewGroup viewGroup){
        return viewGroup.getId() == R.id.clinic_form;
 }


}
