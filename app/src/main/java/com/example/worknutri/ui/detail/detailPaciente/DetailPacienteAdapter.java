package com.example.worknutri.ui.detail.detailPaciente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.dao.paciente.PatologiaDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.BottomMenuConfigurator;
import com.example.worknutri.ui.ExtrasActivities;
import com.example.worknutri.ui.detail.detailClinica.ClinicaDescriptionActivity;
import com.example.worknutri.ui.formularios.formularioPaciente.FormularioPacienteActivity;
import com.example.worknutri.ui.popUp.RemoveConfirmPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.AntroPometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailPacienteAdapter {
    private Paciente paciente;
    private Patologia patologia;
    private Antropometria antropometria;
    private Clinica clinica;
    private PacienteDao pacienteDao;
    private AntropometriaDao antropometriaDao;
    private PatologiaDao patologiaDao;

    private ClinicaDao clinicaDao;
    private final Context context;

    public DetailPacienteAdapter(Intent intent, Context context) {


        if (intent.hasExtra(ExtrasActivities.PACIENTE)) {
            paciente = (Paciente) intent.getSerializableExtra(ExtrasActivities.PACIENTE);
            AppDataBase db = AppDataBase.getInstance(context);
            pacienteDao = db.pacienteDao();
            antropometriaDao = db.antropometriaDao();
            patologiaDao = db.patologiaDao();
            clinicaDao = db.clinicaDao();


        } else {
            ((Activity) context).finish();
        }
        this.context = context;
    }

    public void refreshData() {
        antropometria = antropometriaDao.getByPacienteId(paciente.getId());
        patologia = patologiaDao.loadAllByIdPaciente(paciente.getId()).get(0);
        clinica = clinicaDao.getById(paciente.getClinicaId());
        paciente = pacienteDao.getById(paciente.getId());
    }

    public void configureNavButtom(BottomNavigationView bottomNavigationView, ViewGroup viewGroup) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context, bottomNavigationView);
        Intent intent = new Intent(context, FormularioPacienteActivity.class);
        intent.putExtra(ExtrasActivities.PACIENTE, paciente);
        menuConfigurator.onClickInBottomAppBar(R.id.navegation_edit, intent);

        intent = new Intent(context, ClinicaDescriptionActivity.class);
        intent.putExtra(ExtrasActivities.CLINICA, clinica);
        menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinica_paciente, intent);

        bottomNavigationView.getMenu().findItem(R.id.navegation_delete).
                setOnMenuItemClickListener(onClick -> {
                    RemoveConfirmPopUp popUp = new RemoveConfirmPopUp(((Activity) context).getLayoutInflater());
                    popUp.getConfirmButton().setOnClickListener(onClickButton -> {
                        pacienteDao.delete(paciente);
                        patologiaDao.delete(patologia);
                        antropometriaDao.delete(antropometria);
                        ((Activity) context).finish();
                    });
                    popUp.getPopUpWindow().showAtLocation(viewGroup, Gravity.CENTER, 0, 0);

                    return false;
                });
    }

    public void insertTextInLayout(ViewGroup viewGroup) {

        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_name_paciente_descrition)).setText(paciente.getNomePaciente());
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_idade_paciente_descrition)).setText(String.valueOf(paciente.getIdade()));
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_fone_paciente_descrition)).setText(paciente.getTelefone());
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_email_paciente_descrition)).setText(paciente.getEmail());
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_observation_paciente_descrition)).setText(paciente.getObservacoes());


        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_height_paciente_descrition)).setText(antropometria.getAltura());
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_peso_paciente_descrition)).setText(antropometria.getPeso());
        ((TextView) viewGroup.findViewById(R.id.detail_paciente_activity_peso_ideal_paciente_descrition)).setText(antropometria.getPesoIdeal());
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void moreDetailButtonsConfig(ViewGroup viewGroup, LayoutInflater layoutInflater) {

        AntroPometriaDetaillPopUp antropometriaPopUp = new AntroPometriaDetaillPopUp(layoutInflater, antropometria, true);
        Button buttonAntropometria = viewGroup.findViewById(R.id.detail_paciente_activity_button_antropometric);
        buttonAntropometria.setOnClickListener(v -> {
            antropometriaPopUp.generateClassificacaoImc(Double.parseDouble(antropometria.getImc()), layoutInflater.getContext());
            antropometriaPopUp.getPopUpWindow().showAtLocation(viewGroup.findViewById(R.id.detail_paciente_activity_layout), Gravity.CENTER, -1, -1);
        });

        PatologiaDetaillPopUp patologiaPopUp = new PatologiaDetaillPopUp(layoutInflater, patologia);
        Button buttonPatologia = viewGroup.findViewById(R.id.detail_paciente_activity_button_patologic);
        buttonPatologia.setOnClickListener(v -> patologiaPopUp.getPopUpWindow().showAtLocation(viewGroup.findViewById(R.id.detail_paciente_activity_layout), Gravity.CENTER, -1, -1));

    }
}
