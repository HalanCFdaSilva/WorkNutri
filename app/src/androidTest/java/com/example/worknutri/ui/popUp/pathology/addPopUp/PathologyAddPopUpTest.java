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
import android.widget.LinearLayout;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.ui.ActivityToTest;
import com.example.worknutri.ui.popUp.pathology.PathologyField;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class PathologyAddPopUpTest {

    private PathologyAddPopUp pathologyAddPopUp;
    private Context context;
    @Rule
    public ActivityScenarioRule<ActivityToTest> activityRule =
        new ActivityScenarioRule<>(ActivityToTest.class);


    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
        pathologyAddPopUp = new PathologyAddPopUp(context, List.of(PathologyField.values()));
    }

    @Test
    public void verifyIfNewPathologyAddPopUpInflateTheLayout(){
        pathologyAddPopUp = new PathologyAddPopUp(context,List.of(PathologyField.values()));
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
    }

    @Test
    public void verifyIfViewGroupIsPopupBaseLayout(){
        pathologyAddPopUp = new PathologyAddPopUp(context,List.of(PathologyField.values()));
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        Assert.assertEquals(R.id.popup_base_layout,viewGroup.getId());
    }

    @Test
    public void verifyIfInflatePopUpPatologiaAddCorrectly(){
        pathologyAddPopUp = new PathologyAddPopUp(context,List.of(PathologyField.values()));
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        LinearLayout layout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertNotNull(layout);
        Assert.assertEquals(1,layout.getChildCount());
        ViewGroup viewWereVerify = (ViewGroup) layout.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add,viewWereVerify.getId());
    }

    @Test
    public void verifyIfPopUpPatologiaAddLayoutCorrectly(){
        pathologyAddPopUp = new PathologyAddPopUp(context,List.of(PathologyField.values()));
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
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
        pathologyAddPopUp = new PathologyAddPopUp(context,List.of(PathologyField.values()));
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
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
        List<PathologyField> listOfPathologiesToInsertInSpinner = List.of(PathologyField.values());
        pathologyAddPopUp = new PathologyAddPopUp(context, listOfPathologiesToInsertInSpinner);
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        pathologyAddPopUp.configurePopUp(new LinearLayout(context),null);
        ViewGroup viewToVerify = viewGroup.findViewById(R.id.popup_patologia_add);
        Assert.assertNotNull(viewToVerify);
        Assert.assertEquals(3,viewToVerify.getChildCount());
        View childToVerify = viewToVerify.getChildAt(0);
        Assert.assertEquals(R.id.popup_patologia_add_spinner,childToVerify.getId());
        Assert.assertTrue(childToVerify instanceof Spinner);
        SpinnerAdapter spinnerAdapter = ((Spinner) childToVerify).getAdapter();
        for (int i = 0; i < spinnerAdapter.getCount(); i++) {
            String item = (String) spinnerAdapter.getItem(i);
            Assert.assertTrue(listOfPathologiesToInsertInSpinner.stream()
                    .anyMatch(pathologyField -> pathologyField.getUpperName().equals(item)));
        }
    }

    @Test
    public void verifyIfConfigureLayoutInsertHintInMultiAutoCompleteTextViewCorrectlyInSelectedItemOnSpinner(){
        List<PathologyField> listOfPathologiesToInsertInSpinner = List.of(PathologyField.values());
        pathologyAddPopUp = new PathologyAddPopUp(context, listOfPathologiesToInsertInSpinner);
        pathologyAddPopUp.configurePopUp(new LinearLayout(context),null);
        ViewGroup viewGroup = pathologyAddPopUp.getViewGroup();
        Assert.assertNotNull(viewGroup);
        activityRule.getScenario().onActivity(activity -> {
            // Supondo que você já tenha uma instância de Dialog chamada dialog
            activity.showPopUp(pathologyAddPopUp.getPopUpWindow());
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
            PathologyField pathologyField = listOfPathologiesToInsertInSpinner.stream()
                    .filter(pathology -> pathology.getUpperName().equals(item))
                    .findAny().orElse(null);
            Assert.assertNotNull(pathologyField);
            Assert.assertEquals(context.getText(pathologyField.getHint()),multiAutoCompleteTextView.getHint().toString());
        }
    }

}
