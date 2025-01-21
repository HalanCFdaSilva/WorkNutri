package com.example.nutricoop.sqlLite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.nutricoop.sqlLite.dao.AntropometriaDao;
import com.example.nutricoop.sqlLite.dao.ClinicaDao;
import com.example.nutricoop.sqlLite.dao.PacienteDao;
import com.example.nutricoop.sqlLite.dao.PatologiaDao;
import com.example.nutricoop.sqlLite.domain.clinica.Clinica;
import com.example.nutricoop.sqlLite.domain.paciente.Antropometria;
import com.example.nutricoop.sqlLite.domain.paciente.Paciente;
import com.example.nutricoop.sqlLite.domain.paciente.Patologia;

import java.io.Serializable;


/**Classe que cria e faz a manutenção do banco de dados.
 * Herda de RoomDatabase uma classe do Room a qual guarda todas as informações necessariás para o trabalho dessa classe.
 * A AppDataBase não pode ser chamada ou instância, servindo apenas para configurar o banco de dados e suas tabelas
 * conforme o desejado.
 * @author Halan Silva
 * @see RoomDatabase*/
@Database(entities = {Paciente.class, Patologia.class, Antropometria.class, Clinica.class},exportSchema = true ,version = 1 )
public abstract class AppDataBase extends RoomDatabase {

    /**Constante que guarda o nome que deve ser chamado o banco de dados.
     * Utilizado tanto na criação, quanto quando quiser chamar o banco de dados. */
    private static final String DATA_BASE_NAME= "nutri_bank.db";

    /**Método necessário para quando você quiser gerar uma instância do banco de dados e assim poder acessa-lo.*/
    public static AppDataBase getInstance(Context context) {

        return Room
                .databaseBuilder(context, AppDataBase.class, DATA_BASE_NAME)
                .allowMainThreadQueries()
                .build();

    }
    public abstract PacienteDao pacienteDao();
    public abstract PatologiaDao patologiaDao();
    public abstract AntropometriaDao antropometriaDao();
    public abstract ClinicaDao clinicaDao();
}
