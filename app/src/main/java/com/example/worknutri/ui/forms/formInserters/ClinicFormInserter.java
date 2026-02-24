package com.example.worknutri.ui.forms.formInserters;

import android.view.ViewGroup;
import android.widget.Spinner;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.util.ViewsUtil;


public class ClinicFormInserter extends FormInserter<Clinica> {



    private ClinicFormInserter(ViewGroup viewGroup) {
        super(viewGroup);
        viewGroupIdExpected = R.id.clinic_form;

    }

    public static ClinicFormInserter create(ViewGroup viewGroup) {
        if (checkViewGroupIsCorrectly(viewGroup))
            return new ClinicFormInserter(viewGroup);
        throw new IllegalArgumentException("O viewGroup deve ser do tipo esperado. Esperado: " + viewGroupIdExpected + ", Recebido: " + viewGroup.getId());

    }



    public void insertViewGroupInEntity( Clinica clinic) {

        String stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_name));
        clinic.setNome(stringOfEditText);

        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_email));
        clinic.setEmail(stringOfEditText);

        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_general_data_phone));
        clinic.setTelefone1(stringOfEditText);

        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_street));
        clinic.setRua(stringOfEditText);
        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_number));
        clinic.setNumero(Integer.parseInt(stringOfEditText));
        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_complement));
        clinic.setComplemento(stringOfEditText);
        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_neighborhood));
        clinic.setBairro(stringOfEditText);
        stringOfEditText = ViewsUtil.getStringOfEditText(viewGroup.findViewById(R.id.clinic_form_address_city));
        clinic.setCidade(stringOfEditText);

        String stringOfSpinner = ViewsUtil.getStringOfSpinner(viewGroup.findViewById(R.id.clinic_form_address_state_spinner),
                viewGroup.getContext().getResources().getStringArray(R.array.estado_brasil));
        clinic.setEstado(stringOfSpinner);
    }

    public void insertEntityInViewGroup(Clinica clinic) {
        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_name), clinic.getNome());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_email), clinic.getEmail());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_general_data_phone), clinic.getTelefone1());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_zip_postal), clinic.getCodigoPostal());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_street), clinic.getRua());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_number), String.valueOf(clinic.getNumero()));

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_complement), clinic.getComplemento());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_neighborhood), clinic.getBairro());

        ViewsUtil.insertInEditText(viewGroup.findViewById(R.id.clinic_form_address_city), clinic.getCidade());

        if (!clinic.getEstado().isBlank()) {
            Spinner spinner = viewGroup.findViewById(R.id.clinic_form_address_state_spinner);

            String[] stringArray = viewGroup.getContext().getResources().getStringArray(R.array.estado_brasil);
            int i = 0;
            for (String estado : stringArray) {
                if (estado.equals(clinic.getEstado())) {
                    break;
                }
                i++;
            }
            spinner.setSelection(i);
        }

    }


    private static boolean checkViewGroupIsCorrectly(ViewGroup viewGroup) {
        return viewGroup != null && viewGroup.getId() == R.id.clinic_form;
    }

}
