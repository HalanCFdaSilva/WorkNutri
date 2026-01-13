package com.example.worknutri.sqlLite.dao.paciente;

import static org.junit.Assert.*;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PacienteDaoTest {

    private AppDataBase db;
    private PacienteDao dao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        dao = db.pacienteDao();
    }

    @After
    public void tearDown() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private Paciente newPaciente(String nome) {
        Paciente p = new Paciente();
        // campos mínimos esperados; ajuste caso sua entidade tenha nomes diferentes
        try { p.setId(0); } catch (Exception ignored) {}
        try { p.setNomePaciente(nome); } catch (Exception ignored) {}
        try { p.setEmail(nome.toLowerCase().replace(" ", "") + "@test.com"); } catch (Exception ignored) {}
        try { p.setTelefone("000000000"); } catch (Exception ignored) {}
        return p;
    }

    @Test
    public void insertAll_assignsIdsAndGetAllReturnsInserted() {
        Paciente p1 = newPaciente("Paciente Um");
        Paciente p2 = newPaciente("Paciente Dois");

        dao.insertAll(p1, p2);

        List<Paciente> all = dao.getAll();
        assertEquals(2, all.size());

        long id1 = all.get(0).getId();
        long id2 = all.get(1).getId();
        assertTrue(id1 > 0);
        assertTrue(id2 > 0);
        assertNotEquals(id1, id2);
    }

    @Test
    public void getAllInOrder_returnsPacientesSortedByName() {
        Paciente a = newPaciente("Zzz Paciente");
        Paciente b = newPaciente("Aaa Paciente");
        Paciente c = newPaciente("Medi Paciente");

        dao.insertAll(a, b, c);

        List<Paciente> ordered = dao.getAllInOrder();
        assertEquals(3, ordered.size());
        assertEquals("Aaa Paciente", ordered.get(0).getNomePaciente());
        assertEquals("Medi Paciente", ordered.get(1).getNomePaciente());
        assertEquals("Zzz Paciente", ordered.get(2).getNomePaciente());
    }

    @Test
    public void getById_returnsCorrectPaciente() {
        Paciente p = newPaciente("Paciente Único");
        dao.insertAll(p);

        List<Paciente> all = dao.getAll();
        assertFalse(all.isEmpty());
        long id = all.get(0).getId();

        Paciente found = dao.getById(id);
        assertNotNull(found);
        assertEquals(id, found.getId());
        assertEquals("Paciente Único", found.getNomePaciente());
    }

    @Test
    public void findByName_exactAndWildcardMatches() {
        Paciente p1 = newPaciente("Alpha");
        Paciente p2 = newPaciente("Beta");
        Paciente p3 = newPaciente("Alpha Beta");
        dao.insertAll(p1, p2, p3);

        List<Paciente> exact = dao.findByName("Alpha");
        assertEquals(1, exact.size());
        assertEquals("Alpha", exact.get(0).getNomePaciente());

        List<Paciente> wildcard = dao.findByName("%Alpha%");
        assertTrue(wildcard.size() >= 2);
    }

    @Test
    public void update_modifiesExistingPaciente() {
        Paciente p = newPaciente("Antes");
        dao.insertAll(p);

        Paciente stored = dao.getAll().get(0);
        long id = stored.getId();

        stored.setNomePaciente("Depois");
        dao.update(stored);

        Paciente updated = dao.getById(id);
        assertNotNull(updated);
        assertEquals(id, updated.getId());
        assertEquals("Depois", updated.getNomePaciente());
    }

    @Test
    public void delete_removesEntryAndNonExistingDeleteDoesNotAffectStore() {
        Paciente p = newPaciente("Para Deletar");
        dao.insertAll(p);

        List<Paciente> before = dao.getAll();
        assertEquals(1, before.size());

        Paciente stored = before.get(0);
        dao.delete(stored);

        List<Paciente> afterDelete = dao.getAll();
        assertEquals(0, afterDelete.size());

        // deletar um objeto com id inexistente não deve afetar os demais
        Paciente other = newPaciente("Mantem");
        dao.insertAll(other);
        Paciente fake = newPaciente("Fake");
        fake.setId(99999);
        dao.delete(fake);

        List<Paciente> finalList = dao.getAll();
        assertEquals(1, finalList.size());
        assertEquals("Mantem", finalList.get(0).getNomePaciente());
    }

    @Test
    public void findByName_returnsEmptyWhenNoMatch() {
        // garante que busca sem correspondência retorna lista vazia
        List<Paciente> result = dao.findByName("Não Existe");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void deletingPaciente_deletesLinkedPatologiaAndAntropometria_dueToCascade() {
        // insere paciente
        Paciente paciente = newPaciente("Paciente Cascade");
        dao.insertAll(paciente);

        List<Paciente> pacientes = dao.findByName("Paciente Cascade");
        assertEquals(1, pacientes.size());
        Paciente storedPaciente = pacientes.get(0);
        long pacienteId = storedPaciente.getId();

        // insere patologia vinculada

        Patologia pat = new Patologia();
        try { pat.setIdPaciente((int) pacienteId); } catch (Exception ignored) {}
        pat.setPatologiaAtual("Hipertensão");
        PatologiaDao patologiaDao = db.patologiaDao();
        patologiaDao.insert(pat);

        // insere antropometria vinculada
        Antropometria ant = new Antropometria();
        try { ant.setIdPaciente((int) pacienteId); } catch (Exception ignored) {}
        ant.setPeso("70");
        AntropometriaDao antropometriaDao = db.antropometriaDao();
        antropometriaDao.insertAll(ant);

        // confirma inserções
        List<Patologia> patologiasBefore = patologiaDao.loadAllByIdPaciente((int) pacienteId);
        Antropometria antrosBefore = antropometriaDao.getByPacienteId((int) pacienteId);
        assertEquals(1, patologiasBefore.size());
        assertNotNull(antrosBefore);

        // deleta paciente
        dao.delete(storedPaciente);

        // confirma que entradas vinculadas foram removidas por cascade
        List<Patologia> patologiasAfter = patologiaDao.loadAllByIdPaciente((int) pacienteId);
        Antropometria antrosAfter = antropometriaDao.getByPacienteId((int) pacienteId);
        assertTrue(patologiasAfter == null || patologiasAfter.isEmpty());
        assertNull(antrosAfter);
    }


}

