package com.example.worknutri.ui.detail.detailPaciente;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.calcular.CalculadorAntropometrico;
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
import com.example.worknutri.ui.popUp.detailsPopUp.AntropometriaDetaillPopUp;
import com.example.worknutri.ui.popUp.detailsPopUp.PatologiaDetaillPopUp;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DetailPacienteAdapter {
    private Paciente paciente;
    private Patologia patologia;
    private Antropometria antropometria;
    private Clinica clinica;
    private final PacienteDao pacienteDao;
    private final AntropometriaDao antropometriaDao;
    private final PatologiaDao patologiaDao;

    private final ClinicaDao clinicaDao;
    private final Context context;

    public DetailPacienteAdapter(Paciente paciente, Context context) {

        this.paciente = paciente;
        AppDataBase db = AppDataBase.getInstance(context);
        pacienteDao = db.pacienteDao();
        antropometriaDao = db.antropometriaDao();
        patologiaDao = db.patologiaDao();
        clinicaDao = db.clinicaDao();
        this.context = context;
    }

    public void refreshData() {
        antropometria = antropometriaDao.getByPacienteId(paciente.getId());
        patologia = patologiaDao.loadAllByIdPaciente(paciente.getId()).get(0);
        clinica = clinicaDao.getById(paciente.getClinicaId());
        paciente = pacienteDao.getById(paciente.getId());
    }

    public void configureNavButtom(BottomNavigationView bottomNavigationView) {
        BottomMenuConfigurator menuConfigurator = new BottomMenuConfigurator(context, bottomNavigationView);
        bottomNavEditIcon(menuConfigurator);
        bottomNavClinicaIcon(menuConfigurator);
        bottomNavDeleteIcon(bottomNavigationView);
    }

    private void bottomNavEditIcon(BottomMenuConfigurator menuConfigurator) {
        Intent intent = new Intent(context, FormularioPacienteActivity.class);
        intent.putExtra(ExtrasActivities.PACIENTE_EXTRA.getKey(), paciente);
        menuConfigurator.onClickInBottomAppBar(R.id.navegation_edit, intent);
    }

    private void bottomNavClinicaIcon(BottomMenuConfigurator menuConfigurator) {
        Intent intent;
        if (clinica != null){
            intent = new Intent(context, ClinicaDescriptionActivity.class);
            intent.putExtra(ExtrasActivities.CLINICA_EXTRA.getKey(), clinica);
            menuConfigurator.onClickInBottomAppBar(R.id.navigation_clinica_paciente, intent);
        }
    }

    private void bottomNavDeleteIcon(BottomNavigationView bottomNavigationView) {
        bottomNavigationView.getMenu().findItem(R.id.navegation_delete).
                setOnMenuItemClickListener(onClick -> {
                    RemoveConfirmPopUp popUp = new PopUpFactoryImpl( context).generateRemoveConfirmPopUp();
                    popUp.getConfirmButton().setOnClickListener(onClickButton -> {
                        pacienteDao.delete(paciente);
                        patologiaDao.delete(patologia);
                        antropometriaDao.delete(antropometria);
                        ((Activity) context).finish();
                    });
                    popUp.getPopUpWindow().showAtLocation(((Activity)context).findViewById(R.id.paciente_description_activity_layout), Gravity.CENTER, 0, 0);

                    return false;
                });
    }

    public void insertTextInLayout(ViewGroup viewGroup) {

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_name_paciente_descrition))
                .setText(paciente.getNomePaciente());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_idade_paciente_descrition))
                .setText(getAgeText());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_fone_paciente_descrition))
                .setText(paciente.getTelefone());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_email_paciente_descrition))
                .setText(paciente.getEmail());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_observation_paciente_descrition))
                .setText(paciente.getObservacoes());



        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_height_paciente_descrition))
                .setText(antropometria.getAltura());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_peso_paciente_descrition))
                .setText(antropometria.getPeso());

        ((TextView) viewGroup.findViewById(R.id.paciente_description_activity_peso_ideal_paciente_descrition))
                .setText(antropometria.getPesoIdeal());
    }

    @NonNull
    private String getAgeText() {
        int yearFromDate = CalculadorAntropometrico.getYearFromDate(paciente.getNascimento());
        return String.valueOf(yearFromDate).concat(yearFromDate == 1 ? " ano" : " anos");
    }


    public void generateAntropometriaAndPatologiaPopUp(ViewGroup viewGroup) {

        generateAntropometriaPopUp(viewGroup);
        generatePatologiaPopUp(viewGroup);

    }

    private void generateAntropometriaPopUp(ViewGroup viewGroup) {
        Button buttonAntropometria = viewGroup.findViewById(R.id.paciente_description_activity_button_antropometric);
        buttonAntropometria.setOnClickListener(v -> {
            AntropometriaDetaillPopUp antropometriaDetaillPopUp = new PopUpFactoryImpl(context).
                    generateAntropometriaPopUp();
            antropometriaDetaillPopUp.generateComplete(antropometria);
            antropometriaDetaillPopUp.getPopUpWindow().
                    showAtLocation(viewGroup.findViewById(R.id.paciente_description_activity_layout), Gravity.CENTER, -1, -1);
        });
    }

    private void generatePatologiaPopUp(ViewGroup viewGroup) {
        Button buttonPatologia = viewGroup.findViewById(R.id.paciente_description_activity_button_patologic);
        buttonPatologia.setOnClickListener(v -> {
                    PatologiaDetaillPopUp popUp = new PopUpFactoryImpl(context).
                            generatePatologiaDetailPopUp();
                    popUp.setText(patologia);
                    popUp.removeMarginBottom();
                    popUp.getPopUpWindow().showAtLocation(viewGroup.findViewById(R.id.paciente_description_activity_layout),
                            Gravity.CENTER, -1, -1);
                }
                );
    }

    public String getNomePaciente(){
        return paciente.getNomePaciente();
    }
}
