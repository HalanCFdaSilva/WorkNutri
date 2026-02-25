package com.example.worknutri.ui.popUp.anthropometry;

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
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.support.TestUtil;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;
import com.google.android.material.divider.MaterialDivider;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AntropometriaDetailPopUpTest {

    private AntropometriaDetaillPopUp antropometriaDetaillPopUp;
    private Antropometria anthropometry;
    private Context context;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);

        antropometriaDetaillPopUp = new PopUpFactoryImpl(context).generateAntropometriaPopUp();

        anthropometry = TestEntityFactory.generateAntropometria("15/08/2000",1.70,68);


    }

    @Test
    public void GenerateSmallMethodInsertAPopUpAntropometriaDescriptionFormularioLayoutInLayoutIntern() {
        antropometriaDetaillPopUp.generateSmall(anthropometry);
        ViewGroup layout = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(1,layout.getChildCount());
        Assert.assertEquals(R.id.popup_form_antropometric_description,layout.getChildAt(0).getId());
    }

    @Test
    public void GenerateSmallMethodContainCorrectViews() {
        antropometriaDetaillPopUp.generateSmall(anthropometry);
        ViewGroup viewGroup = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_form_antropometric_description);
        Assert.assertEquals(13,viewGroup.getChildCount());

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_imc_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_imc,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.classificacao_imc_textview,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_metabolical_rate_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_metabolical_rate,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_energy_expend_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_energy_expend,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_thumb_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_thumb,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_venta_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_venta,TextView.class);

        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_water_title,TextView.class);
        verifyIfContainsElementInId(viewGroup,R.id.popup_form_antropometric_description_water,TextView.class);

    }

    private static void verifyIfContainsElementInId(ViewGroup viewGroup, int idElement, Class classExpected) {
        View view = viewGroup.findViewById(idElement);
        Assert.assertNotNull(view);
        Assert.assertEquals(classExpected, view.getClass());
    }

    @Test
    public void GenerateSmallMethodInsertAntropometriaDataCorrectely() {
        antropometriaDetaillPopUp.generateSmall(anthropometry);

        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getImc())),
                getStringInViewText(R.id.popup_form_antropometric_description_imc));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getTaxaMetabolica())),
                getStringInViewText(R.id.popup_form_antropometric_description_metabolical_rate));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getValorMetabolico())),
                getStringInViewText(R.id.popup_form_antropometric_description_energy_expend));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getRegraBolso())),
                getStringInViewText(R.id.popup_form_antropometric_description_thumb));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getVenta())),
                getStringInViewText(R.id.popup_form_antropometric_description_venta));
        Assert.assertEquals(anthropometry.getAgua(), getStringInViewText(R.id.popup_form_antropometric_description_water));

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
        antropometriaDetaillPopUp.generateComplete(anthropometry);
        ViewGroup layout = antropometriaDetaillPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(1,layout.getChildCount());
        Assert.assertEquals(R.id.popup_antropometria_description,layout.getChildAt(0).getId());
    }

    @Test
    public void GenerateCompleteMethodContainCorrectViews() {
        antropometriaDetaillPopUp.generateComplete(anthropometry);
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
        antropometriaDetaillPopUp.generateComplete(anthropometry);
        Assert.assertEquals(anthropometry.getCircumferenciaAbdomen(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_abdomen));
        Assert.assertEquals(anthropometry.getCircumferenciaBracoDir(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_braco));
        Assert.assertEquals(anthropometry.getCircumferenciaCoxaDir(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_coxa));
        Assert.assertEquals(anthropometry.getCircumferenciaCintura(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_cintura));
        Assert.assertEquals(anthropometry.getCircumferenciaQuadril(),getStringInViewText(R.id.paciente_descrition_antropometria_circum_quadril));

        Assert.assertEquals(anthropometry.getAltura(),getStringInViewText(R.id.paciente_descrition_antropometria_altura));
        Assert.assertEquals(anthropometry.getPeso(),getStringInViewText(R.id.paciente_descrition_antropometria_peso_atual));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getPesoIdeal())),
                getStringInViewText(R.id.paciente_descrition_antropometria_peso_ideal));

        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getTaxaMetabolica())),
                getStringInViewText(R.id.paciente_descrition_antropometria_taxa_metabolica));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getValorMetabolico())),
                getStringInViewText(R.id.paciente_descrition_antropometria_valor_metabolico));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getRegraBolso())),
                getStringInViewText(R.id.paciente_descrition_antropometria_bolso));
        Assert.assertEquals(TestUtil.getStringFromDoubleWith2dot(Double.parseDouble(anthropometry.getVenta())),
                getStringInViewText(R.id.paciente_descrition_antropometria_venta));
        Assert.assertEquals(anthropometry.getAgua(),getStringInViewText(R.id.paciente_descrition_antropometria_agua));

    }
    @Test
    public void generateCompleteMethodInsertTheTitleCorrectly() {
        antropometriaDetaillPopUp.generateComplete(anthropometry);
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
        anthropometry.setImc("22.0");
        ClassificacaoImc imcExpected = ClassificacaoImc.tipoImc(22.0);
        Assert.assertNotNull(imcExpected);
        antropometriaDetaillPopUp.generateComplete(anthropometry);
        verifyImcTypeTextView(imcExpected);


    }

    @Test
    public void verifyIfGenerateSmallMethodCreateCorrectImcTypeTextView(){
        anthropometry.setImc("45.0");
        ClassificacaoImc imcExpected = ClassificacaoImc.tipoImc(45.0);
        Assert.assertNotNull(imcExpected);
        antropometriaDetaillPopUp.generateComplete(anthropometry);
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
