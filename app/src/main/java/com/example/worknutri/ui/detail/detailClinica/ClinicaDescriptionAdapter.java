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
import com.example.worknutri.ui.formularios.formularioClinica.FormularioClinicaActivity;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.hourDatePopUp.DayOfWorkUiService;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ClinicaDescriptionAdapter {
    private Clinica clinica;
    private List<DayOfWork> dayOfWorkList;
    private AppDataBase dataBase;
    private Context context;

    public ClinicaDescriptionAdapter(Intent intent, Context context) {
        if (intent.hasExtra(ExtrasActivities.CLINICA_EXTRA.getKey())) {
            this.clinica = (Clinica) intent.getSerializableExtra(ExtrasActivities.CLINICA_EXTRA.getKey());
            dataBase = AppDataBase.getInstance(context);
            dayOfWorkList = dataBase.dayOfWorkDao().getDaysforClinicaId(clinica.getId());
            this.context = context;
        } else ((Activity) context).finish();


    }

    public void insertClinicaInLayout(ViewGroup viewGroup) {
        insertDadosGerais(viewGroup);
        InsertEndereco(viewGroup);

    }

    private void insertDadosGerais(ViewGroup viewGroup) {
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_name), clinica.getNome());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_fone), clinica.getTelefone());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_email), clinica.getEmail());
    }

    private void InsertEndereco(ViewGroup viewGroup) {
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cep), clinica.getCodigoPostal());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_rua), clinica.getRua());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_numero), String.valueOf(clinica.getNumero()));
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_complemento), clinica.getComplemento());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cidade), clinica.getCidade());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_bairro), clinica.getBairro());
        ViewsUtil.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_estado), clinica.getEstado());
    }

    public void insertDaysOfWorkInLayout(ViewGroup linearLayout) {
        DayOfWorkUiService dayOfWorkUiService = new DayOfWorkUiService(linearLayout);
        dayOfWorkUiService.insertAllDayOfWorkWithNotRemoveButton(dataBase.dayOfWorkDao(), clinica.getId());

    }

    public void configureNavButton(BottomNavigationView navigationView) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context, navigationView);

        Intent intent = new Intent(context, FormularioClinicaActivity.class);
        intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinica);
        menuConfigurator.onClickInBottomAppBar(R.id.navegation_edit, intent);


        configureDeleteButton(navigationView);
    }

    private void configureDeleteButton(BottomNavigationView navigationView) {
        navigationView.getMenu().findItem(R.id.navegation_delete).setOnMenuItemClickListener(item -> {
            RemoveConfirmPopUp removeConfirmPopUp = new RemoveConfirmPopUp(((Activity) context).getLayoutInflater());
            removeConfirmPopUp.getConfirmButton().setOnClickListener(onClickButton -> {
                dataBase.clinicaDao().delete(clinica);
                for (DayOfWork dayOfWork : dayOfWorkList) {
                    dataBase.dayOfWorkDao().delete(dayOfWork);
                }
                ((Activity) context).finish();
            });
            removeConfirmPopUp.getPopUpWindow().showAtLocation(((Activity) context).
                    findViewById(R.id.clinica_description_activity_root_layout), Gravity.CENTER, 0, 0);
            return false;
        });
    }


}
