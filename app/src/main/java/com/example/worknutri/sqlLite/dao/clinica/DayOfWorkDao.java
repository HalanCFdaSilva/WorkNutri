package com.example.worknutri.sqlLite.dao.clinica;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;

import java.util.List;

@Dao
public interface DayOfWorkDao {


    @Query("SELECT * FROM day_of_work ")
    List<DayOfWork> getAll();

    @Query("SELECT * FROM day_of_work WHERE id_clinica == (:clinicaId)")
    List<DayOfWork> getDaysforClinicaId(long clinicaId);

    @Query("UPDATE day_of_work SET day_of_week = :dayOfWeek" +
            " AND hora_inicio = :hourBegin " +
            "AND hora_fim = :hourEnd " +
            "WHERE id == :id")
    void updateDayOfWork(String dayOfWeek, String hourBegin, String hourEnd, long id);

    @Insert
    void insert(DayOfWork dayOfWork);

    @Delete
    void delete(DayOfWork dayOfWork);

}
