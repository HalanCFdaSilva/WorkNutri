package com.example.nutricoop.sqlLite.domain.paciente;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.nutricoop.calcular.CalculadorAntropometrico;

@Entity(tableName = "antropometrias",
        foreignKeys = {@ForeignKey(
                entity = Paciente.class,
                parentColumns = {"id"},
                childColumns = {"id_paciente"},
                onDelete = CASCADE, onUpdate = CASCADE)})
public class Antropometria{

    private int id;
    @ColumnInfo(name = "id_paciente")
        @PrimaryKey(autoGenerate = true)
        private int idPaciente;
    private String altura;

    private String peso;

    private String pesoDesejado;

    private int idade;

    private String imc;

    private int circumferenceBracoDir;
    private int circumferenceCoxaDir;
    private int circumferenciaCintura;
    private int circumferenciaAbdomen;
    private int circumferenciaQuadril;

    private String taxaMetabolica;
    private String valorMetabolico;

    private String regraBolso;
    private String venta;

    private String agua;


    public Antropometria(){

    }
    public Antropometria(String peso, String altura, Paciente paciente){
        this.peso = peso;
        this.altura = altura;
        this.idade = paciente.getIdade();
        double doubleAltura = Double.valueOf(altura);
        this.imc = CalculadorAntropometrico.generateImc(Double.valueOf(peso),doubleAltura);
        this.taxaMetabolica = CalculadorAntropometrico.generateTMB(Double.valueOf(peso),(int) doubleAltura*100,
                paciente.getGenero(),idade);


    }

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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public String getImc() {
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public int getCircumferenceBracoDir() {
        return circumferenceBracoDir;
    }

    public void setCircumferenceBracoDir(int circumferenceBracoDir) {
        this.circumferenceBracoDir = circumferenceBracoDir;
    }

    public int getCircumferenceCoxaDir() {
        return circumferenceCoxaDir;
    }

    public void setCircumferenceCoxaDir(int circumferenceCoxaDir) {
        this.circumferenceCoxaDir = circumferenceCoxaDir;
    }

    public int getCircumferenciaCintura() {
        return circumferenciaCintura;
    }

    public void setCircumferenciaCintura(int circumferenciaCintura) {
        this.circumferenciaCintura = circumferenciaCintura;
    }

    public int getCircumferenciaAbdomen() {
        return circumferenciaAbdomen;
    }

    public void setCircumferenciaAbdomen(int circumferenciaAbdomen) {
        this.circumferenciaAbdomen = circumferenciaAbdomen;
    }

    public int getCircumferenciaQuadril() {
        return circumferenciaQuadril;
    }

    public void setCircumferenciaQuadril(int circumferenciaQuadril) {
        this.circumferenciaQuadril = circumferenciaQuadril;
    }

    public String getTaxaMetabolica() {
        return taxaMetabolica;
    }

    public void setTaxaMetabolica(String taxaMetabolica) {
        this.taxaMetabolica = taxaMetabolica;
    }

    public String getValorMetabolico() {
        return valorMetabolico;
    }

    public void setValorMetabolico(String valorMetabolico) {
        this.valorMetabolico = valorMetabolico;
    }

    public String getRegraBolso() {
        return regraBolso;
    }

    public void setRegraBolso(String regraBolso) {
        this.regraBolso = regraBolso;
    }

    public String getVenta() {
        return venta;
    }

    public void setVenta(String venta) {
        this.venta = venta;
    }

    public String getAgua() {
        return agua;
    }

    public void setAgua(String agua) {
        this.agua = agua;
    }

    public String getAltura(){
        return this.altura;
    }
    public void setAltura(String altura){
        this.altura = altura;
    }

    public String getPeso(){
        return this.peso;
    }
    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPesoDesejado() {
        return pesoDesejado;
    }

    public void setPesoDesejado(String pesoDesejado) {
        this.pesoDesejado = pesoDesejado;
    }
}
