package com.example.worknutri.ui.agendasFragment.fragments.patientRegistry;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.ui.agendasFragment.agendaInflater.PatientRegistryInflater;
import com.example.worknutri.ui.agendasFragment.RegistryOrdenators.OrderListOfRegistry;
import com.example.worknutri.ui.agendasFragment.filter.pojos.pacienteFilter.PacienteFilterPojo;
import java.util.List;

public class PatientRegistryAdapter {
    private final Context context;
    private final PacienteDao pacienteDao;
    private final AntropometriaDao antropometriaDao;

    private final ClinicaDao clinicaDao;
    private PacienteFilterPojo pacienteFilterPojo;

    public PatientRegistryAdapter(Context context) {
        this.context = context;
        AppDataBase appDB = AppDataBase.getInstance(context);
        pacienteDao = appDB.pacienteDao();
        antropometriaDao = appDB.antropometriaDao();
        clinicaDao = appDB.clinicaDao();
        pacienteFilterPojo =  new PacienteFilterPojo(getPacientes(), antropometriaDao.getAll(), clinicaDao.getAllInOrder());
    }

    public void inflateAgenda(LinearLayout linearLayout) {

        linearLayout.removeAllViews();
        List<Paciente> pacienteOrdered = new OrderListOfRegistry().orderToAgendaPacientes(pacienteFilterPojo);
        pacienteFilterPojo.setPacienteSelected(pacienteOrdered);
        PatientRegistryInflater patientRegistryInflater = new PatientRegistryInflater(pacienteFilterPojo);
        patientRegistryInflater.inflateSchedule(linearLayout, context);


    }

    public void inflateAgendaAfterSearch(LinearLayout linearLayout, List<Paciente> pacientes) {
        linearLayout.removeAllViews();
        pacienteFilterPojo.setPacienteSelected(pacientes);
        inflateAgenda(linearLayout);



    }


    public List<Paciente> getPacientes() {
        return pacienteDao.getAllInOrder();
    }

    public Context getContext() {
        return this.context;
    }

    public void setPacienteFilterPojo(PacienteFilterPojo pacienteFilterPojo) {
        this.pacienteFilterPojo = pacienteFilterPojo;
    }
    public PacienteFilterPojo getPacienteFilterPojo(){
        return pacienteFilterPojo;
    }
    public void updatePojo(){
        pacienteFilterPojo.setPacientes(getPacientes());
        pacienteFilterPojo.setAntropometriaList(antropometriaDao.getAll());
        pacienteFilterPojo.setClinicas(clinicaDao.getAllInOrder());
    }
}
