package com.example.nutricoop.sqlLite.clinica.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.nutricoop.sqlLite.clinica.domain.DayOfWork;

import java.util.List;

@Dao
public interface DayOfWorkDao {


    @Query("SELECT * FROM day_of_work ")
    List<DayOfWork> getAll();
    @Query("SELECT * FROM day_of_work WHERE id_clinica == (:clinicaId)")
    List<DayOfWork> getDaysforClinicaId(long clinicaId);

    @Insert
    void insert(DayOfWork dayOfWork);

    @Delete
    void delete(DayOfWork dayOfWork);

    @Update
    void update(DayOfWork dayOfWork);
}
