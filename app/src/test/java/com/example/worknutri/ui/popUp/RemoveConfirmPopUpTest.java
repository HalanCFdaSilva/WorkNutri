package com.example.worknutri.ui.popUp;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;

import com.example.worknutri.R;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.Shadows;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.test.core.app.ApplicationProvider;

import java.lang.reflect.Field;

@RunWith(RobolectricTestRunner.class)
public class RemoveConfirmPopUpTest {

    private RemoveConfirmPopUp popUp;

    @Before
    public void setUp() {
        LayoutInflater inflater = LayoutInflater.from(ApplicationProvider.getApplicationContext());
        popUp = new RemoveConfirmPopUp(inflater);
    }

    @Test
    public void constructor_hidesHeader() {
        ViewGroup viewGroup = popUp.getViewGroup();
        View header = viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout_header);
        assertNotNull(header);
        assertEquals(View.GONE, header.getVisibility());
    }

    @Test
    public void getConfirmButton_returnsConfirmButton() {
        Button confirm = popUp.getConfirmButton();
        assertNotNull(confirm);
        assertEquals(R.id.popup_delete_confirm_layout_confirm_button, confirm.getId());
    }

    @Test
    public void cancelButton_hasOnClickListener() {
        ViewGroup viewGroup = popUp.getViewGroup();
        Button cancel = viewGroup.findViewById(R.id.popup_delete_confirm_layout_cancel_button);
        assertNotNull(cancel);
        assertNotNull(Shadows.shadowOf(cancel).getOnClickListener());
    }

    @Test
    public void constraintMargins_areAppliedOnScrollView() {
        ViewGroup viewGroup = popUp.getViewGroup();
        View scrollView = viewGroup.findViewById(R.id.popup_base_layout_scrollview);
        assertNotNull(scrollView);

        ConstraintLayout.LayoutParams lp = (ConstraintLayout.LayoutParams) scrollView.getLayoutParams();
        // o código define margins em 32 (pixels)
        assertEquals(32, lp.rightMargin);
        assertEquals(32, lp.leftMargin);
    }

    @Test
    public void cancelButton_click_callsPopupDismiss_withMockedPopupWindow() throws Exception {
        ViewGroup viewGroup = popUp.getViewGroup();
        Button cancel = viewGroup.findViewById(R.id.popup_delete_confirm_layout_cancel_button);
        assertNotNull(cancel);

        View.OnClickListener listener = Shadows.shadowOf(cancel).getOnClickListener();
        assertNotNull(listener);

        // substituir o PopupWindow privado por um mock via reflexão e verificar dismiss()
        PopupWindow mockPopup = mock(PopupWindow.class);
        Field pwField = PopUpFragment.class.getDeclaredField("pw");
        pwField.setAccessible(true);
        pwField.set(popUp, mockPopup);

        // simula clique
        listener.onClick(cancel);

        verify(mockPopup).dismiss();
    }
}

