package com.example.worknutri.ui.popUp.detailsPopUp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.worknutri.R;

public class PatologiaPopUpFragment {
    private final LayoutInflater inflater;
    private ViewGroup viewGroup;

    public PatologiaPopUpFragment(LayoutInflater inflater) {
        this.inflater = inflater;
       generateViewGroup();
    }
    public void generateViewGroup(){
        this.viewGroup =(ViewGroup) inflater.inflate(R.layout.popup_patologia_description_fragment,null,false);
        setOnClick();
    }

    private void setOnClick(){
        viewGroup.setOnClickListener(onClick ->{
            TextView textView = viewGroup.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
            ImageView imageView = viewGroup.findViewById(R.id.popup_paciente_descrition_patologia_imageView_arrow);

            if (textView.getVisibility() == View.GONE){
                textView.setVisibility(View.VISIBLE);
                imageView.setImageResource(R.drawable.ic_arrow_up);

            }else {
                textView.setVisibility(View.GONE);
                imageView.setImageResource(R.drawable.ic_arrow_down);
            }
        });
    }
    public void setTitle(String titleString){
        TextView textView = viewGroup.findViewById(R.id.popup_paciente_descrition_patologia_textview_title);
        textView.setText(titleString);
    }
    public void setDescription(String descriptionString){
        TextView textView = viewGroup.findViewById(R.id.popup_paciente_descrition_patologia_textview_description);
        textView.setText(descriptionString);
    }



    public ViewGroup getViewGroup() {
        return viewGroup;
    }
}
