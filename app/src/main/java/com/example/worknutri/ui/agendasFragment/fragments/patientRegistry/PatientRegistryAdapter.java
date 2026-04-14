package com.example.worknutri.ui.agendasFragment.fragments.patientRegistry;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.registryInflater.PatientRegistryInflater;
import com.example.worknutri.ui.agendasFragment.RegistryOrdenators.OrderListOfRegistry;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PatientFilterPojo;
import java.util.List;

public class PatientRegistryAdapter {
    private final Context context;
    private final PacienteDao pacienteDao;
    private final AntropometriaDao antropometriaDao;

    private final ClinicaDao clinicaDao;
    private PatientFilterPojo patientFilterPojo;

    public PatientRegistryAdapter(Context context) {
        this.context = context;
        AppDataBase appDB = AppDataBase.getInstance(context);
        pacienteDao = appDB.pacienteDao();
        antropometriaDao = appDB.antropometriaDao();
        clinicaDao = appDB.clinicaDao();
        patientFilterPojo =  new PatientFilterPojo(getPacientes(), antropometriaDao.getAll(), clinicaDao.getAllInOrder());
    }

    public void inflateAgenda(LinearLayout linearLayout) {

        linearLayout.removeAllViews();
        List<Paciente> pacienteOrdered = new OrderListOfRegistry().orderToAgendaPacientes(patientFilterPojo);
        patientFilterPojo.setPatientsSelected(pacienteOrdered);
        PatientRegistryInflater patientRegistryInflater = new PatientRegistryInflater(patientFilterPojo);
        patientRegistryInflater.inflateSchedule(linearLayout, context);


    }

    public void inflateAgendaAfterSearch(LinearLayout linearLayout, List<Paciente> pacientes) {
        linearLayout.removeAllViews();
        patientFilterPojo.setPatientsSelected(pacientes);
        inflateAgenda(linearLayout);



    }


    public List<Paciente> getPacientes() {
        return pacienteDao.getAllInOrder();
    }

    public Context getContext() {
        return this.context;
    }

    public void setPacienteFilterPojo(PatientFilterPojo patientFilterPojo) {
        this.patientFilterPojo = patientFilterPojo;
    }
    public PatientFilterPojo getPacienteFilterPojo(){
        return patientFilterPojo;
    }
    public void updatePojo(){
        patientFilterPojo.setPatientList(getPacientes());
        patientFilterPojo.setAnthropometryList(antropometriaDao.getAll());
        patientFilterPojo.setClinicas(clinicaDao.getAllInOrder());
    }
}
