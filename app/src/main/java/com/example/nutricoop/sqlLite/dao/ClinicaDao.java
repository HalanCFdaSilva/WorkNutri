package com.example.nutricoop.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;

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





    @Insert
    void insertAll(Clinica... clinicas);

    @Delete
    void delete(Clinica clinica);
}
