package com.example.worknutri.ui.agendasFragment.fragments.agendaClinicas;

import android.content.Context;
import android.widget.LinearLayout;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.ui.agendasFragment.agendaInflater.ClinicaScheduleInflater;
import com.example.worknutri.ui.agendasFragment.agendaOrdenators.OrderListOfAgenda;
import com.example.worknutri.ui.agendasFragment.filter.pojos.clinicaFilter.ClinicaFilterPojo;

import java.util.List;

public class AgendaClinicasAdapter {

    private final ClinicaDao clinicaDao;
    private final PacienteDao pacienteDao;
    private final DayOfWorkDao dayOfWorkDao;
    private final Context context;
    private ClinicaFilterPojo clinicaFilterPojo;

    public AgendaClinicasAdapter(Context context) {
        this.context = context;
        AppDataBase appDataBase = AppDataBase.getInstance(context);
        clinicaDao = appDataBase.clinicaDao();
        pacienteDao = appDataBase.pacienteDao();
        dayOfWorkDao = appDataBase.dayOfWorkDao();
        clinicaFilterPojo = new ClinicaFilterPojo();
        clinicaFilterPojo.setClinicasSelected(clinicaDao.getAllInOrder());
        updatePojo();
    }

    public void inflateAgenda(LinearLayout linearLayout) {
        linearLayout.removeAllViews();

        OrderListOfAgenda orderListOfAgenda = new OrderListOfAgenda();
        List<Clinica> clinicasOrdered = orderListOfAgenda.orderToAgendaClinicas(clinicaFilterPojo);
        clinicaFilterPojo.setClinicasSelected(clinicasOrdered);
        
        ClinicaScheduleInflater clinicaScheduleInflater = new ClinicaScheduleInflater(clinicaFilterPojo);
        clinicaScheduleInflater.inflateSchedule(linearLayout, context);

    }

    public void inflateAgenda(LinearLayout linearLayout, List<Clinica> clinicaList) {
        clinicaFilterPojo.setClinicasSelected(clinicaList);
        inflateAgenda(linearLayout);

    }

    public Context getContext() {
        return context;
    }

    public List<Clinica> getClinicaList() {
        return clinicaDao.getAllInOrder();
    }

    public ClinicaFilterPojo getClinicaFilterPojo() {
        return clinicaFilterPojo;
    }

    public void setClinicaFilterPojo(ClinicaFilterPojo clinicaFilterPojo) {
        this.clinicaFilterPojo = clinicaFilterPojo;
    }

    public void updatePojo() {
        clinicaFilterPojo.setClinicas(clinicaDao.getAll());
        clinicaFilterPojo.setPacientes(pacienteDao.getAll());
        clinicaFilterPojo.setDayOfWorkList(dayOfWorkDao.getAll());

    }
}
