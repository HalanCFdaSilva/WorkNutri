package com.example.worknutri.ui.popUp;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.worknutri.R;

public class PopUpFragment {

    private final PopupWindow pw;
    private ViewGroup viewGroup;
    private final int width = LinearLayout.LayoutParams.MATCH_PARENT;
    private final int heigth = LinearLayout.LayoutParams.MATCH_PARENT;

    private LayoutInflater inflater;


    public PopUpFragment(LayoutInflater layoutInflater) {
        this.viewGroup = (ViewGroup) layoutInflater.inflate(R.layout.popup_base_layout, null);
        this.pw = new PopupWindow(this.viewGroup,
                width, heigth, true);

        this.inflater = layoutInflater;
        encerrarAoClicarFora(R.id.popup_base_layout_image_out);
    }

    public PopUpFragment(ViewGroup viewGroup, int idViewToFinish) {
        this.pw = new PopupWindow(viewGroup,
                width, heigth, true);
        this.viewGroup = viewGroup;
        encerrarAoClicarFora(idViewToFinish);
    }


    protected void encerrarAoClicarFora(int idView) {
        ImageView imageViewBackground = this.viewGroup.findViewById(idView);
        imageViewBackground.setOnClickListener(v -> pw.dismiss());
    }

    protected void insertTitle(int resId) {
        TextView textView = viewGroup.findViewById(R.id.popup_base_layout_title_textview_header);
        if (textView != null) {
            textView.setText(resId);
        }
    }


    protected ViewGroup getViewToInsert(){
        return viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
    }

    public PopupWindow getPopUpWindow() {
        return pw;
    }

    public ViewGroup getViewGroup() {
        return viewGroup;
    }

    public LayoutInflater getInflater() {
        return inflater;
    }

    protected void setViewGroup(ViewGroup viewGroup) {
        this.viewGroup = viewGroup;
        pw.setContentView(viewGroup);
    }
}
