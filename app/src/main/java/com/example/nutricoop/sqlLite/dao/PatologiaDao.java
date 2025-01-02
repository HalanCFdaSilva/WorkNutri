package com.example.nutricoop.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;

import java.util.List;

@Dao
public interface PatologiaDao {
    @Query("SELECT * FROM patologia_paciente")
    List<Patologia> getAll();

    @Query("SELECT * FROM patologia_paciente WHERE id_paciente IN (:pacienteIds)")
    List<Patologia> loadAllByIds(int[] pacienteIds);
    @Insert
    void insertAll(Patologia... patologias);

    @Delete
    void delete(Patologia patologia);
}
