package com.example.worknutri.sqlLite.dao.paciente;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.worknutri.sqlLite.domain.paciente.Paciente;

import java.util.List;

@Dao
public interface PacienteDao {

    @Query("SELECT * FROM paciente")
    List<Paciente> getAll();

    @Query("SELECT * FROM paciente ORDER BY nome_paciente ASC")
    List<Paciente> getAllInOrder();

    @Query("SELECT * FROM paciente WHERE id IN (:pacienteId)")
    Paciente getById(long pacienteId);

    @Query("SELECT * FROM paciente WHERE nome_paciente LIKE :nomePaciente")
    List<Paciente> findByName(String nomePaciente);


//    @Transaction
//    @Query("SELECT * FROM paciente WHERE cpf LIKE :cpfPaciente")
//    List<DietasEPaciente> findPacienteAndDietasByCpf(String cpfPaciente);

    @Insert
    void insertAll(Paciente... pacientes);

    @Update
    void update(Paciente paciente);

    @Delete
    void delete(Paciente paciente);
}
