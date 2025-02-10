package com.example.worknutri.calcular;

public class Imc {

    private double resultadoImc;
    private ClassificacaoImc classificacao;
//    public void calcular(Paciente paciente){
//
//        double altura = paciente.getAltura();
//        double peso = paciente.getPeso();
//        this.resultadoImc = peso/altura*altura;
//        this.classificacao = ClassificacaoImc.tipoImc(this.getResultadoImc());
//
//    }

    public double getResultadoImc() {
        return resultadoImc;
    }

    public ClassificacaoImc getClassificacao() {
        return classificacao;
    }
}
