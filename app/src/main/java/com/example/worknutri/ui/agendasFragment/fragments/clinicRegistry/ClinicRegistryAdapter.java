package com.example.worknutri.ui.agendasFragment.fragments.clinicRegistry;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.registryInflater.ClinicRegistryInflater;
import com.example.worknutri.ui.agendasFragment.RegistryOrdenators.OrderListOfRegistry;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicFilterPojo;

import java.util.List;

public class ClinicRegistryAdapter {

    private final ClinicaDao clinicaDao;
    private final PacienteDao pacienteDao;
    private final DayOfWorkDao dayOfWorkDao;
    private final Context context;
    private ClinicFilterPojo clinicFilterPojo;

    public ClinicRegistryAdapter(Context context) {
        this.context = context;
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        clinicaDao = appDataBase.clinicaDao();
        pacienteDao = appDataBase.pacienteDao();
        dayOfWorkDao = appDataBase.dayOfWorkDao();
        clinicFilterPojo = new ClinicFilterPojo();
        clinicFilterPojo.setClinicsSelected(clinicaDao.getAllInOrder());
        updatePojo();
    }

    public void inflateAgenda(LinearLayout linearLayout) {
        linearLayout.removeAllViews();

        OrderListOfRegistry orderListOfRegistry = new OrderListOfRegistry();
        List<Clinica> clinicasOrdered = orderListOfRegistry.orderToClinicSchedule(clinicFilterPojo);
        clinicFilterPojo.setClinicsSelected(clinicasOrdered);
        
        ClinicRegistryInflater clinicRegistryInflater = new ClinicRegistryInflater(clinicFilterPojo);
        clinicRegistryInflater.inflateSchedule(linearLayout, context);

    }

    public void inflateAgenda(LinearLayout linearLayout, List<Clinica> clinicaList) {
        clinicFilterPojo.setClinicsSelected(clinicaList);
        inflateAgenda(linearLayout);

    }

    public Context getContext() {
        return context;
    }

    public List<Clinica> getClinicaList() {
        return clinicaDao.getAllInOrder();
    }

    public ClinicFilterPojo getClinicaFilterPojo() {
        return clinicFilterPojo;
    }

    public void setClinicaFilterPojo(ClinicFilterPojo clinicFilterPojo) {
        this.clinicFilterPojo = clinicFilterPojo;
    }

    public void updatePojo() {
        clinicFilterPojo.setClinicsList(clinicaDao.getAll());
        clinicFilterPojo.setPatientList(pacienteDao.getAll());
        clinicFilterPojo.setDayOfWorkList(dayOfWorkDao.getAll());

    }
}
