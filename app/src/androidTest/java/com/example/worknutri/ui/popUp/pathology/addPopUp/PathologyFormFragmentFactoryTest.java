package com.example.worknutri.ui.popUp.pathology.addPopUp;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.support.TestEntityFactory;
import com.example.worknutri.ui.popUp.pathology.PathologyField;
import com.example.worknutri.ui.popUp.pathology.PathologyFieldMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PathologyFormFragmentFactoryTest {

    private PathologyFormFragmentFactory pathologyFormFactory;
    private Patologia pathology;
    private Context context;

    @Before
    public void setUp() {
        pathology = TestEntityFactory.generatePatologia();
        pathologyFormFactory = new PathologyFormFragmentFactory(PathologyField.ETHYLIC, pathology);
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);

    }

    @Test
    public void testGenerateViewGroupInsertOneViewInLayoutReceivedOnParameter() {
        LinearLayout layout = new LinearLayout(context);
        Assert.assertEquals(0, layout.getChildCount());
        pathologyFormFactory.generateViewGroup(context, layout);
        Assert.assertEquals(1, layout.getChildCount());

    }

    @Test
    public void testGenerateViewGroupInflateCorrectlyView() {
        LinearLayout layout = new LinearLayout(context);
        pathologyFormFactory.generateViewGroup(context, layout);
        Assert.assertEquals(1, layout.getChildCount());
        View view = layout.getChildAt(0);
        Assert.assertEquals(R.id.pop_up_patologia_description_formulario, view.getId());

    }

    @Test
    public void testConfigureEditTextSetCorrectHintAndText() {
        LinearLayout layout = new LinearLayout(context);
        pathologyFormFactory.generateViewGroup(context, layout);
        String testMessage = "Test Message";
        pathologyFormFactory.configureEditText(testMessage);
        View view = layout.getChildAt(0);
        MultiAutoCompleteTextView viewDescription = view.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        Assert.assertEquals(R.id.pop_up_patologia_description_formulario_editText, viewDescription.getId());
        Assert.assertEquals(context.getText(PathologyField.ETHYLIC.getHint()), viewDescription.getHint().toString());
        Assert.assertEquals(testMessage,  viewDescription.getText().toString());
    }

    @Test
    public void testConfigureDeleteButtonRemovesViewAndAddsCategoryBack() {
        LinearLayout layout = new LinearLayout(context);
        pathologyFormFactory.generateViewGroup(context, layout);
        PathologyField category = PathologyField.ETHYLIC;
        java.util.List<PathologyField> categories = new java.util.ArrayList<>();
        categories.add(PathologyField.ACTUAL_PATHOLOGY); // Add a different category to ensure ETHYLIC is not present
        pathologyFormFactory.configureDeleteButton(layout, categories);

        // Simulate button click
        View view = layout.getChildAt(0);
        ImageButton deleteButton = view.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        deleteButton.performClick();

        // Verify the view is removed
        Assert.assertEquals(0, layout.getChildCount());

        // Verify the category is added back
        Assert.assertTrue(categories.contains(category));
    }

    @Test
    public void testConfigureDeleteButtonDoesNotAddCategoryIfAlreadyPresent() {
        LinearLayout layout = new LinearLayout(context);
        pathologyFormFactory.generateViewGroup(context, layout);
        PathologyField category = PathologyField.ETHYLIC;
        java.util.List<PathologyField> categories = new java.util.ArrayList<>();
        categories.add(category); // Add the same category to ensure it is already present
        pathologyFormFactory.configureDeleteButton(layout, categories);

        // Simulate button click
        View view = layout.getChildAt(0);
        ImageButton deleteButton = view.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        deleteButton.performClick();

        // Verify the view is removed
        Assert.assertEquals(0, layout.getChildCount());

        // Verify the category is not added again (size should remain 1)
        Assert.assertEquals(1, categories.size());
    }

    @Test
    public void testConfigureDeleteButtonClearsPathologyFieldValue() {
        LinearLayout layout = new LinearLayout(context);
        pathologyFormFactory.generateViewGroup(context, layout);
        PathologyField category = PathologyField.ETHYLIC;
        java.util.List<PathologyField> categories = new java.util.ArrayList<>();
        // Ensure the category is not present initially
        Assert.assertFalse(categories.contains(category));
        pathologyFormFactory.configureDeleteButton(layout, categories);

        // Simulate button click
        View view = layout.getChildAt(0);
        ImageButton deleteButton = view.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        deleteButton.performClick();

        // Verify the view is removed
        Assert.assertEquals(0, layout.getChildCount());
        // Verify the category is added back
        Assert.assertTrue(categories.contains(category));
        // Verify the pathology field value is cleared
        Assert.assertEquals("", new PathologyFieldMapper(category).getValue(pathology));
    }

    @Test
    public void verifyIfGenerateTwoViewsWithTheSameCategoryTheSecondOneIsNotAddedToTheLayout(){
        LinearLayout layout = new LinearLayout(context);
        Assert.assertEquals(0, layout.getChildCount());
        pathologyFormFactory.generateViewGroup(context, layout);
        Assert.assertEquals(1, layout.getChildCount());
        PathologyFormFragmentFactory secondFactory = new PathologyFormFragmentFactory(PathologyField.ETHYLIC, pathology);
        secondFactory.generateViewGroup(context, layout);
        Assert.assertEquals(1, layout.getChildCount());
    }

    @Test
    public void verifyIfGenerateTwoViewsWithDifferentCategoryTheSecondOneIsAddedToTheLayout(){
        LinearLayout layout = new LinearLayout(context);
        Assert.assertEquals(0, layout.getChildCount());
        pathologyFormFactory.generateViewGroup(context, layout);
        Assert.assertEquals(1, layout.getChildCount());
        PathologyFormFragmentFactory secondFactory = new PathologyFormFragmentFactory(PathologyField.SLUMBER, pathology);
        secondFactory.generateViewGroup(context, layout);
        Assert.assertEquals(2, layout.getChildCount());
    }



}
