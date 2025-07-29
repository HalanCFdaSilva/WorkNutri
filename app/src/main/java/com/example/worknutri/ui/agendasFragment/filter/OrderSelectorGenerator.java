package com.example.worknutri.ui.agendasFragment.filter;

import android.content.Context;
import android.widget.HorizontalScrollView;

import com.example.worknutri.R;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGeneratorUtil;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class OrderSelectorGenerator {

    private final Context context;
    private final UiState uiState;

    public OrderSelectorGenerator(Context context, UiState uiState) {
        this.context = context;

        this.uiState = uiState;
    }

    public HorizontalScrollView generateChipsOfOrder(List<OrderFilterSelectedsBy> listOfOrder) {
        CategoriesGeneratorUtil util = new CategoriesGeneratorUtil(context);
        HorizontalScrollView scrollView = util.generateChipGroup();
        ChipGroup chipGroup = scrollView.findViewById(R.id.filter_category_chipgroup);
        for (OrderFilterSelectedsBy order : listOfOrder) {
            Chip chip = util.generateChip(order.getValue().replace('_', ' '));
            chip.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if (isChecked) {
                    uiState.setOrderBy(order);
                }
            });
            chipGroup.addView(chip);
        }
        return scrollView;
    }
}
