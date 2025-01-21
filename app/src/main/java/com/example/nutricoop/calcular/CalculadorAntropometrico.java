package com.example.nutricoop.calcular;

public abstract class CalculadorAntropometrico {

    public static String generateImc(double peso, double altura){
        return String.valueOf(peso/(altura*altura));
    }

    public static String generateTMB(double peso, int altura,char genero, int idade){
        String tmb = null;
        if (genero == 'M'){
            tmb = String.valueOf(66 +(13.7*peso) + (5.0 * altura) -(6.8*idade) );
        }else if (genero == 'F'){
            tmb = String.valueOf(655 +(9.6*peso) + (1.8 * altura) -(4.7*idade) );
        }
        return tmb;
    }

    public static String generateGET(String taxaMetabolicaBasal,int nivelAtividadePaciente, char genero){
        double tmb = Double.valueOf(taxaMetabolicaBasal);
        if (genero == 'M'){
            switch (nivelAtividadePaciente){
                case 2: return String.valueOf(tmb*1.78);
                case 3: return String.valueOf(tmb*2.1);
                default:return String.valueOf(tmb*1.55);
            }
        } else if (genero == 'F') {
            switch (nivelAtividadePaciente){
                case 2: return String.valueOf(tmb*1.64);
                case 3: return String.valueOf(tmb*1.82);
                default:return String.valueOf(tmb*1.56);
            }
        }
        return null;

    }

    public static String generateBolso(double pesoAtual, double pesoIdeal){
        if (pesoIdeal< pesoAtual) return String.valueOf(pesoAtual*20);
        else if (pesoIdeal > pesoAtual) return String.valueOf(pesoAtual * 30);
        return "0";
    }

    public static double generateVenta(double gastoEnergetico, double kgAperder){
        return (gastoEnergetico - 256.6666666666667*kgAperder);
    }

    public static double generateAgua(double pesoAtual,int idade){

        if (idade <= 17) return 40*pesoAtual;
        else if (idade <= 55) return 35*pesoAtual;
        else if (idade <= 65) return 30*pesoAtual;
        else  return 25*pesoAtual;


    }
}
