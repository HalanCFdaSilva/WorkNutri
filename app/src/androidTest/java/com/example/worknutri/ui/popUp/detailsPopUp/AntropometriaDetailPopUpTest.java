package com.example.worknutri.ui.popUp.detailsPopUp;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.calcular.ClassificacaoImc;
import com.example.worknutri.sqlLite.domain.paciente.Antropometria;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.google.android.material.divider.MaterialDivider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AntropometriaDetailPopUpTest {

    private AntropometriaDetaillPopUp antropometriaDetaillPopUp;
    private Antropometria antropometria;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);

        antropometriaDetaillPopUp = new PopUpFactoryImpl(context).generateAntropometriaPopUp();

        antropometria = new Antropometria();
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


    }

    @Test
    public void GenerateSmallMethodInsertAPopUpAntropometriaDescriptionFormularioLayoutInLayoutIntern() {
        antropometriaDetaillPopUp.generateSmall(antropometria);
        ViewGroup layout = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(1,layout.getChildCount());
        Assert.assertEquals(R.id.popup_antropometria_description_small,layout.getChildAt(0).getId());
    }

    @Test
    public void GenerateSmallMethodContainCorrectViews() {
        antropometriaDetaillPopUp.generateSmall(antropometria);
        ViewGroup viewGroup = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_antropometria_description_small);
        Assert.assertEquals(13,viewGroup.getChildCount());

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_imc_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_imc,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.classificacao_imc_textview,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_taxa_metabolica_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_taxa_metabolica,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_valor_metabolico_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_valor_metabolico,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_bolso_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_bolso,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_venta_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_venta,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_agua_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.antropometria_small_popup_agua,TextView.class);

    }

    private static void verifyIfContainsElementInId(ViewGroup viewGroup, int idElement, Class classExpected) {
        View view = viewGroup.findViewById(idElement);
        Assert.assertNotNull(view);
        Assert.assertEquals(classExpected, view.getClass());
    }

    @Test
    public void GenerateSmallMethodInsertAntropometriaDataCorrectely() {
        antropometriaDetaillPopUp.generateSmall(antropometria);

        Assert.assertEquals(antropometria.getImc(),getStringInViewText(R.id.antropometria_small_popup_imc));
        Assert.assertEquals(antropometria.getTaxaMetabolica(),getStringInViewText(R.id.antropometria_small_popup_taxa_metabolica));
        Assert.assertEquals(antropometria.getValorMetabolico(),getStringInViewText(R.id.antropometria_small_popup_valor_metabolico));
        Assert.assertEquals(antropometria.getRegraBolso(),getStringInViewText(R.id.antropometria_small_popup_bolso));
        Assert.assertEquals(antropometria.getVenta(),getStringInViewText(R.id.antropometria_small_popup_venta));
        Assert.assertEquals(antropometria.getAgua(),getStringInViewText(R.id.antropometria_small_popup_agua));

    }

    private String getStringInViewText(int id){
        TextView textView = antropometriaDetaillPopUp.getViewGroup().findViewById(id);
        String string = textView.getText().toString();
        if (string.contains(" "))
            return string.substring(0,string.indexOf(' '));
        return string;
    }

    @Test
    public void GenerateCompleteMethodInsertAPopUpAntropometriaDescriptionLayoutInLayoutIntern() {
        antropometriaDetaillPopUp.generateComplete(antropometria);
        ViewGroup layout = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(1,layout.getChildCount());
        Assert.assertEquals(R.id.popup_antropometria_description,layout.getChildAt(0).getId());
    }

    @Test
    public void GenerateCompleteMethodContainCorrectViews() {
        antropometriaDetaillPopUp.generateComplete(antropometria);
        ViewGroup viewGroup = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_antropometria_description);
        Assert.assertEquals(33,viewGroup.getChildCount());

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_braco_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_braco,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_abdomen_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_abdomen,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_quadril_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_quadril,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_cintura_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_cintura,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_coxa_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_circum_coxa,TextView.class);


        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_altura_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_altura,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_title_sortDivider, MaterialDivider.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_atual_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_atual,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_ideal_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_peso_ideal,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_calculos_antropometricos_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_calculos_antropometricos_title_sortDivider, MaterialDivider.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_imc_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_imc,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.classificacao_imc_textview,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_taxa_metabolica_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_taxa_metabolica,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_valor_metabolico_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_valor_metabolico,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_bolso_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_bolso,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_venta_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_venta,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_agua_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.paciente_descrition_antropometria_agua,TextView.class);

    }

    @Test
    public void GenerateCompleteMethodInsertAntropometriaDataCorrectely() {
        antropometriaDetaillPopUp.generateComplete(antropometria);
        Assert.assertEquals(antropometria.getCircumferenciaAbdomen(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_abdomen));
        Assert.assertEquals(antropometria.getCircumferenciaBracoDir(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_braco));
        Assert.assertEquals(antropometria.getCircumferenciaCoxaDir(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_coxa));
        Assert.assertEquals(antropometria.getCircumferenciaCintura(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_cintura));
        Assert.assertEquals(antropometria.getCircumferenciaQuadril(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_quadril));

        Assert.assertEquals(antropometria.getAltura(),getStringInViewText(R.id.paciente_descrition_antropometria_altura));
        Assert.assertEquals(antropometria.getPeso(),getStringInViewText(R.id.paciente_descrition_antropometria_peso_atual));
        Assert.assertEquals(antropometria.getPesoIdeal(),getStringInViewText(R.id.paciente_descrition_antropometria_peso_ideal));

        Assert.assertEquals(antropometria.getTaxaMetabolica(),getStringInViewText(R.id.paciente_descrition_antropometria_taxa_metabolica));
        Assert.assertEquals(antropometria.getValorMetabolico(),getStringInViewText(R.id.paciente_descrition_antropometria_valor_metabolico));
        Assert.assertEquals(antropometria.getRegraBolso(),getStringInViewText(R.id.paciente_descrition_antropometria_bolso));
        Assert.assertEquals(antropometria.getVenta(),getStringInViewText(R.id.paciente_descrition_antropometria_venta));
        Assert.assertEquals(antropometria.getAgua(),getStringInViewText(R.id.paciente_descrition_antropometria_agua));

    }
    @Test
    public void generateCompleteMethodInsertTheTitleCorrectly() {
        antropometriaDetaillPopUp.generateComplete(antropometria);
        TextView title = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_base_layout_title_textview_header);
        Assert.assertNotNull(title);
        Assert.assertEquals(title.getText().toString(), InstrumentationRegistry.getInstrumentation().getTargetContext().getString(R.string.antropometria_title));
    }

    @Test
    public void GetViewGroupMethodReturnTheCorrectViewGroup() {
        Assert.assertNotNull(antropometriaDetaillPopUp.getViewGroup());
        Assert.assertEquals(antropometriaDetaillPopUp.getViewGroup().getId(),R.id.popup_base_layout);
    }

    @Test
    public void verifyIfGenerateCompleteMethodCreateCorrectImcTypeTextView(){
        antropometria.setImc("22.0");
        ClassificacaoImc imcExpected = ClassificacaoImc.tipoImc(22.0);
        Assert.assertNotNull(imcExpected);
        antropometriaDetaillPopUp.generateComplete(antropometria);
        verifyImcTypeTextView(imcExpected);


    }

    @Test
    public void verifyIfGenerateSmallMethodCreateCorrectImcTypeTextView(){
        antropometria.setImc("45.0");
        ClassificacaoImc imcExpected = ClassificacaoImc.tipoImc(45.0);
        Assert.assertNotNull(imcExpected);
        antropometriaDetaillPopUp.generateComplete(antropometria);
        verifyImcTypeTextView(imcExpected);


    }


    private void verifyImcTypeTextView(ClassificacaoImc imcExpected) {
        TextView textView = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.classificacao_imc_textview);
        Assert.assertNotNull(textView);

        Drawable background = textView.getBackground();
        Assert.assertEquals(ContextCompat.getColor(context, imcExpected.getColor()),((ColorDrawable) background).getColor());

        Assert.assertEquals(imcExpected.toString(),textView.getText().toString());
        Assert.assertEquals(context.getText(R.string.imc_type_content).toString(),textView.getContentDescription());
        Assert.assertEquals(Typeface.DEFAULT_BOLD,textView.getTypeface());

        Assert.assertEquals(5, textView.getPaddingLeft());
        Assert.assertEquals(0, textView.getPaddingTop());
        Assert.assertEquals(5, textView.getPaddingRight());
        Assert.assertEquals(0, textView.getPaddingBottom());

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams)textView.getLayoutParams();
        Assert.assertEquals(R.id.paciente_descrition_antropometria_imc,layoutParams.topToTop);
        Assert.assertEquals(R.id.paciente_descrition_antropometria_imc,layoutParams.bottomToBottom);
        Assert.assertEquals(R.id.paciente_descrition_antropometria_imc,layoutParams.startToEnd);
        Assert.assertEquals(8,layoutParams.leftMargin);
        Assert.assertEquals(ConstraintLayout.LayoutParams.WRAP_CONTENT,layoutParams.width);
        Assert.assertEquals(ConstraintLayout.LayoutParams.WRAP_CONTENT,layoutParams.height);
    }


}
