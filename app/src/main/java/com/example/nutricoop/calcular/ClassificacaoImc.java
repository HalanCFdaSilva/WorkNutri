package com.example.nutricoop.calcular;

public enum ClassificacaoImc {
    DEFICIT,
    NORMAL,
    SOBRE_PESO,
    OBESIDADE_LEVE,
    OBESIDADE_MEDIA,
    OBESIDADE_MORBIDA;

   public static ClassificacaoImc tipoImc(double valor){

       if (valor < 18.5){
           return ClassificacaoImc.DEFICIT;
       }

       if (18.5<= valor & valor <=24.9){
           return ClassificacaoImc.NORMAL;
       }

       if (25<= valor & valor <=29.9){
           return ClassificacaoImc.SOBRE_PESO;
       }

       if (30<= valor & valor <=34.9){
           return ClassificacaoImc.OBESIDADE_LEVE;
       }

       if (35<= valor & valor <=39.9){
           return ClassificacaoImc.OBESIDADE_MEDIA;
       }

       if ( valor >= 40){
           return ClassificacaoImc.OBESIDADE_MORBIDA;
       }


       return null;
   }
}
