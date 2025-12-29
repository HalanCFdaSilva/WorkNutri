package com.example.worknutri.support;

import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Fake in-memory implementation de DayOfWorkDao para uso em androidTest.
 * - mantém uma lista local de DayOfWork
 * - marca deletedCalled e armazena deletedDay quando delete é chamado
 */
public class FakeDayOfWorkDao implements DayOfWorkDao {
    public boolean deletedCalled = false;
    public DayOfWork deletedDay = null;

    private final List<DayOfWork> store = new ArrayList<>();
    private long nextId = 1;

    @Override
    public void delete(DayOfWork day) {
        deletedCalled = true;
        deletedDay = day;
        if (day == null) return;
        Iterator<DayOfWork> it = store.iterator();
        while (it.hasNext()) {
            DayOfWork d = it.next();
            if (d.getId() == day.getId()) {
                it.remove();
                break;
            }
        }
    }

    @Override
    public List<DayOfWork> getDaysforClinicaId(long clinicaId) {
        List<DayOfWork> result = new ArrayList<>();
        for (DayOfWork d : store) {
            if (d.getIdClinica() == clinicaId) {
                result.add(d);
            }
        }
        return result;
    }

    @Override
    public void insert(DayOfWork day) {
        if (day != null){
            if (day.getId() == 0) {
                day.setId(nextId++);
            } else {
                // keep nextId ahead of any manually set ids
                if (day.getId() >= nextId) nextId = day.getId() + 1;
            }
            store.add(day);
        }
    }

    @Override
    public void update(DayOfWork day) {
        if (day == null) return;
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId() == day.getId()) {
                store.set(i, day);
                return;
            }
        }
    }

    @Override
    public List<DayOfWork> getAll() {
        return new ArrayList<>(store);
    }
}
