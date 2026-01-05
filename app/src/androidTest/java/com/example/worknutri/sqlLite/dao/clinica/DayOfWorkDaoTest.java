package com.example.worknutri.sqlLite.dao.clinica;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DayOfWorkDaoTest {

    private AppDataBase db;
    private DayOfWorkDao dao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        dao = db.dayOfWorkDao();
    }

    @After
    public void tearDown() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private DayOfWork newDay(long clinicaId, String inicio, String fim, String day) {
        DayOfWork d = new DayOfWork();
        d.setIdClinica(clinicaId);
        d.setHoraInicio(inicio);
        d.setHoraFim(fim);
        d.setDayOfWeek(day);
        return d;
    }

    @Test
    public void insert_assignsIdAndGetAllReturnsInserted() {
        DayOfWork d1 = newDay(1, "08:00", "12:00", "Segunda");
        DayOfWork d2 = newDay(1, "13:00", "17:00", "Terça");

        dao.insert(d1);
        dao.insert(d2);

        List<DayOfWork> all = dao.getAll();
        assertEquals(2, all.size());
        assertTrue(all.get(0).getId() > 0);
        assertTrue(all.get(1).getId() > 0);
        assertNotEquals(all.get(0).getId(), all.get(1).getId());
    }

    @Test
    public void getDaysforClinicaId_filtersByClinicaId() {
        DayOfWork a = newDay(10, "07:00", "11:00", "Segunda");
        DayOfWork b = newDay(20, "09:00", "13:00", "Terça");
        DayOfWork c = newDay(10, "14:00", "18:00", "Quarta");

        dao.insert(a);
        dao.insert(b);
        dao.insert(c);

        List<DayOfWork> for10 = dao.getDaysforClinicaId(10);
        assertEquals(2, for10.size());
        for (DayOfWork d : for10) {
            assertEquals(10, d.getIdClinica());
        }

        List<DayOfWork> for20 = dao.getDaysforClinicaId(20);
        assertEquals(1, for20.size());
        assertEquals(20, for20.get(0).getIdClinica());
    }

    @Test
    public void update_modifiesExistingEntry() {
        DayOfWork d = newDay(3, "06:00", "10:00", "Segunda");
        dao.insert(d);

        DayOfWork stored = dao.getAll().get(0);
        long id = stored.getId();

        stored.setHoraInicio("06:30");
        stored.setHoraFim("10:30");
        stored.setDayOfWeek("Terça");
        dao.update(stored);

        List<DayOfWork> all = dao.getAll();
        assertEquals(1, all.size());
        DayOfWork updated = all.get(0);
        assertEquals(id, updated.getId());
        assertEquals("06:30", updated.getHoraInicio());
        assertEquals("10:30", updated.getHoraFim());
        assertEquals("Terça", updated.getDayOfWeek());
    }

    @Test
    public void delete_removesEntryAndNonMatchingDeleteDoesNotAffectStore() {
        DayOfWork d = newDay(5, "07:00", "11:00", "Sexta");
        dao.insert(d);

        List<DayOfWork> before = dao.getAll();
        assertEquals(1, before.size());

        DayOfWork stored = before.get(0);
        dao.delete(stored);

        List<DayOfWork> afterDelete = dao.getAll();
        assertEquals(0, afterDelete.size());

        // tentativa de deletar id inexistente não deve lançar e não altera demais
        DayOfWork notExists = newDay(5, "00:00", "00:00", "Domingo");
        notExists.setId(9999);
        dao.insert(newDay(6, "08:00", "12:00", "Sábado")); // adiciona outro
        dao.delete(notExists); // não existe no banco

        List<DayOfWork> finalList = dao.getAll();
        assertEquals(1, finalList.size()); // apenas o que inserimos permanece
    }
}

