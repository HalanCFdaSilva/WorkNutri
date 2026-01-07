package com.example.worknutri.sqlLite.dao.paciente;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PatologiaDaoTest {

    private AppDataBase db;
    private PatologiaDao dao;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        dao = db.patologiaDao();
    }

    @After
    public void tearDown() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    private Patologia generatePatologia(String pacienteNome) {
        int pacienteId = createPacienteInDB(pacienteNome);
        Patologia p = new Patologia();
        try { p.setIdPaciente(pacienteId); } catch (Exception ignored) {}
        // Outros campos da entidade podem ser setados aqui se existirem,
        // usando try/catch para evitar erros se o setter não existir.
        return p;
    }

    private int createPacienteInDB(String nome){
        Paciente p = new Paciente();
        try { p.setNomePaciente(nome); } catch (Exception ignored) {}
        PacienteDao pacienteDao = db.pacienteDao();
        pacienteDao.insertAll(p);
        List<Paciente> byName = pacienteDao.findByName(nome);
        for (int i = 0; i<byName.size(); i++){
            if (byName.get(i).getNomePaciente().equals(p.getNomePaciente()))
                return (int) byName.get(i).getId();
        }
        return 0;
    }

    private void checkPatologiaIdPaciente(Patologia expected, Patologia actual){
        Assert.assertEquals(expected.getIdPaciente(), actual.getIdPaciente());
    }

    @Test
    public void insert_assignsAndGetReturnsInserted() {
        Patologia p1 = generatePatologia("Paciente A");
        Patologia p2 = generatePatologia("Paciente B");
        dao.insert(p1);
        dao.insert(p2);

        List<Patologia> all = dao.getAll();
        assertEquals(2, all.size());
        checkPatologiaIdPaciente(p1, all.get(0));
        checkPatologiaIdPaciente(p2, all.get(1));
    }



    @Test
    public void update_modifiesExistingPatologiaButNotIfModifypacienteId() {
        Patologia p = generatePatologia("Antes update");
        dao.insert(p);
        int oldPacienteId = p.getIdPaciente();
        List<Patologia> patologiesBeforeUpdate = dao.getAll();
        Patologia stored = patologiesBeforeUpdate.get(0);

        stored.setPatologiaAtual("Atualizada");
        dao.update(stored);

        List<Patologia> encontrados = dao.getAll();
        assertEquals(1, encontrados.size());
        assertEquals("Atualizada", encontrados.get(0).getPatologiaAtual());
        assertEquals(oldPacienteId, encontrados.get(0).getIdPaciente());

        int newPacienteId = createPacienteInDB("Depois update");
        stored = encontrados.get(0);
        stored.setIdPaciente(newPacienteId);
        stored.setPatologiaAtual("Outra Atualizacao");
        dao.update(stored);
        encontrados = dao.getAll();
        assertEquals(1, encontrados.size());
        assertNotEquals("Outra Atualizacao", encontrados.get(0).getPatologiaAtual());
        assertNotEquals(newPacienteId, encontrados.get(0).getIdPaciente());
        assertEquals(oldPacienteId, encontrados.get(0).getIdPaciente());
        assertEquals("Atualizada", encontrados.get(0).getPatologiaAtual());
    }

    @Test
    public void delete_removesEntryAndNonExistingDeleteDoesNotAffectStore() {
        Patologia p = generatePatologia("Para Deletar");
        dao.insert(p);

        List<Patologia> before = dao.getAll();
        assertEquals(1, before.size());

        Patologia stored = before.get(0);
        dao.delete(stored);

        List<Patologia> afterDelete = dao.getAll();
        assertEquals(0, afterDelete.size());

        // deletar um objeto com id inexistente não deve afetar os demais
        Patologia other = generatePatologia("Mantem");
        dao.insert(other);
        Patologia fake = generatePatologia("Fake");
        try { fake.setId(99999); } catch (Exception ignored) {}
        try { dao.delete(fake); } catch (Exception ignored) {}

        List<Patologia> finalList = dao.getAll();
        assertEquals(1, finalList.size());
        checkPatologiaIdPaciente(other, finalList.get(0));
    }

    @Test
    public void loadAllByIdPaciente_returnsEmptyWhenNoMatch() {
        List<Patologia> result = dao.loadAllByIdPaciente(10);
        assertEquals(0, result.size());
    }

    @Test
    public void insert_throwsWhenDuplicatePacienteId() {
        int pacienteId = createPacienteInDB("PacienteDuplicado");

        Patologia p1 = new Patologia();
        try { p1.setIdPaciente(pacienteId); } catch (Exception ignored) {}

        Patologia p2 = new Patologia();
        try { p2.setIdPaciente(pacienteId); } catch (Exception ignored) {}

        // primeira inserção deve passar
        dao.insert(p1);

        // segunda inserção com mesmo id_paciente deve disparar SQLiteConstraintException
        try {
            dao.insert(p2);
            Assert.fail("Esperava SQLiteConstraintException ao inserir segunda patologia com mesmo idPaciente");
        } catch (android.database.sqlite.SQLiteConstraintException expected) {
            // comportamento esperado
        }
    }

}

