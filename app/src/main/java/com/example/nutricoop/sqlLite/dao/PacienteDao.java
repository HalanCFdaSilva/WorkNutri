package com.example.nutricoop.sqlLite.dao;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.nutricoop.sqlLite.domain.DietasEPaciente;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import java.util.List;

import kotlinx.coroutines.flow.Flow;

@Dao
public interface PacienteDao {

    @Query("SELECT * FROM paciente")
    List<Paciente> getAll();

    @Query("SELECT * FROM paciente ORDER BY nome_paciente ASC")
    List<Paciente> getAllInOrder();

    @Query("SELECT * FROM paciente ORDER BY nome_paciente ASC")
    LiveData<List<Paciente>> getAllInOrderF();

    @Query("SELECT * FROM paciente WHERE id IN (:pacienteIds)")
    List<Paciente> loadAllByIds(int[] pacienteIds);

    @Query("SELECT * FROM paciente WHERE nome_paciente LIKE :nomePaciente")
    List<Paciente> findByName(String nomePaciente);

    @Query("SELECT * FROM paciente WHERE cpf LIKE :cpfPaciente LIMIT 1")
    Paciente findByCpf(String cpfPaciente);

//    @Transaction
//    @Query("SELECT * FROM paciente WHERE cpf LIKE :cpfPaciente")
//    List<DietasEPaciente> findPacienteAndDietasByCpf(String cpfPaciente);

    @Insert
    void insertAll(Paciente... pacientes);

    @Delete
    void delete(Paciente paciente);
}
