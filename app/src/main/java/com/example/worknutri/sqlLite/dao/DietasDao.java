package com.example.worknutri.sqlLite.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.worknutri.sqlLite.domain.Dietas;

import java.util.List;

@Dao
public interface DietasDao {

    @Query("SELECT * FROM dietas")
    List<Dietas> getAll();


    @Insert
    void insertAll(Dietas... dietasPacientes);

    @Delete
    void delete(Dietas dietas);
}
