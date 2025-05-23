package com.example.worknutri.sqlLite.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.worknutri.sqlLite.dao.clinica.ClinicaDao;
import com.example.worknutri.sqlLite.dao.clinica.DayOfWorkDao;
import com.example.worknutri.sqlLite.dao.paciente.AntropometriaDao;
import com.example.worknutri.sqlLite.dao.paciente.PacienteDao;
import com.example.worknutri.sqlLite.dao.paciente.PatologiaDao;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;


/**
 * Classe que cria e faz a manutenção do banco de dados.
 * Herda de RoomDatabase uma classe do Room a qual guarda todas as informações necessariás para o trabalho dessa classe.
 * A AppDataBase não pode ser chamada ou instância, servindo apenas para configurar o banco de dados e suas tabelas
 * conforme o desejado.
 *
 * @author Halan Silva
 * @see RoomDatabase
 */
@Database(entities = {Paciente.class, Patologia.class, Antropometria.class, Clinica.class, DayOfWork.class},
        exportSchema = true, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    /**
     * Constante que guarda o nome que deve ser chamado o banco de dados.
     * Utilizado tanto na criação, quanto quando quiser chamar o banco de dados.
     */
    private static final String DATA_BASE_NAME = "nutri_bank.db";

    /**
     * Método necessário para quando você quiser gerar uma instância do banco de dados e assim poder acessa-lo.
     */
    public static AppDataBase getInstance(Context context) {

        return Room.databaseBuilder(context, AppDataBase.class, DATA_BASE_NAME)
                .createFromAsset("dataBase/nutri_bank.db")
                .allowMainThreadQueries()
                .build();

    }

    /**
     * Assinatura de método que será usado pelo Room para gerar uma instância da interface PacienteDao
     * a qual é usada para fazer a interação com o banco de dados.
     *
     * @return Retorna uma instância da PacienteDao que é utilizada para fazer a interação com a tabela
     * referente a classe paciente no banco de dados.
     * @see PacienteDao
     * @see Paciente
     */
    public abstract PacienteDao pacienteDao();

    /**
     * Assinatura de método que será usado pelo Room para gerar uma instância da interface PatologiaDao
     * a qual é usada para fazer a interação com o banco de dados.
     *
     * @return Retorna uma instância da PatologiaDao que é utilizada para fazer a interação com a tabela
     * referente a classe patologia no banco de dados.
     * @see PatologiaDao
     * @see Patologia
     */
    public abstract PatologiaDao patologiaDao();

    /**
     * Assinatura de método que será usado pelo Room para gerar uma instância da interface AntropometriaDao
     * a qual é usada para fazer a interação com o banco de dados.
     *
     * @return Retorna uma instância da AntropometriaDao que é utilizada para fazer a interação com a tabela
     * referente a classe antropometria no banco de dados.
     * @see AntropometriaDao
     * @see Antropometria
     */
    public abstract AntropometriaDao antropometriaDao();

    /**
     * Assinatura de método que será usado pelo Room para gerar uma instância da interface ClinicaDao
     * a qual é usada para fazer a interação com o banco de dados.
     *
     * @return Retorna uma instância da ClinicaDao que é utilizada para fazer a interação com a tabela
     * referente a classe Clinica no banco de dados.
     * @see ClinicaDao
     * @see Clinica
     */
    public abstract ClinicaDao clinicaDao();

    /**
     * Assinatura de método que será usado pelo Room para gerar uma instância da interface DayOfWorkDao
     * a qual é usada para fazer a interação com o banco de dados.
     *
     * @return Retorna uma instância da DayOfWorkDao que é utilizada para fazer a interação com a tabela
     * referente a classe dayOfWork no banco de dados.
     * @see DayOfWorkDao
     * @see DayOfWork
     */
    public abstract DayOfWorkDao dayOfWorkDao();
}
