package com.example.worknutri.ui.popUp.detailsPopUp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.content.res.AppCompatResources;

import com.example.worknutri.R;
import com.example.worknutri.ui.popUp.PopUpFragment;

public class ActivityLevelDetailPopUp extends PopUpFragment {

    private final Context context;

    public ActivityLevelDetailPopUp(Context context) {
        super(LayoutInflater.from(context));
        this.context = context;



    }

    public void configureLayout(){
        ViewGroup viewGroup = getViewGroup();
        removeHeaderOfPopUp(viewGroup);
        insertImageView(viewGroup);
        hideBackGroundOfPopUp(viewGroup);
    }

    private void removeHeaderOfPopUp(ViewGroup viewGroup) {
        viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout_header).setVisibility(View.GONE);
    }

    private void insertImageView(ViewGroup viewGroup){
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.tabela_desc_nivel_atividade_pic);
        LinearLayout layout = viewGroup.findViewById(R.id.popup_base_layout_layout_intern);
        layout.addView(imageView);
    }

    private void hideBackGroundOfPopUp(ViewGroup viewGroup){
        ViewGroup scrollLayout = viewGroup.findViewById(R.id.popup_base_layout_scrollview_layout);
        Drawable drawable = AppCompatResources.getDrawable(context, R.color.transparent);
        scrollLayout.setForeground(drawable);
        scrollLayout.setBackground(drawable);
        scrollLayout.setOnClickListener(onClick -> getPopUpWindow().dismiss());
    }
}
