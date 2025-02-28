package com.example.worknutri.sqlLite.domain.paciente;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "patologia_paciente",
        foreignKeys = {@ForeignKey(
                entity = Paciente.class,
                parentColumns = {"id"},
                childColumns = {"id_paciente"},
                onDelete = CASCADE, onUpdate = CASCADE)})
public class Patologia {

    private int id;
    @ColumnInfo(name = "id_paciente")
    @PrimaryKey(autoGenerate = true)
    private int idPaciente;
    @ColumnInfo(name = "patologia_atual")
    private String patologiaAtual;
    private String Urina;
    private String Fezes;
    @ColumnInfo(name = "hora_sono")
    private String horaSono;
    private String medicacao;
    private String Suplemento;
    private String etilico;
    private String fumante;
    @ColumnInfo(name = "alergia_alimentar")
    private String alergiaAlimentar;
    @ColumnInfo(name = "consumo_agua")
    private String consumoAgua;
    private String acucar;
    @ColumnInfo(name = "atividade_fisica")
    private String atividadeFisica;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getPatologiaAtual() {
        return patologiaAtual;
    }

    public void setPatologiaAtual(String patologiaAtual) {
        this.patologiaAtual = patologiaAtual;
    }

    public String getUrina() {
        return Urina;
    }

    public void setUrina(String urina) {
        Urina = urina;
    }

    public String getFezes() {
        return Fezes;
    }

    public void setFezes(String fezes) {
        Fezes = fezes;
    }

    public String getHoraSono() {
        return horaSono;
    }

    public void setHoraSono(String horaSono) {
        this.horaSono = horaSono;
    }

    public String getMedicacao() {
        return medicacao;
    }

    public void setMedicacao(String medicacao) {
        this.medicacao = medicacao;
    }

    public String getSuplemento() {
        return Suplemento;
    }

    public void setSuplemento(String suplemento) {
        Suplemento = suplemento;
    }

    public String getEtilico() {
        return etilico;
    }

    public void setEtilico(String etilico) {
        this.etilico = etilico;
    }

    public String getFumante() {
        return fumante;
    }

    public void setFumante(String fumante) {
        this.fumante = fumante;
    }

    public String getAlergiaAlimentar() {
        return alergiaAlimentar;
    }

    public void setAlergiaAlimentar(String alergiaAlimentar) {
        this.alergiaAlimentar = alergiaAlimentar;
    }

    public String getConsumoAgua() {
        return consumoAgua;
    }

    public void setConsumoAgua(String consumoAgua) {
        this.consumoAgua = consumoAgua;
    }

    public String getAcucar() {
        return acucar;
    }

    public void setAcucar(String acucar) {
        this.acucar = acucar;
    }

    public String getAtividadeFisica() {
        return atividadeFisica;
    }

    public void setAtividadeFisica(String atividadeFisica) {
        this.atividadeFisica = atividadeFisica;
    }
}
