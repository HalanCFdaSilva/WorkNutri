package com.example.nutricoop.ui.popUp.hourDatePopUp;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;


import com.example.nutricoop.R;

public class HourDateFragment {
    private final ViewGroup layout;


    public HourDateFragment(LayoutInflater layoutInflater ){
        layout = (ViewGroup) layoutInflater.inflate(R.layout.time_descrition_fragment,null,false);

    }

    public void setDayOfweek(String dayOfweek) {
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_day_of_week);
        textView.setText(dayOfweek);
    }

    public void setHourBegin(String hourBegin){
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_hour_begin);
        textView.setText(hourBegin);
    }

    public void setHourEnd(String hourEnd) {
        TextView textView = layout.findViewById(R.id.time_descrition_fragment_textView_hour_end);
        textView.setText(hourEnd);
    }

    public void addLayout(ViewGroup layoutWhereAdd ) {
        this.trashButtonConfigure(layoutWhereAdd);
        layoutWhereAdd.addView(layout);
    }
    public void removeTrashButton(){
        layout.findViewById(R.id.time_descrition_fragment_button_delete).setVisibility(View.GONE);
    }

    private void trashButtonConfigure(ViewGroup layoutWhereAdd ) {
        ImageButton deleteButton = layout.findViewById(R.id.time_descrition_fragment_button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutWhereAdd.removeView(layout);

            }
        });
    }

    public ViewGroup getLayout() {
        return layout;
    }


}
