package com.example.worknutri.sqlLite.domain;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "dietas")
public class Dietas {

    @PrimaryKey
    private int id;

    @ColumnInfo(name = "cpf_paciente")
    private String cpfPaciente;

    @ColumnInfo(name = "endereco_dieta")

    private String enderecoDieta;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCpfPaciente() {
        return cpfPaciente;
    }

    public void setCpfPaciente(String cpfPaciente) {
        this.cpfPaciente = cpfPaciente;
    }

    public String getEnderecoDieta() {
        return enderecoDieta;
    }

    public void setEnderecoDieta(String enderecoDieta) {
        this.enderecoDieta = enderecoDieta;
    }


}
