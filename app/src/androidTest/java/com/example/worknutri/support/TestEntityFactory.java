package com.example.worknutri.support;

import androidx.annotation.NonNull;

import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

public  abstract class TestEntityFactory {
    /**
     * Método que cria uma instância de Patologia com dados de teste
     */
    @NonNull
    public static Patologia generatePatologia() {
        Patologia patologia = new Patologia();
        patologia.setPatologiaAtual("Diabetes");
        patologia.setUrina("Normal");
        patologia.setFezes("Constipado");
        patologia.setHoraSono("8 horas");
        patologia.setMedicacao("Metformina");
        patologia.setSuplemento("Vitamina D");
        patologia.setEtilico("Não");
        patologia.setFumante("Não");
        patologia.setAlergiaAlimentar("Nenhuma");
        patologia.setConsumoAgua("2 litros");
        patologia.setAcucar("Baixo");
        patologia.setAtividadeFisica("Regular");
        return patologia;
    }

    public static Antropometria generateAntropometria() {
        Antropometria antropometria = new Antropometria();
        antropometria.setAltura("1.75");
        antropometria.setPeso("70.0");
        antropometria.setPesoIdeal("68.0");
        antropometria.setImc("22.86");
        antropometria.setCircumferenciaAbdomen("85.0");
        antropometria.setCircumferenciaCintura("90.0");
        antropometria.setCircumferenciaBracoDir("30.0");
        antropometria.setCircumferenciaCoxaDir("29.0");
        antropometria.setCircumferenciaQuadril("35.0");
        antropometria.setRegraBolso("2500");
        antropometria.setTaxaMetabolica("1500");
        antropometria.setValorMetabolico("2000");
        antropometria.setVenta("300");
        antropometria.setAgua("2000");
        return antropometria;
    }
}