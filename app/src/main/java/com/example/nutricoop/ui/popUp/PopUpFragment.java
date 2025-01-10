package com.example.nutricoop.ui.popUp;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class PopUpFragment {

    private final PopupWindow pw;
    private final ViewGroup viewGroup;
    private int width = LinearLayout.LayoutParams.MATCH_PARENT;
    private int heigth = LinearLayout.LayoutParams.MATCH_PARENT;

    public PopUpFragment(ViewGroup viewGroup,int idViewToFinish) {
        this.pw = new PopupWindow(viewGroup,
                width,heigth, true);
        this.viewGroup = viewGroup;
        encerrarAoClicarFora(idViewToFinish);
    }

    private void encerrarAoClicarFora(int idView){
        ImageView imageViewBackground = this.viewGroup.findViewById(idView);
        imageViewBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pw.dismiss();
            }
        });
    }

    public PopupWindow getPopUpWindow() {
        return pw;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }
}
