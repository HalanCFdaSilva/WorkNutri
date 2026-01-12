package com.example.worknutri.sqlLite.database;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Method;

@RunWith(AndroidJUnit4.class)
public class AppDataBaseTest {

    private AppDataBase db;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class)
                .allowMainThreadQueries()
                .build();
    }

    @After
    public void tearDown() {
        if (db != null) {
            db.close();
        }
    }


    @Test
    public void getInstance_returnsNonNullAndIsAppDataBase() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDataBase.getInstance(context);

        assertNotNull("getInstance should not return null", db);
        assertTrue("Returned object should be AppDataBase", db instanceof AppDataBase);
    }

    @Test
    public void getInstance_isSingletonAcrossCalls() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDataBase first = AppDataBase.getInstance(context);
        AppDataBase second = AppDataBase.getInstance(context);

        assertSame("getInstance should return the same singleton instance", first, second);

        db = first;
    }

    @Test
    public void getInstance_opensWhenAccessedAndCanBeClosed() {
        Context context = ApplicationProvider.getApplicationContext();
        db = AppDataBase.getInstance(context);
        db.getOpenHelper().getWritableDatabase();

        assertTrue("Database should be open after getInstance", db.isOpen());

        db.close();
        assertFalse("Database should be closed after close()", db.isOpen());
    }

    @Test
    public void allDaoMethodsReturnNonNullAndExpectedType() throws Exception {
        Object[][] cases = new Object[][]{
                {"pacienteDao", com.example.worknutri.sqlLite.dao.paciente.PacienteDao.class},
                {"patologiaDao", com.example.worknutri.sqlLite.dao.paciente.PatologiaDao.class},
                {"antropometriaDao", com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao.class},
                {"clinicaDao", com.example.worknutri.sqlLite.dao.clinica.ClinicaDao.class}, // verifique pacote se necessário
                {"dayOfWorkDao", com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao.class}
        };

        for (Object[] c : cases) {
            String methodName = (String) c[0];
            Class<?> expectedClass = (Class<?>) c[1];

            Method m = db.getClass().getMethod(methodName);
            Object dao = m.invoke(db);

            assertNotNull("DAO should not be null for method: " + methodName, dao);
            assertTrue("DAO should be instance of " + expectedClass.getSimpleName(),
                    expectedClass.isInstance(dao));
        }
    }

}
