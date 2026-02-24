package com.example.worknutri.ui.popUp.pathology.addPopUp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.Matchers.anything;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.sqlLite.domain.paciente.Patologia;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.pathology.PathologyField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
public class AddPathologyPopUpTest {

    private AddPathologyPopUp addPathologyPopUp;
    private Context context;
    private ArrayList<PathologyField> pathologyFields;
    @Rule
    public ActivityScenarioRule<ActivityToTest> activityRule =
        new ActivityScenarioRule<>(ActivityToTest.class);


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
        pathologyFields = new ArrayList<>(List.of(PathologyField.values()));
        addPathologyPopUp = new AddPathologyPopUp(context, pathologyFields);
    }

    @Test
    public void verifyIfNewPathologyAddPopUpInflateTheLayout(){
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
    }

    @Test
    public void verifyIfViewGroupIsPopupBaseLayout(){
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        Assert.assertEquals(R.id.popup_base_layout,viewGroup.getId());
    }

    @Test
    public void verifyIfInflatePopUpPatologiaAddCorrectly(){
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        LinearLayout layout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(layout);
        Assert.assertEquals(1,layout.getChildCount());
        ViewGroup viewWereVerify = (ViewGroup) layout.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add,viewWereVerify.getId());
    }

    @Test
    public void verifyIfPopUpPatologiaAddLayoutCorrectly(){
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        ViewGroup viewToVerify = viewGroup.findViewById(R.id.popup_patologia_add);
        Assert.assertNotNull(viewToVerify);
        Assert.assertEquals(3,viewToVerify.getChildCount());
        View childToVerify = viewToVerify.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add_spinner,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof Spinner);

        childToVerify = viewToVerify.getChildAt(1);
        Assert.assertEquals(R.id.popup_patologia_add_multiAutoComplete,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof MultiAutoCompleteTextView);

        childToVerify = viewToVerify.getChildAt(2);
        Assert.assertEquals(R.id.popup_patologia_add_button,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof Button);
    }

    @Test
    public void verifyIfTitleIsInsertedCorrectly(){
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        View title = viewGroup.findViewById(R.id.popup_base_layout_title_textview_header);
        Assert.assertNotNull(title);
        Assert.assertEquals(View.VISIBLE,title.getVisibility());
        Assert.assertTrue(title instanceof TextView);
        TextView titleTextView = (TextView) title;
        Assert.assertEquals(context.getString(R.string.patologia_title),titleTextView.getText().toString());
    }

    @Test
    public void verifyIfConfigureLayoutInsertDataInSpinnerCorrectly(){

        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        addPathologyPopUp.configurePopUp(new LinearLayout(context),new Patologia());
        ViewGroup viewToVerify = viewGroup.findViewById(R.id.popup_patologia_add);
        Assert.assertNotNull(viewToVerify);
        Assert.assertEquals(3,viewToVerify.getChildCount());
        View childToVerify = viewToVerify.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add_spinner,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof Spinner);
        SpinnerAdapter spinnerAdapter = ((Spinner) childToVerify).getAdapter();
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            String item = (String) spinnerAdapter.getItem(i);
            Assert.assertTrue(pathologyFields.stream()
                    .anyMatch(pathologyField -> pathologyField.getUpperName().equals(item)));
        }
    }

    @Test
    public void verifyIfConfigureLayoutInsertHintInMultiAutoCompleteTextViewCorrectlyInSelectedItemOnSpinner(){

        addPathologyPopUp.configurePopUp(new LinearLayout(context),new Patologia());
        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        activityRule.getScenario().onActivity(activity -> {
            // Supondo que você já tenha uma instância de Dialog chamada dialog
            activity.showPopUp(addPathologyPopUp.getPopUpWindow());
        });

        //verify if layout is correct
        ViewGroup viewToVerify = viewGroup.findViewById(R.id.popup_patologia_add);
        Assert.assertNotNull(viewToVerify);
        Assert.assertEquals(3,viewToVerify.getChildCount());


        // Verify MultiAutoCompleteTextView
        View childToVerify = viewToVerify.getChildAt(1);
        Assert.assertEquals(R.id.popup_patologia_add_multiAutoComplete,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof MultiAutoCompleteTextView);
        MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView) childToVerify;

        // Verify Spinner
        childToVerify = viewToVerify.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add_spinner,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof Spinner);
        Spinner spinner = (Spinner) childToVerify;
        Assert.assertNotNull(spinner.getOnItemSelectedListener());



        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
            onData(anything()).atPosition(i).perform(click());
            InstrumentationRegistry.getInstrumentation().waitForIdleSync();
            String item = (String) spinner.getAdapter().getItem(i);
            PathologyField pathologyField = pathologyFields.stream()
                    .filter(pathology -> pathology.getUpperName().equals(item))
                    .findAny().orElse(null);
            Assert.assertNotNull(pathologyField);
            Assert.assertEquals(context.getText(pathologyField.getHint()),multiAutoCompleteTextView.getHint().toString());
        }
    }

    @Test
    public void verifyIfInClickButtonRemoveThePathologyTypeSelectedFromList(){
        LinearLayout layoutWhereAdd = new LinearLayout(context);
        addPathologyPopUp.configurePopUp(layoutWhereAdd, new Patologia());

        for (int i = 0; i < pathologyFields.size(); i++) {

            int expectedSize = pathologyFields.size()-1;
            PathologyField removed = pathologyFields.get(0);
            Assert.assertTrue(pathologyFields.contains(removed));

            simulateNewPathologyEntry(i);

            Assert.assertEquals(expectedSize, pathologyFields.size());
            Assert.assertFalse(pathologyFields.contains(removed));
        }
    }

    @Test
    public void verifyIfSpinnerIsDisabledOrEmptyWhenAllPathologiesAreRemoved() {
        addPathologyPopUp = new AddPathologyPopUp(context, new ArrayList<>(List.of(PathologyField.values())));
        LinearLayout layoutWhereAdd = new LinearLayout(context);
        addPathologyPopUp.configurePopUp(layoutWhereAdd, new Patologia());

        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Spinner spinner = viewGroup.findViewById(R.id.popup_patologia_add_spinner);

        int total = spinner.getAdapter().getCount();
        for (int i = 0; i < total; i++) {
            simulateNewPathologyEntry(i);
        }

        // Após remover todos, o adapter deve estar vazio
        Assert.assertEquals(0, spinner.getAdapter().getCount());
        // O spinner deve estar desabilitado
        Assert.assertFalse(spinner.isEnabled() || spinner.isClickable());
    }

    @Test
    public void verifyIfConfigureLayoutInsertDataInLinearLayoutWhenClickOnButton() {
        LinearLayout layoutWhereAdd = new LinearLayout(context);
        addPathologyPopUp.configurePopUp(layoutWhereAdd, new Patologia());

        ViewGroup viewGroup = addPathologyPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        View viewOfSpinner = viewGroup.findViewById(R.id.popup_patologia_add_spinner);
        Assert.assertNotNull(viewOfSpinner);
        Assert.assertTrue(viewOfSpinner instanceof Spinner);
        Spinner spinner = (Spinner) viewOfSpinner;


        for (int i = 0; i < spinner.getAdapter().getCount(); i++) {
            simulateNewPathologyEntry(i);
            Assert.assertEquals(i + 1, layoutWhereAdd.getChildCount());
            View addedView = layoutWhereAdd.getChildAt(i);
            Assert.assertNotNull(addedView);
            checkViewCreated(i, addedView);
            Assert.assertFalse(addPathologyPopUp.getPopUpWindow().isShowing());

        }
    }

    private void simulateNewPathologyEntry(int i) {
        activityRule.getScenario().onActivity(activity ->
                activity.showPopUp(addPathologyPopUp.getPopUpWindow()));
        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
        View editText = addPathologyPopUp.getViewGroup().findViewById(R.id.popup_patologia_add_multiAutoComplete);
        Assert.assertNotNull(editText);
        Assert.assertTrue(editText instanceof MultiAutoCompleteTextView);
        MultiAutoCompleteTextView multiAutoCompleteTextView = (MultiAutoCompleteTextView) editText;
        String messageToInsert = "Test Message " + i;
        multiAutoCompleteTextView.setText(messageToInsert);
        onView(withId(R.id.popup_patologia_add_button)).perform(click());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();
    }

    private void checkViewCreated(int position,View addedView) {
        PathologyField[] values = PathologyField.values();
        Assert.assertTrue(addedView instanceof ViewGroup);
        Assert.assertEquals(R.id.pop_up_patologia_description_formulario, addedView.getId());
        verifyifTitleOfViewCreatedIsCorrect(addedView.findViewById(R.id.pop_up_patologia_description_formulario_title), values[position]);

        View description = addedView.findViewById(R.id.pop_up_patologia_description_formulario_editText);
        verifyIfDescriptionIsCorrect(position, description, values);

        View deleteButton = addedView.findViewById(R.id.pop_up_patologia_description_formulario_button_delete);
        Assert.assertNotNull(deleteButton);
        Assert.assertTrue(deleteButton instanceof ImageButton);
        Assert.assertEquals(View.VISIBLE, deleteButton.getVisibility());
    }

    private static void verifyifTitleOfViewCreatedIsCorrect(View title, PathologyField values) {
        Assert.assertNotNull(title);
        Assert.assertTrue(title instanceof TextView);
        Assert.assertEquals(View.VISIBLE, title.getVisibility());
        Assert.assertEquals(values.getUpperName(), ((TextView) title).getText().toString());
    }

    private void verifyIfDescriptionIsCorrect(int position, View description, PathologyField[] values) {

        Assert.assertNotNull(description);
        Assert.assertTrue(description instanceof MultiAutoCompleteTextView);
        Assert.assertEquals(View.VISIBLE, description.getVisibility());
        String expectedMessage = "Test Message " + position;
        Assert.assertEquals(expectedMessage, ((EditText) description).getText().toString());
        Assert.assertEquals(context.getText(values[position].getHint()), ((MultiAutoCompleteTextView) description).getHint().toString());
    }

    @Test(expected = NullPointerException.class)
    public void verifyIfConfigurePopUpThrowsExceptionWhenPathologyIsNull() {
        addPathologyPopUp.configurePopUp(new LinearLayout(context), null);
    }

    @Test
    public void verifyIfPopUpIsDismissedAfterAddPathology() {
        LinearLayout layoutWhereAdd = new LinearLayout(context);
        addPathologyPopUp.configurePopUp(layoutWhereAdd, new Patologia());

        activityRule.getScenario().onActivity(activity ->
                activity.showPopUp(addPathologyPopUp.getPopUpWindow()));

        // Simula seleção e clique no botão
        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onData(anything()).atPosition(0).perform(click());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        onView(withId(R.id.popup_patologia_add_button)).perform(click());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Verifica se o popup foi fechado
        Assert.assertFalse(addPathologyPopUp.getPopUpWindow().isShowing());
    }

    @Test
    public void verifyIfPopUpStateIsMaintainedAfterRecreation() {
        LinearLayout layoutWhereAdd = new LinearLayout(context);
        addPathologyPopUp.configurePopUp(layoutWhereAdd, new Patologia());

        activityRule.getScenario().onActivity(activity ->
                activity.showPopUp(addPathologyPopUp.getPopUpWindow()));

        // Seleciona um item no spinner
        onView(withId(R.id.popup_patologia_add_spinner)).perform(click());
        onData(anything()).atPosition(1).perform(click());
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Digita um texto
        onView(withId(R.id.popup_patologia_add_multiAutoComplete)).perform(click());
        MultiAutoCompleteTextView multi = addPathologyPopUp.getViewGroup().findViewById(R.id.popup_patologia_add_multiAutoComplete);
        multi.setText("Texto de teste");

        // Recria a Activity (simula rotação)
        activityRule.getScenario().recreate();
        InstrumentationRegistry.getInstrumentation().waitForIdleSync();

        // Verifica se o popup ainda está visível
        Assert.assertTrue(addPathologyPopUp.getPopUpWindow().isShowing());

        // Verifica se o item selecionado e o texto foram mantidos
        Spinner spinner = addPathologyPopUp.getViewGroup().findViewById(R.id.popup_patologia_add_spinner);
        Assert.assertEquals(1, spinner.getSelectedItemPosition());
        MultiAutoCompleteTextView multiAfter = addPathologyPopUp.getViewGroup().findViewById(R.id.popup_patologia_add_multiAutoComplete);
        Assert.assertEquals("Texto de teste", multiAfter.getText().toString());
    }



}
