package com.example.worknutri.ui.popUp.detailsPopUp;

import android.content.Context;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.ui.agendasFragment.ScheduleActivity;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class PatologiaPopUpFragmentTest {

    private PatologiaPopUpFragment patologiaPopUpFragment;
    private Context context;

    @Rule
    public ActivityScenarioRule<ScheduleActivity> activityRule = new ActivityScenarioRule<>(ScheduleActivity.class);

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
        patologiaPopUpFragment = new PatologiaPopUpFragment(LayoutInflater.from(context));
    }

    @Test
    public void generateViewGroupInflateCorrectLayout() {
        View childAt = generateView();
        Assert.assertNotNull(childAt);
        Assert.assertEquals(R.id.popup_patologia_descrition,childAt.getId());
    }

    @Test
    public void layoutInflatedInGenerateViewGroupMethodHasCorrectViews(){
        ViewGroup childAt = generateView();
        Assert.assertEquals(1,childAt.getChildCount());

        ViewGroup cardView = childAt.findViewById(R.id.popup_paciente_descrition_patologia_cardview);
        Assert.assertNotNull(cardView);
        Assert.assertTrue(cardView instanceof CardView);
        Assert.assertEquals(1,cardView.getChildCount());

        ViewGroup view = cardView.findViewById(R.id.popup_paciente_descrition_patologia_layout);
        Assert.assertNotNull(view);
        Assert.assertTrue(view instanceof ConstraintLayout);
        Assert.assertEquals(4,view.getChildCount());


        Assert.assertNotNull(view.findViewById(R.id.popup_paciente_descrition_patologia_imageView_background));
        Assert.assertTrue(view.findViewById(R.id.popup_paciente_descrition_patologia_imageView_background) instanceof ImageView);

        Assert.assertNotNull(view.findViewById(R.id.popup_paciente_descrition_patologia_textview_title));
        Assert.assertTrue(view.findViewById(R.id.popup_paciente_descrition_patologia_textview_title) instanceof TextView);

        Assert.assertNotNull(view.findViewById(R.id.popup_paciente_descrition_patologia_textview_description));
        Assert.assertTrue(view.findViewById(R.id.popup_paciente_descrition_patologia_textview_description) instanceof TextView);

        Assert.assertNotNull(view.findViewById(R.id.popup_paciente_descrition_patologia_imageView_arrow));
        Assert.assertTrue(view.findViewById(R.id.popup_paciente_descrition_patologia_imageView_arrow) instanceof ImageView);

    }

    @Test
    public void setTitleMethodSetsCorrectTitle(){
        String title = "title";
        ViewGroup childAt = generateView();
        patologiaPopUpFragment.setTitle(title);

        TextView textView = childAt.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
        Assert.assertEquals(title,textView.getText().toString());
    }

    @Test
    public void setDescriptionMethodSetsCorrectDescription(){
        String description = "description";
        ViewGroup childAt = generateView();
        patologiaPopUpFragment.setDescription(description);

        TextView textView = childAt.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        Assert.assertEquals(description,textView.getText().toString());
    }


    @NonNull
    private ViewGroup generateView() {
        LinearLayout layout = new LinearLayout(context);
        patologiaPopUpFragment.generateViewGroup(layout);
        return patologiaPopUpFragment.getViewGroup();
    }

    @Test
    public void onClickTogglesDescriptionVisibilityAndArrowIcon() {
        activityRule.getScenario().onActivity(activity -> {
            context = activity;
            context = new ContextThemeWrapper(context, R.style.Theme_NutriCoop);
            patologiaPopUpFragment = new PatologiaPopUpFragment(LayoutInflater.from(context));
            ViewGroup childAt = generateView();

            TextView textView = childAt.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);

            // Initial state: description should be GONE and arrow should point down
            Assert.assertEquals(View.GONE, textView.getVisibility());

            // Simulate click to expand
            childAt.performClick();
            Assert.assertEquals(View.VISIBLE, textView.getVisibility());

            // Simulate click to collapse
            childAt.performClick();
            Assert.assertEquals(View.GONE, textView.getVisibility());
        });

    }



}
