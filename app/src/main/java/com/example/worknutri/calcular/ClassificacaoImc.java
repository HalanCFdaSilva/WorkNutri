package com.example.worknutri.calcular;

import android.graphics.Color;

import com.example.worknutri.R;

public enum ClassificacaoImc {
    DEFICIT(R.color.yellow_alert),
    NORMAL(R.color.green_alert),
    SOBRE_PESO(R.color.yellow_alert),
    OBESIDADE_LEVE(R.color.orange_alert),
    OBESIDADE_MEDIA(R.color.red_alert),
    OBESIDADE_MORBIDA(R.color.magenta_alert);

    private int color;

    ClassificacaoImc(int color) {
        this.color = color;
    }


    public int getColor() {
        return color;
    }

    public static ClassificacaoImc tipoImc(double valor) {

        if (valor < 18.5) {
            return ClassificacaoImc.DEFICIT;
        }

        if (18.5 <= valor & valor <= 24.9) {
            return ClassificacaoImc.NORMAL;
        }

        if (25 <= valor & valor <= 29.9) {
            return ClassificacaoImc.SOBRE_PESO;
        }

        if (30 <= valor & valor <= 34.9) {
            return ClassificacaoImc.OBESIDADE_LEVE;
        }

        if (35 <= valor & valor <= 39.9) {
            return ClassificacaoImc.OBESIDADE_MEDIA;
        }

        if (valor >= 40) {
            return ClassificacaoImc.OBESIDADE_MORBIDA;
        }


        return null;
    }
}
