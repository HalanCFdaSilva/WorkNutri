package com.example.worknutri.sqlLite.dao.clinica;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worknutri.sqlLite.domain.clinica.Clinica;

import java.util.List;

@Dao
public interface ClinicaDao {

    @Query("SELECT * FROM clinica")
    List<Clinica> getAll();

    @Query("SELECT * FROM clinica ORDER BY nome ASC")
    List<Clinica> getAllInOrder();

    @Query("SELECT * FROM clinica WHERE id IN (:clinicaId)")
    Clinica getById(long clinicaId);

    @Query("SELECT * FROM clinica WHERE nome LIKE :nomeClinica")
    List<Clinica> findByName(String nomeClinica);

    @Query("SELECT id FROM clinica WHERE nome LIKE :nomeClinica")
    int findIdByName(String nomeClinica);


    @Update
    void update(Clinica clinica);

    @Insert
    void insertAll(Clinica... clinicas);

    @Delete
    void delete(Clinica clinica);
}
