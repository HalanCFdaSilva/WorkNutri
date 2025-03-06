package com.example.worknutri.ui.popUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.worknutri.R;

public class NivelAtividadeDescritpionPopUp extends PopUpFragment {


    public NivelAtividadeDescritpionPopUp(LayoutInflater layoutInflater) {
        super(layoutInflater);

        ViewGroup viewGroup = getViewGroup();
        viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout_header).setVisibility(View.GONE);


        ImageView imageView = new ImageView(layoutInflater.getContext());
        imageView.setImageResource(R.drawable.tabela_desc_nivel_atividade_pic);
        LinearLayout layout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        layout.addView(imageView);
        ViewGroup scrollLayout = viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout);
        scrollLayout.setForeground(layoutInflater.getContext().getDrawable(R.color.transparent));
        scrollLayout.setBackground(layoutInflater.getContext().getDrawable(R.color.transparent));
        scrollLayout.setOnClickListener(onClick -> {
            getPopUpWindow().dismiss();
        });


    }


}
