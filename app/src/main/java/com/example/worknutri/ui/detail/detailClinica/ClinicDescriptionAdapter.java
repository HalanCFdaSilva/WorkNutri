package com.example.worknutri.ui.detail.detailClinica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.ViewGroup;
import com.example.worknutri.R;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.ui.BottomMenuConfigurator;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.util.ViewsUtil;
import com.example.worknutri.ui.forms.clinicForm.ClinicFormActivity;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ClinicDescriptionAdapter {
    private Clinica clinic;
    private List<DayOfWork> dayOfWorkList;
    private AppDataBase dataBase;
    private Context context;

    public ClinicDescriptionAdapter(Intent intent, Context context) {
        if (intent.hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey())) {
            this.clinic = (Clinica) intent.getSerializableExtra(ExtrasActivities.CLINICA_EXTRA.getKey());
            dataBase = AppDataBase.getInstance(context);
            dayOfWorkList = dataBase.dayOfWorkDao().getDaysforClinicaId(clinic.getId());
            this.context = context;
        } else ((Activity) context).finish();


    }

    public void insertClinicInLayout(ViewGroup viewGroup) {
        insertGeneralData(viewGroup);
        InsertAdress(viewGroup);

    }

    private void insertGeneralData(ViewGroup viewGroup) {
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_name), clinic.getNome());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_fone), clinic.getTelefone1());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_email), clinic.getEmail());
    }

    private void InsertAdress(ViewGroup viewGroup) {
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cep), clinic.getCodigoPostal());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_rua), clinic.getRua());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_numero), String.valueOf(clinic.getNumero()));
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_complemento), clinic.getComplemento());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cidade), clinic.getCidade());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_bairro), clinic.getBairro());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_estado), clinic.getEstado());
    }

    public void insertDaysOfWorkInLayout(ViewGroup linearLayout) {
        DayOfWorkUiService dayOfWorkUiService = new DayOfWorkUiService(linearLayout);
        dayOfWorkUiService.insertAllDayOfWorkWithNotRemoveButton(dataBase.dayOfWorkDao(), clinic.getId());

    }

    public void configureNavButton(BottomNavigationView navigationView, ViewGroup layoutRoot) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context, navigationView);

        Intent intent = new Intent(context, ClinicFormActivity.class);
        intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinic);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_edit, intent);


        configureDeleteButton(navigationView,layoutRoot);
    }

    private void configureDeleteButton(BottomNavigationView navigationView, ViewGroup rootLayout) {
        navigationView.getMenu().findItem(R.id.navigation_delete).setOnMenuItemClickListener(item -> {
            RemoveConfirmPopUp removeConfirmPopUp = new RemoveConfirmPopUp(((Activity) context).getLayoutInflater());
            removeConfirmPopUp.getConfirmButton().setOnClickListener(onClickButton -> {
                dataBase.clinicaDao().delete(clinic);
                for (DayOfWork dayOfWork : dayOfWorkList) {
                    dataBase.dayOfWorkDao().delete(dayOfWork);
                }
                ((Activity) context).finish();
            });
            removeConfirmPopUp.getPopUpWindow().showAtLocation(rootLayout, Gravity.CENTER, 0, 0);
            return false;
        });
    }


}
