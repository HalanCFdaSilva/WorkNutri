package com.example.worknutri.calcular;

import androidx.annotation.NonNull;

import com.example.worknutri.R;

public enum ClassificacaoImc {
    DEFICIT(R.color.yellow_alert, -1),
    NORMAL(R.color.green_alert, 0),
    SOBREPESO(R.color.yellow_alert, 1),
    OBESIDADE_LEVE(R.color.orange_alert, 2),
    OBESIDADE_MEDIA(R.color.red_alert, 3),
    OBESIDADE_MORBIDA(R.color.magenta_alert, 4);

    private final int color;
    private final int priority;

    ClassificacaoImc(int color, int priority) {
        this.color = color;
        this.priority = priority;
    }


    public int getColor() {
        return color;
    }

    public int getPriority() {
        return priority;
    }

    public static ClassificacaoImc tipoImc(double valor) {

        if (valor < 18.5) {
            return ClassificacaoImc.DEFICIT;
        }

        if (18.5 <= valor & valor < 25) {
            return ClassificacaoImc.NORMAL;
        }

        if (25 <= valor & valor < 30) {
            return ClassificacaoImc.SOBREPESO;
        }

        if (30 <= valor & valor < 35) {
            return ClassificacaoImc.OBESIDADE_LEVE;
        }

        if (35 <= valor & valor < 40) {
            return ClassificacaoImc.OBESIDADE_MEDIA;
        }

        if (valor >= 40) {
            return ClassificacaoImc.OBESIDADE_MORBIDA;
        }


        return null;
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString().replace("_", " ");
    }
}
