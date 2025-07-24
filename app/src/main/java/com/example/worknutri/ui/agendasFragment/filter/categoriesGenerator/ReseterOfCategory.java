package com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator;

import androidx.annotation.NonNull;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.slider.RangeSlider;

public abstract class ReseterOfCategory {



    public static void resetChipGroup(@NonNull ChipGroup chipGroup) {

        int childCount = chipGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            Chip chip = (Chip) chipGroup.getChildAt(i);
            if (chip.isChecked()){
                chip.setChecked(false);
            }
        }
    }

    public static void resetSlider(@NonNull RangeSlider rangeSlider, @NonNull float[] valuesInState) {
        valuesInState[0] = rangeSlider.getValueFrom();
        valuesInState[1] = rangeSlider.getValueTo();
        rangeSlider.setValues(rangeSlider.getValueFrom(), rangeSlider.getValueTo());
    }
}
