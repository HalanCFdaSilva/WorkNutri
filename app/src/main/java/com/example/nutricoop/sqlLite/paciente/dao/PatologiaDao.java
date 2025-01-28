package com.example.nutricoop.sqlLite.paciente.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nutricoop.sqlLite.paciente.domain.Patologia;

import java.util.List;

@Dao
public interface PatologiaDao {
    @Query("SELECT * FROM patologia_paciente")
    List<Patologia> getAll();

    @Query("SELECT * FROM patologia_paciente WHERE id_paciente IN (:pacienteIds)")
    List<Patologia> loadAllByIdPaciente(long pacienteIds);
    @Insert
    void insertAll(Patologia... patologias);

    @Delete
    void delete(Patologia patologia);


}
