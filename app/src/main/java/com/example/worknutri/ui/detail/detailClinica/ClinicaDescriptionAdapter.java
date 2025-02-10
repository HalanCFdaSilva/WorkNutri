package com.example.worknutri.ui.detail.detailClinica;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.ui.BottomMenuConfigurator;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.InsertSelectViewSupport;
import com.example.worknutri.ui.formularios.formularioClinica.FormularioClinicaActivity;
import com.example.worknutri.ui.popUp.hourDatePopUp.HourDateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class ClinicaDescriptionAdapter {
    private Clinica clinica;
    private List<DayOfWork> dayOfWorkList;
    private AppDataBase dataBase;
    private Context context;
    public ClinicaDescriptionAdapter(Intent intent,Context context) {
        if (intent.hasExtra(ExtrasActivities.CLINICA)){
            this.clinica = (Clinica) intent.getSerializableExtra(ExtrasActivities.CLINICA);
            dataBase = AppDataBase.getInstance(context);
            dayOfWorkList = dataBase.dayOfWorkDao().getDaysforClinicaId(clinica.getId());
            this.context = context;
        }else ((Activity)context).finish();


    }

    public void insertClinicaInLayout(ViewGroup viewGroup) {
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_name),clinica.getNome());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_fone),clinica.getTelefone1());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_dados_gerais_email),clinica.getEmail());

        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cep),clinica.getCodigoPostal());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_rua),clinica.getRua());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_numero),String.valueOf(clinica.getNumero()));
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_complemento),clinica.getComplemento());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cidade),clinica.getCidade());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_bairro),clinica.getBairro());
        InsertSelectViewSupport.insertInTextView(viewGroup.findViewById(
                R.id.clinica_description_activity_endereco_cep),clinica.getEstado());

    }

    public void insertDaysOfWorkInLayout(LinearLayout linearLayout, LayoutInflater inflater) {


        for (DayOfWork dayOfWork : dayOfWorkList){
            HourDateFragment dateFragment = new HourDateFragment(inflater);
            dateFragment.removeTrashButton();
            dateFragment.setHourBegin(dayOfWork.getHoraInicio());
            dateFragment.setHourEnd(dayOfWork.getHoraFim());
            dateFragment.setDayOfweek(dayOfWork.getDayOfWeek());
            dateFragment.addLayout(linearLayout);
        }
    }

    public void configureNavButton(BottomNavigationView navigationView) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context,navigationView);

        Intent intent = new Intent(context, FormularioClinicaActivity.class);
        intent.putExtra(ExtrasActivities.CLINICA,clinica);
        menuConfigurator.onClickInBottomAppBar(R.id.navegation_edit,intent);


        navigationView.getMenu().findItem(R.id.navegation_delete).setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                dataBase.clinicaDao().delete(clinica);
                for (DayOfWork dayOfWork : dayOfWorkList){
                    dataBase.dayOfWorkDao().delete(dayOfWork);
                }
                ((Activity)context).finish();

                return false;
            }
        });
    }
}
