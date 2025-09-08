package com.example.worknutri.ui.popUp.pathology.viewPopUp;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PathologyViewPopUpTest {

    private PathologyViewPopUp patologiaDetailPopUp;
    private Context context;
    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
        patologiaDetailPopUp = new PathologyViewPopUp(LayoutInflater.from(context));
    }

    @Test
    public void verifyIfNewPatologiaDetailPopUpIsNotNull() {
        PathologyViewPopUp popUpFragment = new PathologyViewPopUp(LayoutInflater.from(context));
        Assert.assertNotNull(popUpFragment);
    }

    @Test
    public void verifyIfNewPatologiaDetailPopUpSetTheTitleCorrectly() {
        PathologyViewPopUp popUpFragment = new PathologyViewPopUp(LayoutInflater.from(context));
        Assert.assertNotNull(popUpFragment);
        ViewGroup viewGroup = popUpFragment.getViewGroup();
        TextView title = viewGroup.findViewById(R.id.popup_base_layout_title_textview_header);
        Assert.assertEquals(ContextCompat.getString(context, R.string.patologia_title), title.getText().toString());
    }
    @Test
    public void verifyIfPatologiaDetailPopUpExtendsFromPopUpFragment() {
        Class<?> superclass = patologiaDetailPopUp.getClass().getSuperclass();
        Assert.assertNotNull(superclass);
        Assert.assertEquals(PopUpFragment.class, superclass);

    }

    @Test
    public void verifyIfNewPatologiaDetailPopUpInflateOnlyThePopupBaseLayout() {
        PathologyViewPopUp popUpFragment = new PathologyViewPopUp(LayoutInflater.from(context));
        ViewGroup viewGroup = popUpFragment.getViewGroup();
        Assert.assertNotNull(viewGroup);
        Assert.assertEquals(R.id.popup_base_layout,viewGroup.getId());
        ViewGroup layoutWereInsert = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(0,layoutWereInsert.getChildCount());
    }

    @Test
    public void verifyIfRemoveMarginBottomWorksCorrectly() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        View internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);

        // Verifica a margem inicial
        int initialBottomMargin = ((ViewGroup.MarginLayoutParams) internLayout.getLayoutParams()).bottomMargin;
        Assert.assertTrue(initialBottomMargin > 0);
        // Remove a margem inferior
        patologiaDetailPopUp.removeMarginBottom();
        // Verifica a margem atualizada
        int updatedBottomMargin = ((ViewGroup.MarginLayoutParams) internLayout.getLayoutParams()).bottomMargin;
        Assert.assertEquals(0, updatedBottomMargin);
    }


    @Test
    public void verifyIfgenerateViewReturnsOneViewgroup() {
        ViewGroup view = patologiaDetailPopUp.generateView("Test Title", "Test Description");
        Assert.assertNotNull(view);
    }

    @Test
    public void verifyIfGenerateViewWorksCorrectly() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        ViewGroup internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);

        // Verifica o número inicial de filhos
        int initialChildCount = internLayout.getChildCount();
        Assert.assertEquals(0, initialChildCount);

        // Gera uma nova view
        patologiaDetailPopUp.generateView("Test Title", "Test Description");

        // Verifica se um novo filho foi adicionado
        int updatedChildCount = internLayout.getChildCount();
        Assert.assertEquals(1, updatedChildCount);

        // Verifica se o layout correto foi inflado
        Assert.assertEquals(R.id.popup_patologia_descrition,internLayout.getChildAt(0).getId());

        // Verifica se o título e a descrição foram definidos corretamente
        View childView = internLayout.getChildAt(0);
        Assert.assertNotNull(childView);
        View titleView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
        View descriptionView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        Assert.assertNotNull(titleView);
        Assert.assertNotNull(descriptionView);
    }

    @Test
    public void verifyIfInCallGenerateViewWithBlankStringInDescritionInsertNaoInformado() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        ViewGroup internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);
        // Gera uma nova view com descrição em branco
        patologiaDetailPopUp.generateView("Test Title", "   ");
        // Verifica se um novo filho foi adicionado
        int updatedChildCount = internLayout.getChildCount();
        Assert.assertEquals(1, updatedChildCount);

        // Verifica se o título e a descrição foram definidos corretamente
        View childView =  internLayout.getChildAt(0);
        Assert.assertNotNull(childView);
        View titleView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
        View descriptionView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        Assert.assertNotNull(titleView);
        Assert.assertNotNull(descriptionView);
        Assert.assertEquals("Não Informado", ((android.widget.TextView) descriptionView).getText().toString());

    }

    @Test
    public void verifyIfInCallGenerateViewWithNullInDescritionInsertNaoInformado() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        ViewGroup internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);
        // Gera uma nova view com descrição nula
        patologiaDetailPopUp.generateView("Test Title", null);
        // Verifica se um novo filho foi adicionado
        int updatedChildCount = internLayout.getChildCount();
        Assert.assertEquals(1, updatedChildCount);

        // Verifica se o título foi definido corretamente
        View childView =  internLayout.getChildAt(0);
        Assert.assertNotNull(childView);
        View descriptionView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        Assert.assertNotNull(descriptionView);
        Assert.assertEquals("Não Informado", ((android.widget.TextView) descriptionView).getText().toString());

    }


    @Test
    public void verifyIfSetTextInflateCorrectlyNumberOfElements() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        ViewGroup internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);

        int initialChildCount = internLayout.getChildCount();
        Assert.assertEquals(0, initialChildCount);

        Patologia patologia = getPatologia();

        patologiaDetailPopUp.setText(patologia);

        int updatedChildCount = internLayout.getChildCount();
        Assert.assertEquals(12, updatedChildCount);

    }

    /**Método que cria uma instância de Patologia com dados de teste*/
    @NonNull
    private static Patologia getPatologia() {
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

    @Test
    public void verifyIfSetTextInflateCorrectlyTheTitleAndDescriptionOfElements() {
        ViewGroup viewGroup = patologiaDetailPopUp.getViewGroup();
        ViewGroup internLayout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(internLayout);

        int initialChildCount = internLayout.getChildCount();
        Assert.assertEquals(0, initialChildCount);

        Patologia patologia = getPatologia();

        patologiaDetailPopUp.setText(patologia);

        int updatedChildCount = internLayout.getChildCount();
        Assert.assertEquals(12, updatedChildCount);

        int i = 0;
        for (PathologyField type : PathologyField.values()) {
            String expectedTitle = type.getName();

            String expectedDescription = new PathologyFieldMapper(type).getValue(patologia);
            if (expectedDescription == null || expectedDescription.isBlank()) {
                expectedDescription = "Não Informado";
            }
            ViewGroup childView = (ViewGroup) internLayout.getChildAt(i);
            verifyViewIfCorrectly(expectedTitle, expectedDescription, childView);
            i++;
        }


    }

    private static void verifyViewIfCorrectly(String titleExpected,String descriptionExpected, ViewGroup childView) {
        Assert.assertNotNull(childView);
        Assert.assertEquals(R.id.popup_patologia_descrition, childView.getId());
        TextView titleView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
        View descriptionView = childView.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        Assert.assertNotNull(titleView);
        Assert.assertNotNull(descriptionView);
        Assert.assertEquals(titleExpected,  titleView.getText().toString());
        Assert.assertEquals(descriptionExpected, ((TextView) descriptionView).getText().toString());
    }

}
