package com.example.worknutri.calcular;

import static org.junit.Assert.assertEquals;

import com.example.worknutri.R;

import org.junit.Test;

public class ClassificacaoImcTest {

    @Test
    public void testTipoImc() {
        // Adicione aqui os testes para o método tipoImc
        // Exemplo:
         assertEquals(ClassificacaoImc.DEFICIT, ClassificacaoImc.tipoImc(17.0));
         assertEquals(ClassificacaoImc.NORMAL, ClassificacaoImc.tipoImc(22.0));
         assertEquals(ClassificacaoImc.SOBREPESO, ClassificacaoImc.tipoImc(27.0));
         assertEquals(ClassificacaoImc.OBESIDADE_LEVE, ClassificacaoImc.tipoImc(32.0));
         assertEquals(ClassificacaoImc.OBESIDADE_MEDIA, ClassificacaoImc.tipoImc(37.0));
         assertEquals(ClassificacaoImc.OBESIDADE_MORBIDA, ClassificacaoImc.tipoImc(42.0));
    }

    @Test
    public void testGetColor() {
        // Adicione aqui os testes para o método getColor
        // Exemplo:
         assertEquals(R.color.yellow_alert, ClassificacaoImc.DEFICIT.getColor());
         assertEquals(R.color.green_alert, ClassificacaoImc.NORMAL.getColor());
         assertEquals(R.color.yellow_alert, ClassificacaoImc.SOBREPESO.getColor());
         assertEquals(R.color.orange_alert, ClassificacaoImc.OBESIDADE_LEVE.getColor());
         assertEquals(R.color.red_alert, ClassificacaoImc.OBESIDADE_MEDIA.getColor());
         assertEquals(R.color.magenta_alert, ClassificacaoImc.OBESIDADE_MORBIDA.getColor());
    }

    @Test
    public void testGetPriority() {
        // Adicione aqui os testes para o método getPriority
        // Exemplo:
         assertEquals(-1, ClassificacaoImc.DEFICIT.getPriority());
         assertEquals(0, ClassificacaoImc.NORMAL.getPriority());
         assertEquals(1, ClassificacaoImc.SOBREPESO.getPriority());
         assertEquals(2, ClassificacaoImc.OBESIDADE_LEVE.getPriority());
         assertEquals(3, ClassificacaoImc.OBESIDADE_MEDIA.getPriority());
         assertEquals(4, ClassificacaoImc.OBESIDADE_MORBIDA.getPriority());
    }

    @Test
    public void testToString() {
        // Adicione aqui os testes para o método toString
        // Exemplo:
         assertEquals("DEFICIT", ClassificacaoImc.DEFICIT.toString());
         assertEquals("NORMAL", ClassificacaoImc.NORMAL.toString());
         assertEquals("SOBREPESO", ClassificacaoImc.SOBREPESO.toString());
         assertEquals("OBESIDADE LEVE", ClassificacaoImc.OBESIDADE_LEVE.toString());
         assertEquals("OBESIDADE MEDIA", ClassificacaoImc.OBESIDADE_MEDIA.toString());
         assertEquals("OBESIDADE MORBIDA", ClassificacaoImc.OBESIDADE_MORBIDA.toString());
    }

    @Test
    public void testTipoImcLimitToDeficit() {
        // Teste valores de limite para o método tipoImc
        for (double i =0;i<=18.6;i+=0.1) {
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 18.5)
                assertEquals(ClassificacaoImc.DEFICIT, tipo);
            else
                assertEquals(ClassificacaoImc.NORMAL, tipo);
        }

    }

    @Test
    public void testTipoImcLimitToNormal() {
        for (double i = 18.4F; i <= 25.1; i += 0.1) {
            System.out.println(i);
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 18.5)
                assertEquals(ClassificacaoImc.DEFICIT, tipo);
            else if (i<25)
                assertEquals(ClassificacaoImc.NORMAL, tipo);
            else
                assertEquals(ClassificacaoImc.SOBREPESO, tipo);
        }
    }

    @Test
    public void testTipoImcLimitToSobrePeso() {
        for (double i = 24.9F; i <= 30.1; i += 0.1) {
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 25)
                assertEquals(ClassificacaoImc.NORMAL, tipo);
            else if (i < 30)
                assertEquals(ClassificacaoImc.SOBREPESO, tipo);
            else
                assertEquals(ClassificacaoImc.OBESIDADE_LEVE, tipo);
        }
    }

    @Test
    public void testTipoImcLimitToObesidadeLeve() {
        for (double i = 29.9F; i <= 35.1; i += 0.1) {
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 30)
                assertEquals(ClassificacaoImc.SOBREPESO, tipo);
            else if (i < 35)
                assertEquals(ClassificacaoImc.OBESIDADE_LEVE, tipo);
            else
                assertEquals(ClassificacaoImc.OBESIDADE_MEDIA, tipo);
        }
    }

    @Test
    public void testTipoImcLimitToObesidadeMedia() {
        for (double i = 34.9F; i <= 40.1; i += 0.1) {
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 35)
                assertEquals(ClassificacaoImc.OBESIDADE_LEVE, tipo);
            else if (i < 40)
                assertEquals(ClassificacaoImc.OBESIDADE_MEDIA, tipo);
            else
                assertEquals(ClassificacaoImc.OBESIDADE_MORBIDA, tipo);
        }
    }

    @Test
    public void testTipoImcLimitToObesidadeMorbida() {
        for (double i = 39.9F; i <= 100; i += 0.1) {
            ClassificacaoImc tipo = ClassificacaoImc.tipoImc(i);
            if (i < 40)
                assertEquals(ClassificacaoImc.OBESIDADE_MEDIA, tipo);
            else
                assertEquals(ClassificacaoImc.OBESIDADE_MORBIDA, tipo);

        }
    }
}
