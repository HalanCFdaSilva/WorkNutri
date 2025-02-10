package com.example.worknutri.sqlLite.domain.paciente;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "paciente")
public class Paciente implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "nome_paciente")

    private String nomePaciente;



    private int idade;

    private String telefone;

    private String email;

    private String nascimento;

    @ColumnInfo(name = "peso_ideal")
    private int pesoIdeal;

    private char genero;

    private long clinicaId;

    private String observacoes;

    public String getNomePaciente(){
        return this.nomePaciente;
    }
    public void setNomePaciente(String nomePaciente){
        this.nomePaciente = nomePaciente;
    }

    public int getIdade(){
        return this.idade;
    }
    public void setIdade(int idade){
        this.idade = idade;
    }



    public void setId(long id) {
        this.id = id;
    }
    public long getId(){
        return this.id;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public int getPesoIdeal() {
        return pesoIdeal;
    }

    public void setPesoIdeal(int pesoIdeal) {
        this.pesoIdeal = pesoIdeal;
    }

    public char getGenero() {
        return genero;
    }

    public void setGenero(char genero) {
        this.genero = genero;
    }

    public long getClinicaId() {
        return clinicaId;
    }

    public void setClinicaId(long clinicaId) {
        this.clinicaId = clinicaId;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
