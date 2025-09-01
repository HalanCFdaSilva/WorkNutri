package com.example.worknutri.ui.popUp.detailsPopUp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.core.content.ContextCompat;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.worknutri.R;
import com.example.worknutri.ui.popUp.PopUpFragment;
import com.example.worknutri.ui.popUp.factory.PopUpFactoryImpl;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ActivityLevelDetailPopUpTest {

    private Context context;
    private ActivityLevelDetailPopUp activityLevelDetailPopUp;

    @Before
    public void setUp() {
        context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        activityLevelDetailPopUp = new PopUpFactoryImpl(context).generateActivityLevelDetailPopUp();
        activityLevelDetailPopUp.configureLayout();
    }

    @Test
    public void testPreconditions() {
        assert activityLevelDetailPopUp != null;
    }

    @Test
    public void ActivityLevelDetailPopUpExtendsPopUpFragment() {
        Class<?> superclass = activityLevelDetailPopUp.getClass().getSuperclass();
        Assert.assertNotNull(superclass);
        Assert.assertEquals(PopUpFragment.class, superclass);
    }

    @Test
    public void getPopUpWindowIsNotNull() {
        Assert.assertNotNull(activityLevelDetailPopUp.getPopUpWindow());
    }

    @Test
    public void getViewGroupMethodIsNonNull() {
        Assert.assertNotNull(activityLevelDetailPopUp.getViewGroup());
    }

    @Test
    public void configureLayoutMethodMakeLayoutHeaderGone() {
        Assert.assertEquals(View.GONE,
                activityLevelDetailPopUp.getViewGroup().findViewById(R.id.popup_base_layout_scrollview_layout_header)
                        .getVisibility());
    }

    @Test
    public void configureLayoutMethodInsertOnlyOneChildInLayoutIntern() {

        ViewGroup viewGroup = activityLevelDetailPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertEquals(1,viewGroup.getChildCount());
    }

    @Test
    public void configureLayoutMethodInsertImageViewInLayoutIntern() {
        ViewGroup viewGroup = activityLevelDetailPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        Assert.assertTrue(viewGroup.getChildAt(0) instanceof android.widget.ImageView);
    }

    @Test
    public void configureLayoutMethodSetImageViewWithCorrectDrawableResource() {
        ViewGroup viewGroup = activityLevelDetailPopUp.getViewGroup().findViewById(R.id.popup_base_layout_layout_intern);
        ImageView imageView = (ImageView) viewGroup.getChildAt(0);

        Drawable expectedDrawable = ContextCompat.getDrawable(context, R.drawable.tabela_desc_nivel_atividade_pic);
        Drawable actualDrawable = imageView.getDrawable();

        Assert.assertNotNull(expectedDrawable);
        Assert.assertEquals(expectedDrawable.getConstantState(), actualDrawable.getConstantState());
    }

    @Test
    public void configureLayoutMethodSetBackgroundAndForegroundOfScrollLayoutToTransparent() {
        ViewGroup scrollLayout = activityLevelDetailPopUp.getViewGroup().findViewById(R.id.popup_base_layout_scrollview_layout);
        int expectedColor = InstrumentationRegistry.getInstrumentation().getTargetContext().getResources().getColor(R.color.transparent, null);
        int actualBackgroundColor = ((android.graphics.drawable.ColorDrawable) scrollLayout.getBackground()).getColor();
        int actualForegroundColor = ((android.graphics.drawable.ColorDrawable) scrollLayout.getForeground()).getColor();
        Assert.assertEquals(expectedColor, actualBackgroundColor);
        Assert.assertEquals(expectedColor, actualForegroundColor);
    }


}
