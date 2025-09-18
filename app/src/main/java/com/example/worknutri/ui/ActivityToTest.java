package com.example.worknutri.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.PopupWindow;

import androidx.annotation.Nullable;

import com.example.worknutri.R;

public class ActivityToTest extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
    }

    public void showPopUp(PopupWindow dialog) {
        dialog.showAtLocation(findViewById(R.id.layout_test), Gravity.CENTER, 0, 0);
    }
}
