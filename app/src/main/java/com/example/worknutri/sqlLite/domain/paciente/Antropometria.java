package com.example.worknutri.sqlLite.domain.paciente;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

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

    @ColumnInfo(name = "peso_ideal")
    private String pesoIdeal;

    private int idade;

    private String imc;

    @ColumnInfo(name = "circum_braco")
    private String circumferenciaBracoDir;
    @ColumnInfo(name = "circum_coxa")
    private String circumferenciaCoxaDir;
    @ColumnInfo(name = "circum_cintura")
    private String circumferenciaCintura;
    @ColumnInfo(name = "circum_abdomen")
    private String circumferenciaAbdomen;
    @ColumnInfo(name = "circum_quadril")
    private String circumferenciaQuadril;


    @ColumnInfo(name = "taxa_metabolica")
    private String taxaMetabolica;

    @ColumnInfo(name = "valor_metabolico")
    private String valorMetabolico;

    @ColumnInfo(name = "regra_bolso")
    private String regraBolso;
    private String venta;

    private String agua;



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

    public String getCircumferenciaBracoDir() {
        return circumferenciaBracoDir;
    }

    public void setCircumferenciaBracoDir(String circumferenciaBracoDir) {
        this.circumferenciaBracoDir = circumferenciaBracoDir;
    }

    public String getCircumferenciaCoxaDir() {
        return circumferenciaCoxaDir;
    }

    public void setCircumferenciaCoxaDir(String circumferenciaCoxaDir) {
        this.circumferenciaCoxaDir = circumferenciaCoxaDir;
    }

    public String getCircumferenciaCintura() {
        return circumferenciaCintura;
    }

    public void setCircumferenciaCintura(String circumferenciaCintura) {
        this.circumferenciaCintura = circumferenciaCintura;
    }

    public String getCircumferenciaAbdomen() {
        return circumferenciaAbdomen;
    }

    public void setCircumferenciaAbdomen(String circumferenciaAbdomen) {
        this.circumferenciaAbdomen = circumferenciaAbdomen;
    }

    public String getCircumferenciaQuadril() {
        return circumferenciaQuadril;
    }

    public void setCircumferenciaQuadril(String circumferenciaQuadril) {
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

    public String getPesoIdeal() {
        return pesoIdeal;
    }

    public void setPesoIdeal(String pesoIdeal) {
        this.pesoIdeal = pesoIdeal;
    }
}
