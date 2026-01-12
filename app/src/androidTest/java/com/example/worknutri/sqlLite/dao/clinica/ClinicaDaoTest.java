package com.example.worknutri.sqlLite.dao.clinica;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.worknutri.sqlLite.database.AppDataBase;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class ClinicaDaoTest {

    private ClinicaDao dao;
    private AppDataBase dataBase;

    @Before
    public void setUp() {
        Context context = ApplicationProvider.getApplicationContext();
        dataBase = Room.inMemoryDatabaseBuilder(context, AppDataBase.class).build();
        dao = dataBase.clinicaDao();
    }

    @After
    public void tearDown() {
        if (dataBase != null && dataBase.isOpen()) {
            dataBase.close();
        }
    }

    @Test
    public void testInsertAllAddClinicaToDataBase() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica Teste");
        dao.insertAll(clinica);
        List<Clinica> clinicasInDb = dao.getAll();
        Assert.assertTrue(clinicasInDb.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica.getNome())));

    }

    @Test
    public void testInsertAllCanAddMoreThan1ClinicaToDataBase() {
        Clinica clinica1 = new Clinica();
        clinica1.setNome("Clinica Teste 1");

        Clinica clinica2 = new Clinica();
        clinica2.setNome("Clinica Teste 2");

        dao.insertAll(clinica1, clinica2);
        List<Clinica> clinicasInDb = dao.getAll();
        Assert.assertTrue(clinicasInDb.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica1.getNome())));
        Assert.assertTrue(clinicasInDb.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica2.getNome())));

    }

    @Test
    public void testInsertAllGenerateIdToClinicaInDataBase() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica Teste");
        Assert.assertEquals(0, clinica.getId());

        dao.insertAll(clinica);

        List<Clinica> clinicasInDb = dao.getAll();
        Clinica clinicaAfterDB = null;
        for (int i = 0; i < clinicasInDb.size(); i++) {
            if (clinicasInDb.get(i).getNome().equals(clinica.getNome())) {
                clinicaAfterDB = clinicasInDb.get(i);
            }
        }
        Assert.assertNotNull(clinicaAfterDB);
        Assert.assertTrue(clinicaAfterDB.getId() > 0);
    }

    @Test
    public void testAddAllSaveClinicaWithAllData() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica Teste");
        clinica.setEmail("teste@test.com");
        clinica.setTelefone1("123456789");
        clinica.setBairro("Bairro Teste");
        clinica.setCidade("cidade teste");
        clinica.setEstado("estado teste");
        clinica.setRua("rua teste");
        clinica.setNumero(22);
        clinica.setCodigoPostal("12345-678");
        clinica.setComplemento("complemento teste");
        dao.insertAll(clinica);

        List<Clinica> clinicasInDb = dao.getAll();
        Clinica clinicaAfterDB = null;
        for (int i = 0; i < clinicasInDb.size(); i++) {
            if (clinicasInDb.get(i).getNome().equals(clinica.getNome())) {
                clinicaAfterDB = clinicasInDb.get(i);
            }
        }
        Assert.assertNotNull(clinicaAfterDB);
        Assert.assertEquals(clinica.getNome(), clinicaAfterDB.getNome());
        Assert.assertEquals(clinica.getEmail(), clinicaAfterDB.getEmail());
        Assert.assertEquals(clinica.getTelefone1(), clinicaAfterDB.getTelefone1());
        Assert.assertEquals(clinica.getBairro(), clinicaAfterDB.getBairro());
        Assert.assertEquals(clinica.getCidade(), clinicaAfterDB.getCidade());
        Assert.assertEquals(clinica.getEstado(), clinicaAfterDB.getEstado());
        Assert.assertEquals(clinica.getRua(), clinicaAfterDB.getRua());
        Assert.assertEquals(clinica.getNumero(), clinicaAfterDB.getNumero());
        Assert.assertEquals(clinica.getCodigoPostal(), clinicaAfterDB.getCodigoPostal());
        Assert.assertEquals(clinica.getComplemento(), clinicaAfterDB.getComplemento());

    }


    @Test
    public void testUpdateModifyAllClinicaDataExceptId() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica Teste");
        clinica.setEmail("teste@test.com");
        clinica.setTelefone1("123456789");
        clinica.setBairro("Bairro Teste");
        clinica.setCidade("cidade teste");
        clinica.setEstado("estado teste");
        clinica.setRua("rua teste");
        clinica.setNumero(22);
        clinica.setCodigoPostal("12345-678");
        clinica.setComplemento("complemento teste");
        dao.insertAll(clinica);

        List<Clinica> clinicasInDb = dao.getAll();
        Assert.assertEquals(1, clinicasInDb.size());
        Clinica clinicaAfterDB = null;
        for (int i = 0; i < clinicasInDb.size(); i++) {
            if (clinicasInDb.get(i).getNome().equals(clinica.getNome())) {
                clinicaAfterDB = clinicasInDb.get(i);
            }
        }
        Assert.assertNotNull(clinicaAfterDB);
        Assert.assertEquals(clinica.getNome(), clinicaAfterDB.getNome());
        Assert.assertEquals(clinica.getEmail(), clinicaAfterDB.getEmail());
        Assert.assertEquals(clinica.getTelefone1(), clinicaAfterDB.getTelefone1());
        Assert.assertEquals(clinica.getBairro(), clinicaAfterDB.getBairro());
        Assert.assertEquals(clinica.getCidade(), clinicaAfterDB.getCidade());
        Assert.assertEquals(clinica.getEstado(), clinicaAfterDB.getEstado());
        Assert.assertEquals(clinica.getRua(), clinicaAfterDB.getRua());
        Assert.assertEquals(clinica.getNumero(), clinicaAfterDB.getNumero());
        Assert.assertEquals(clinica.getCodigoPostal(), clinicaAfterDB.getCodigoPostal());
        Assert.assertEquals(clinica.getComplemento(), clinicaAfterDB.getComplemento());

        clinicaAfterDB.setComplemento(clinicaAfterDB.getComplemento() + " modificado");
        clinicaAfterDB.setCodigoPostal("98765-432");
        clinicaAfterDB.setNumero(99);
        clinicaAfterDB.setRua("rua teste modificada");
        clinicaAfterDB.setEstado("estado teste modificado");
        clinicaAfterDB.setCidade("cidade teste modificada");
        clinicaAfterDB.setBairro("Bairro Teste Modificado");
        clinicaAfterDB.setTelefone1("987654321");
        clinicaAfterDB.setEmail("modificado@teste.com");
        clinicaAfterDB.setNome("Clinica Teste Modificada");
        dao.update(clinicaAfterDB);
        List<Clinica> clinicasInDbAfterUpdate = dao.getAll();
        Assert.assertEquals(1, clinicasInDbAfterUpdate.size());
        Clinica clinicaAfterUpdate = clinicasInDbAfterUpdate.get(0);
        Assert.assertEquals(clinicaAfterDB.getId(), clinicaAfterUpdate.getId());
        Assert.assertEquals(clinicaAfterDB.getNome(), clinicaAfterUpdate.getNome());
        Assert.assertEquals(clinicaAfterDB.getEmail(), clinicaAfterUpdate.getEmail());
        Assert.assertEquals(clinicaAfterDB.getTelefone1(), clinicaAfterUpdate.getTelefone1());
        Assert.assertEquals(clinicaAfterDB.getBairro(), clinicaAfterUpdate.getBairro());
        Assert.assertEquals(clinicaAfterDB.getCidade(), clinicaAfterUpdate.getCidade());
        Assert.assertEquals(clinicaAfterDB.getEstado(), clinicaAfterUpdate.getEstado());
        Assert.assertEquals(clinicaAfterDB.getRua(), clinicaAfterUpdate.getRua());
        Assert.assertEquals(clinicaAfterDB.getNumero(), clinicaAfterUpdate.getNumero());
        Assert.assertEquals(clinicaAfterDB.getCodigoPostal(), clinicaAfterUpdate.getCodigoPostal());
        Assert.assertEquals(clinicaAfterDB.getComplemento(), clinicaAfterUpdate.getComplemento());

    }

    @Test
    public void testUpdateDontModifyClinicaDataIfModifyId() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica Teste");
        clinica.setEmail("teste@test.com");
        clinica.setTelefone1("123456789");
        clinica.setBairro("Bairro Teste");
        clinica.setCidade("cidade teste");
        clinica.setEstado("estado teste");
        clinica.setRua("rua teste");
        clinica.setNumero(22);
        clinica.setCodigoPostal("12345-678");
        clinica.setComplemento("complemento teste");
        dao.insertAll(clinica);

        List<Clinica> clinicasInDb = dao.getAll();
        Assert.assertEquals(1, clinicasInDb.size());
        Clinica clinicaAfterDB = clinicasInDb.get(0);

        Assert.assertEquals(clinica.getNome(), clinicaAfterDB.getNome());
        Assert.assertEquals(clinica.getEmail(), clinicaAfterDB.getEmail());
        Assert.assertEquals(clinica.getTelefone1(), clinicaAfterDB.getTelefone1());
        Assert.assertEquals(clinica.getBairro(), clinicaAfterDB.getBairro());
        Assert.assertEquals(clinica.getCidade(), clinicaAfterDB.getCidade());
        Assert.assertEquals(clinica.getEstado(), clinicaAfterDB.getEstado());
        Assert.assertEquals(clinica.getRua(), clinicaAfterDB.getRua());
        Assert.assertEquals(clinica.getNumero(), clinicaAfterDB.getNumero());
        Assert.assertEquals(clinica.getCodigoPostal(), clinicaAfterDB.getCodigoPostal());
        Assert.assertEquals(clinica.getComplemento(), clinicaAfterDB.getComplemento());

        long oldId = clinicaAfterDB.getId();
        clinicaAfterDB.setId(2);
        clinicaAfterDB.setComplemento(clinicaAfterDB.getComplemento() + " modificado");
        clinicaAfterDB.setCodigoPostal("98765-432");
        clinicaAfterDB.setNumero(99);
        clinicaAfterDB.setRua("rua teste modificada");
        clinicaAfterDB.setEstado("estado teste modificado");
        clinicaAfterDB.setCidade("cidade teste modificada");
        clinicaAfterDB.setBairro("Bairro Teste Modificado");
        clinicaAfterDB.setTelefone1("987654321");
        clinicaAfterDB.setEmail("modificado@teste.com");
        clinicaAfterDB.setNome("Clinica Teste Modificada");
        dao.update(clinicaAfterDB);

        List<Clinica> clinicasInDbAfterUpdate = dao.getAll();
        Assert.assertEquals(1, clinicasInDbAfterUpdate.size());
        Clinica clinicaAfterUpdate = clinicasInDbAfterUpdate.get(0);
        Assert.assertNotEquals(clinicaAfterDB.getId(), clinicaAfterUpdate.getId());
        Assert.assertNotEquals(clinicaAfterDB.getNome(), clinicaAfterUpdate.getNome());
        Assert.assertNotEquals(clinicaAfterDB.getEmail(), clinicaAfterUpdate.getEmail());
        Assert.assertNotEquals(clinicaAfterDB.getTelefone1(), clinicaAfterUpdate.getTelefone1());
        Assert.assertNotEquals(clinicaAfterDB.getBairro(), clinicaAfterUpdate.getBairro());
        Assert.assertNotEquals(clinicaAfterDB.getCidade(), clinicaAfterUpdate.getCidade());
        Assert.assertNotEquals(clinicaAfterDB.getEstado(), clinicaAfterUpdate.getEstado());
        Assert.assertNotEquals(clinicaAfterDB.getRua(), clinicaAfterUpdate.getRua());
        Assert.assertNotEquals(clinicaAfterDB.getNumero(), clinicaAfterUpdate.getNumero());
        Assert.assertNotEquals(clinicaAfterDB.getCodigoPostal(), clinicaAfterUpdate.getCodigoPostal());
        Assert.assertNotEquals(clinicaAfterDB.getComplemento(), clinicaAfterUpdate.getComplemento());

        Assert.assertEquals(oldId, clinicaAfterUpdate.getId());
        Assert.assertEquals(clinica.getNome(), clinicaAfterUpdate.getNome());
        Assert.assertEquals(clinica.getEmail(), clinicaAfterUpdate.getEmail());
        Assert.assertEquals(clinica.getTelefone1(), clinicaAfterUpdate.getTelefone1());
        Assert.assertEquals(clinica.getBairro(), clinicaAfterUpdate.getBairro());
        Assert.assertEquals(clinica.getCidade(), clinicaAfterUpdate.getCidade());
        Assert.assertEquals(clinica.getEstado(), clinicaAfterUpdate.getEstado());
        Assert.assertEquals(clinica.getRua(), clinicaAfterUpdate.getRua());
        Assert.assertEquals(clinica.getNumero(), clinicaAfterUpdate.getNumero());
        Assert.assertEquals(clinica.getCodigoPostal(), clinicaAfterUpdate.getCodigoPostal());
        Assert.assertEquals(clinica.getComplemento(), clinicaAfterUpdate.getComplemento());

    }



    @Test
    public void testDeleteRemovesClinicaFromDataBase() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica To Be Deleted");
        dao.insertAll(clinica);

        List<Clinica> clinicasInDbBeforeDelete = dao.getAll();
        Assert.assertTrue(clinicasInDbBeforeDelete.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica.getNome())));

        dao.delete(clinicasInDbBeforeDelete.get(0));

        List<Clinica> clinicasInDbAfterDelete = dao.getAll();
        Assert.assertFalse(clinicasInDbAfterDelete.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica.getNome())));
    }

    @Test
    public void testDeleteDontRemovesClinicaFromDataBaseIfDontFindTupleWithIdEqual() {
        Clinica clinica = new Clinica();
        clinica.setNome("Clinica To Be Deleted");
        List<Clinica> clinicasInDbBeforeAll = dao.getAll();
        Assert.assertEquals(0, clinicasInDbBeforeAll.size());
        dao.insertAll(clinica);

        List<Clinica> clinicasInDbBeforeDelete = dao.getAll();
        Assert.assertEquals(1, clinicasInDbBeforeDelete.size());
        Assert.assertTrue(clinicasInDbBeforeDelete.stream().anyMatch(clinicaInDb -> clinicaInDb.getNome().equals(clinica.getNome())));
        Clinica clinicaAfterInsert = clinicasInDbBeforeDelete.get(0);
        long idBeforeModify = clinicaAfterInsert.getId();
        clinicaAfterInsert.setId(2);
        dao.delete(clinicaAfterInsert);

        List<Clinica> clinicasInDbAfterDelete = dao.getAll();
        Assert.assertEquals(1, clinicasInDbBeforeDelete.size());
        Clinica clinicaAfterDelete = clinicasInDbAfterDelete.get(0);
        Assert.assertEquals(idBeforeModify, clinicaAfterDelete.getId());
        Assert.assertEquals(clinicaAfterDelete.getNome(), clinica.getNome());
        Assert.assertEquals(clinicaAfterDelete.getNome(), clinicaAfterInsert.getNome());

    }



    @Test
    public void getAllInOrder_returnsClinicasSortedByName() {
        Clinica c1 = newClinica("Z Clinic");
        Clinica c2 = newClinica("A Clinic");
        Clinica c3 = newClinica("M Clinic");

        dao.insertAll(c1, c2, c3);

        List<Clinica> ordered = dao.getAllInOrder();
        assertEquals(3, ordered.size());
        assertEquals("A Clinic", ordered.get(0).getNome());
        assertEquals("M Clinic", ordered.get(1).getNome());
        assertEquals("Z Clinic", ordered.get(2).getNome());
    }


    @Test
    public void getById_returnsCorrectClinica() {
        Clinica c = newClinica("Unique Clinic");
        dao.insertAll(c);

        List<Clinica> all = dao.getAll();
        assertFalse(all.isEmpty());
        long id = all.get(0).getId();

        Clinica found = dao.getById(id);
        assertNotNull(found);
        assertEquals("Unique Clinic", found.getNome());
        assertEquals(id, found.getId());
    }

    @Test
    public void findByName_exactAndWildcardMatches() {
        Clinica c1 = newClinica("Alpha Clinic");
        Clinica c2 = newClinica("Beta Clinic");
        dao.insertAll(c1, c2);

        // exact match
        List<Clinica> exact = dao.findByName("Alpha Clinic");
        assertEquals(1, exact.size());
        assertEquals("Alpha Clinic", exact.get(0).getNome());

        // wildcard match (using SQL LIKE pattern)
        List<Clinica> wildcard = dao.findByName("%Clinic%");
        assertTrue(wildcard.size() >= 2);
    }


    @Test
    public void findIdByName_returnsIdOrZeroWhenNotFound() {
        Clinica c = newClinica("FindId Clinic");
        dao.insertAll(c);

        List<Clinica> all = dao.getAll();
        assertFalse(all.isEmpty());
        int expectedId = (int) all.get(0).getId();

        int foundId = dao.findIdByName("FindId Clinic");
        assertEquals(expectedId, foundId);

        int notFound = dao.findIdByName("Nonexistent Clinic");
        assertEquals(0, notFound);
    }

    private Clinica newClinica(String nome) {
        Clinica c = new Clinica();
        c.setNome(nome);
        c.setEmail(nome.toLowerCase().replace(" ", "") + "@test.com");
        c.setTelefone1("000000000");
        c.setRua("Rua Teste");
        c.setNumero(1);
        c.setComplemento("");
        c.setCodigoPostal("00000-000");
        c.setBairro("Bairro");
        c.setCidade("Cidade");
        c.setEstado("Estado");
        return c;
    }
}
