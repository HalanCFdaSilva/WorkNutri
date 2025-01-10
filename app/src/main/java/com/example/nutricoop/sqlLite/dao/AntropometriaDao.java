package com.example.nutricoop.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;


import java.util.List;

@Dao
public interface AntropometriaDao {

    @Query("SELECT * FROM antropometrias")
    List<Antropometria> getAll();
//    @Query("SELECT peso,altura FROM antropometrias WHERE id_paciente IN (:pacienteIds)")
//    List<Antropometria> loadMinimusByIdPaciente(int[] pacienteIds);
    @Query("SELECT * FROM antropometrias WHERE id_paciente IN (:pacienteIds)")
    List<Antropometria> loadAllByIdPaciente(long pacienteIds);
    @Insert
    void insertAll(Antropometria... antropometrias);

    @Delete
    void delete(Antropometria antropometria);
}
