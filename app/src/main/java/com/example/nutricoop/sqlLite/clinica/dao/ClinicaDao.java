package com.example.nutricoop.sqlLite.clinica.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nutricoop.sqlLite.clinica.domain.Clinica;

import java.util.List;

@Dao
public interface ClinicaDao {

    @Query("SELECT * FROM clinica")
    List<Clinica> getAll();

    @Query("SELECT * FROM clinica ORDER BY nome ASC")
    List<Clinica> getAllInOrder();

    @Query("SELECT * FROM clinica WHERE id IN (:clinicaIds)")
    List<Clinica> loadAllByIds(int[] clinicaIds);

    @Query("SELECT * FROM clinica WHERE nome LIKE :nomeClinica")
    List<Clinica> findByName(String nomeClinica);

    @Query("SELECT id FROM clinica WHERE nome LIKE :nomeClinica")
    int findIdByName(String nomeClinica);





    @Insert
    void insertAll(Clinica... clinicas);

    @Delete
    void delete(Clinica clinica);
}
