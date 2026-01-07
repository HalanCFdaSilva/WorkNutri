package com.example.worknutri.sqlLite.dao.paciente;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AntropometriaDaoTest {

    private AppDataBase db;
    private AntropometriaDao dao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        dao = db.antropometriaDao();
    }

    @After
    public void tearDown() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private Antropometria generateAntropometria( String text) {

        int pacienteId = createPacienteInDB(text);
        Antropometria antropometria = new Antropometria();

        try { antropometria.setIdPaciente(pacienteId); } catch (Exception ignored) {}
        try { antropometria.setImc(text); } catch (Exception ignored) {}
        try { antropometria.setPeso(text); } catch (Exception ignored) {}
        try { antropometria.setPesoIdeal(text); } catch (Exception ignored) {}
        try { antropometria.setAltura(text); } catch (Exception ignored) {}
        try { antropometria.setIdade(12); } catch (Exception ignored) {}

        try { antropometria.setCircumferenciaBracoDir(text); } catch (Exception ignored) {}
        try { antropometria.setCircumferenciaCoxaDir(text); } catch (Exception ignored) {}
        try { antropometria.setCircumferenciaAbdomen(text); } catch (Exception ignored) {}
        try { antropometria.setCircumferenciaCintura(text); } catch (Exception ignored) {}
        try { antropometria.setCircumferenciaQuadril(text); } catch (Exception ignored) {}

        try { antropometria.setAgua(text); } catch (Exception ignored) {}
        try { antropometria.setRegraBolso(text); } catch (Exception ignored) {}
        try { antropometria.setVenta(text); } catch (Exception ignored) {}
        try { antropometria.setValorMetabolico(text); } catch (Exception ignored) {}
        try { antropometria.setTaxaMetabolica(text); } catch (Exception ignored) {}
        return antropometria;
    }

    private int createPacienteInDB(String nome){
        Paciente p = new Paciente();
        p.setNomePaciente(nome);
        PacienteDao pacienteDao = db.pacienteDao();
        pacienteDao.insertAll(p);
        List<Paciente> byName = pacienteDao.findByName(nome);
       for (int i = 0; i<byName.size(); i++){
           if (byName.get(0).getNomePaciente().equals(p.getNomePaciente()))
               return (int) byName.get(0).getId();
       }
       return 0;
    }

    private void checkAntropometriaValor(Antropometria antropometriaEsperada, Antropometria antropometriaComparada){
        Assert.assertEquals(antropometriaEsperada.getIdPaciente(),antropometriaComparada.getIdPaciente());

        Assert.assertEquals(antropometriaEsperada.getCircumferenciaCintura(),antropometriaComparada.getCircumferenciaCintura());
        Assert.assertEquals(antropometriaEsperada.getCircumferenciaBracoDir(),antropometriaComparada.getCircumferenciaBracoDir());
        Assert.assertEquals(antropometriaEsperada.getCircumferenciaCoxaDir(),antropometriaComparada.getCircumferenciaCoxaDir());
        Assert.assertEquals(antropometriaEsperada.getCircumferenciaAbdomen(),antropometriaComparada.getCircumferenciaAbdomen());
        Assert.assertEquals(antropometriaEsperada.getCircumferenciaQuadril(),antropometriaComparada.getCircumferenciaQuadril());

        Assert.assertEquals(antropometriaEsperada.getPeso(),antropometriaComparada.getPeso());
        Assert.assertEquals(antropometriaEsperada.getPesoIdeal(),antropometriaComparada.getPesoIdeal());
        Assert.assertEquals(antropometriaEsperada.getAltura(),antropometriaComparada.getAltura());
        Assert.assertEquals(antropometriaEsperada.getIdade(),antropometriaComparada.getIdade());

        Assert.assertEquals(antropometriaEsperada.getAgua(),antropometriaComparada.getAgua());
        Assert.assertEquals(antropometriaEsperada.getVenta(),antropometriaComparada.getVenta());
        Assert.assertEquals(antropometriaEsperada.getValorMetabolico(),antropometriaComparada.getValorMetabolico());
        Assert.assertEquals(antropometriaEsperada.getTaxaMetabolica(),antropometriaComparada.getTaxaMetabolica());
        Assert.assertEquals(antropometriaEsperada.getImc(),antropometriaComparada.getImc());
        Assert.assertEquals(antropometriaEsperada.getRegraBolso(),antropometriaComparada.getRegraBolso());

    }

    @Test
    public void insertAll_assignsIdsAndGetAllReturnsInserted() {

        Antropometria p1 = generateAntropometria("Paciente Um");
        Antropometria p2 = generateAntropometria("Paciente Dois");
        dao.insertAll(p1, p2);

        List<Antropometria> all = dao.getAll();
        assertEquals(2, all.size());
        checkAntropometriaValor(p1,all.get(0));
        checkAntropometriaValor(p2,all.get(1));
    }

    @Test
    public void getById_returnsCorrectAntropometria() {

        Antropometria a = generateAntropometria("Paciente Único");
        dao.insertAll(a);


        Antropometria found = dao.getByPacienteId(a.getIdPaciente());
        assertNotNull(found);
        checkAntropometriaValor(a,found);
    }

    @Test
    public void update_modifiesExistingAntropometria() {
        Antropometria p = generateAntropometria("Antes");
        dao.insertAll(p);

        Antropometria stored = dao.getAll().get(0);
        long id = stored.getIdPaciente();

        stored.setTaxaMetabolica("Depois");
        dao.update(stored);

        Antropometria updated = dao.getByPacienteId(id);
        assertNotNull(updated);
        assertEquals(id, updated.getIdPaciente());
        assertEquals("Depois", updated.getTaxaMetabolica());
    }

    @Test
    public void delete_removesEntryAndNonExistingDeleteDoesNotAffectStore() {
        Antropometria p = generateAntropometria("Para Deletar");
        dao.insertAll(p);

        List<Antropometria> before = dao.getAll();
        assertEquals(1, before.size());

        Antropometria stored = before.get(0);
        dao.delete(stored);

        List<Antropometria> afterDelete = dao.getAll();
        assertEquals(0, afterDelete.size());

        // deletar um objeto com id inexistente não deve afetar os demais
        Antropometria other = generateAntropometria("Mantem");
        dao.insertAll(other);
        Antropometria fake = generateAntropometria("Fake");
        fake.setId(99999);
        dao.delete(fake);

        List<Antropometria> finalList = dao.getAll();
        assertEquals(1, finalList.size());
        checkAntropometriaValor(other,finalList.get(0));
    }

    @Test
    public void findByName_returnsEmptyWhenNoMatch() {
        Antropometria result = dao.getByPacienteId( 10);
        Assert.assertNull(result);
    }

    @Test
    public void insertAllDontInsertDuplicatePacienteId() {

        Antropometria p1 = generateAntropometria("Paciente Um");
        Antropometria p2 = generateAntropometria("Paciente Um");
        p2.setIdPaciente(p1.getIdPaciente());
        dao.insertAll(p1);
        try {
            dao.insertAll(p2);
        } catch (Exception e) {
            // Espera-se uma exceção devido à violação da chave primária
        }

        List<Antropometria> all = dao.getAll();
        assertEquals(1, all.size());
        checkAntropometriaValor(p1, all.get(0));
    }
}
