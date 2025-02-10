package com.example.worknutri.ui.popUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.worknutri.R;

public class PopUpFragment {

    private final PopupWindow pw;
    private final ViewGroup viewGroup;
    private int width = LinearLayout.LayoutParams.MATCH_PARENT;
    private int heigth = LinearLayout.LayoutParams.MATCH_PARENT;


    public PopUpFragment(LayoutInflater layoutInflater) {
        this.viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.base_layout_popup,null);
        this.pw = new PopupWindow(this.viewGroup,
                width,heigth, true);

        encerrarAoClicarFora(R.id.popup_base_layout_image_out);
    }

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
    protected void insertTitle(int resId){
        TextView textView = viewGroup.findViewById(R.id.popup_base_layout_title_textview);
        if (textView != null){
            textView.setText(resId);
        }
    }
    protected void insertView(View view){
        LinearLayout layout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        layout.addView(view);
    }
    public PopupWindow getPopUpWindow() {
        return pw;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }
}
