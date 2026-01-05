package com.example.worknutri.ui.popUp;



import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupWindow;

import com.example.worknutri.R;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.Field;

public class PopUpFragmentTest {

    private ViewGroup mockViewGroup;
    private ImageView mockImageView;
    private TextView mockTextView;
    private PopUpFragment popUpFragment;

    private final int idFinish = R.id.popup_base_layout_image_out;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        mockViewGroup = mock(ViewGroup.class);
        mockImageView = mock(ImageView.class);
        mockTextView = mock(TextView.class);
        ViewGroup inner = mock(ViewGroup.class);

        when(mockViewGroup.findViewById(idFinish)).thenReturn(mockImageView);
        when(mockViewGroup.findViewById(R.id.popup_base_layout_title_textview_header)).thenReturn(mockTextView);
        when(mockViewGroup.findViewById(R.id.popup_base_layout_layout_intern)).thenReturn(inner);

        // usa o construtor que recebe ViewGroup para evitar LayoutInflater
        popUpFragment = new PopUpFragment(mockViewGroup, idFinish);
    }

    @Test
    public void insertTitle_setsText_whenTextViewPresent() {
        int fakeRes = 12345;
        popUpFragment.insertTitle(fakeRes);
        verify(mockTextView).setText(fakeRes);
    }

    @Test
    public void encerrarAoClicarFora_click_callsDismissOnPopupWindow() throws Exception {
        // captura o listener que foi registrado no ImageView
        ArgumentCaptor<View.OnClickListener> captor = ArgumentCaptor.forClass(View.OnClickListener.class);
        verify(mockImageView).setOnClickListener(captor.capture());
        View.OnClickListener listener = captor.getValue();

        // substitui o PopupWindow privado por um mock via reflexão
        PopupWindow mockPopup = mock(PopupWindow.class);
        Field pwField = PopUpFragment.class.getDeclaredField("pw");
        pwField.setAccessible(true);
        pwField.set(popUpFragment, mockPopup);

        // simula o clique
        listener.onClick(mock(View.class));

        verify(mockPopup).dismiss();
    }

    @Test
    public void setViewGroup_updatesAndCallsSetContentView() throws Exception {
        // substitui o PopupWindow por mock para verificar setContentView
        PopupWindow mockPopup = mock(PopupWindow.class);
        Field pwField = PopUpFragment.class.getDeclaredField("pw");
        pwField.setAccessible(true);
        pwField.set(popUpFragment, mockPopup);

        ViewGroup newViewGroup = mock(ViewGroup.class);
        popUpFragment.setViewGroup(newViewGroup);

        assertSame(newViewGroup, popUpFragment.getViewGroup());
        verify(mockPopup).setContentView(newViewGroup);
    }

    @Test
    public void getViewToInsert_returnsChildView() {
        ViewGroup expected = mockViewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        assertSame(expected, popUpFragment.getViewToInsert());
    }
}

