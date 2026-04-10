package com.example.worknutri.support;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.worknutri.R;
import com.example.worknutri.calcular.AntropometricCalculator;
import com.example.worknutri.sqlLite.domain.clinica.Clinica;
import com.example.worknutri.sqlLite.domain.clinica.DayOfWork;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.sqlLite.domain.paciente.Paciente;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;

import java.util.Arrays;
import java.util.List;

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


    @NonNull
    public static Patologia generatePathology() {
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

    @NonNull
    public static Antropometria generateAnthropometry(String date, double height, double weight) {
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

    @NonNull
    public static Clinica generateClinic() {
        Clinica c = new Clinica();
        c.setNome("Nome Teste");
        c.setTelefone1("111222333");
        c.setEmail("teste@example.com");
        c.setRua("Rua A");
        c.setNumero(10);
        c.setComplemento("Apto 1");
        c.setCodigoPostal("12345-678");
        c.setBairro("Bairro");
        c.setCidade("Cidade");
        c.setEstado("RJ");
        return c;
    }

    @NonNull
    public static DayOfWork generateRandomicDayOfWork(long clinicId) {
        Context context = TestUtil.getThemedContext();
       String[] daysOfWeek = context.getResources().getStringArray(R.array.dias_semana);
       String[] hours = context.getResources().getStringArray(R.array.hours_total);
       int indexHourStart = (int) (Math.random() * (hours.length - 2));
       DayOfWork dayOfWork = new DayOfWork();
       dayOfWork.setIdClinica(clinicId);
       dayOfWork.setDayOfWeek(daysOfWeek[(int) (Math.random() * daysOfWeek.length)]);
       dayOfWork.setHoraInicio(hours[indexHourStart]);
       dayOfWork.setHoraFim(hours[indexHourStart +2]);
       return dayOfWork;

    }

    public static List<Paciente> generatePatientListToTest(){
        Paciente p1 = TestEntityFactory.generatePatient();
        p1.setId(1); p1.setNomePaciente("Paciente 1");

        Paciente p2 = TestEntityFactory.generatePatient();
        p2.setId(2); p2.setNomePaciente("Paciente 2");

        Paciente p3 = TestEntityFactory.generatePatient();
        p3.setId(3); p3.setNomePaciente("Paciente 3");
        return Arrays.asList(p1, p2, p3);
    }

    public static List<Antropometria> generateAnthropometryListToTest(List<Paciente> patients) {
        Antropometria a1 = TestEntityFactory.generateAnthropometry("15/08/1990", 1.70, 60);
        a1.setIdPaciente((int) patients.get(0).getId());
        Antropometria a2 = TestEntityFactory.generateAnthropometry("15/08/1990", 1.70, 80);
        a2.setIdPaciente((int) patients.get(1).getId());
        Antropometria a3 = TestEntityFactory.generateAnthropometry("15/08/1990", 1.70, 90);
        a3.setIdPaciente((int) patients.get(2).getId());
        return Arrays.asList(a1, a2, a3);
    }

    public static List<Clinica> generateClinicListToTest(){
        Clinica c1 = TestEntityFactory.generateClinic();
        c1.setId(1); c1.setBairro("Boa Viagem"); c1.setCidade("Recife"); c1.setNome("Alpha Clinic");
        Clinica c2 = TestEntityFactory.generateClinic();
        c2.setId(2); c2.setBairro("boa viagem"); c2.setCidade("Ramá"); c2.setNome("Beta Clinic");
        Clinica c3 = TestEntityFactory.generateClinic();
        c3.setId(3); c3.setBairro("Casa Amarela"); c3.setCidade("Olinda"); c3.setNome("Gamma Clinic");

        return Arrays.asList(c1, c2, c3);
    }

    public static List<DayOfWork> getDaysOfWork() {


        DayOfWork dayOfWork1 = new DayOfWork();
        dayOfWork1.setId(1);
        dayOfWork1.setDayOfWeek("SEGUNDA");
        dayOfWork1.setHoraInicio("08:00");
        dayOfWork1.setHoraFim("12:00");

        DayOfWork dayOfWork2 = new DayOfWork();
        dayOfWork2.setId(2);
        dayOfWork2.setDayOfWeek("SEGUNDA");
        dayOfWork2.setHoraInicio("13:00");
        dayOfWork2.setHoraFim("17:00");

        DayOfWork dayOfWork3 = new DayOfWork();
        dayOfWork3.setId(3);
        dayOfWork3.setDayOfWeek("TERÇA");
        dayOfWork3.setHoraInicio("09:00");
        dayOfWork3.setHoraFim("14:00");

        return List.of(dayOfWork1, dayOfWork2, dayOfWork3);
    }

}