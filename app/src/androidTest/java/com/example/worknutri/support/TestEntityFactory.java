package com.example.worknutri.support;

import androidx.annotation.NonNull;

import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

public  abstract class TestEntityFactory {

    @NonNull
    public static Paciente generatePatient() {
        Paciente paciente = new Paciente();
        paciente.setNomePaciente("João Intent Teste");
        paciente.setEmail("intent@exemplo.com");
        paciente.setTelefone("11999999999");
        paciente.setGenero('M');
        paciente.setClinicaId(1);
        paciente.setNascimento("15/08/1990");
        paciente.setObservacoes("teste de observações");
        return paciente;
    }

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

    public static Antropometria generateAntropometria(String date, double height, double weight) {
        Antropometria antropometria = new Antropometria();
        AntropometricCalculator calculator = new AntropometricCalculator(weight,height);
        antropometria.setAltura(String.valueOf(height));
        antropometria.setPeso(String.valueOf(weight));

        antropometria.setPesoIdeal(TestUtil
                .formatDoubleToString(AntropometricCalculator.getIdealWeight(height)));
        antropometria.setImc(TestUtil.formatDoubleToString(
                Double.parseDouble(calculator.generateImc())));

        antropometria.setCircumferenciaAbdomen("85.0");
        antropometria.setCircumferenciaCintura("90.0");
        antropometria.setCircumferenciaBracoDir("30.0");
        antropometria.setCircumferenciaCoxaDir("29.0");
        antropometria.setCircumferenciaQuadril("35.0");

        double bolso = Double.parseDouble(calculator.generateBolso(
                AntropometricCalculator.getIdealWeight(height)));
        antropometria.setRegraBolso(TestUtil.formatDoubleToString(bolso));

        int age = AntropometricCalculator.getYearFromDate(date);

        antropometria.setAgua(String.valueOf(calculator.generateAgua(age, weight)));

        double metabolicalCoast = Double.parseDouble(calculator.generateTMB('M', age));
        antropometria.setTaxaMetabolica(TestUtil.formatDoubleToString(metabolicalCoast));

        double energyExpend = Double.parseDouble(calculator.generateGET(metabolicalCoast, 1));
        antropometria.setValorMetabolico(TestUtil.formatDoubleToString(energyExpend));

        double ventalCoast = Double.parseDouble(calculator.generateVenta(energyExpend, 0));
        antropometria.setVenta(TestUtil.formatDoubleToString(ventalCoast));


        return antropometria;
    }
}