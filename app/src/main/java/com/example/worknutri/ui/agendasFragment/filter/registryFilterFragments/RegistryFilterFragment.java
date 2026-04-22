package com.example.worknutri.ui.agendasFragment.filter.registryFilterFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.example.worknutri.databinding.RegistryFilterBinding;
import com.example.worknutri.ui.agendasFragment.filter.OrderSelectorGenerator;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.example.worknutri.ui.agendasFragment.filter.pojos.OrderFilterSelectedsBy;
import com.example.worknutri.ui.agendasFragment.filter.pojos.UiState;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public abstract class RegistryFilterFragment extends BottomSheetDialogFragment {



    private RegistryFilterBinding binding;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = RegistryFilterBinding.inflate(inflater, container, false);
        generateFilter(binding.registryFilter.getContext());
        generateChipsOfOrder(binding.registryFilter.getContext());

        return binding.registryFilter;
    }
    protected abstract void generateFilter(Context context);
    private void generateChipsOfOrder(Context context) {
        OrderSelectorGenerator generator = new OrderSelectorGenerator(context, getUiState());
        HorizontalScrollView orderSelectorLayout = generator.generateChipsOfOrder(getListOfSortChips());
        binding.registryFilterSortLayout.addView(orderSelectorLayout);
    }

    protected abstract UiState getUiState();
    protected abstract List<OrderFilterSelectedsBy> getListOfSortChips();



    protected void insertCategotyInLayout(FilterCategories categoryGenerate) {
        ViewGroup viewGroup = categoryGenerate.generateCategory(getLayoutInflater());
        binding.registryFilterLayoutCategories.addView(viewGroup);
    }

    protected abstract Bundle generateBundle();
    protected abstract String getRequestKey();



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        correctExpandedBottomSheet();
        onClickSaveButton();
        onClickInResetButton();
    }

    private void correctExpandedBottomSheet() {
        requireDialog().setOnShowListener(dialog -> {
            BottomSheetBehavior<ConstraintLayout> behavior = BottomSheetBehavior.from(binding.registryFilterBottomSheet);
            behavior.setHideable(false);
            CoordinatorLayout coordinatorLayout = binding.registryFilter;


            BottomSheetBehavior.from((View) coordinatorLayout.getParent()).setPeekHeight(coordinatorLayout.getHeight());

            behavior.setPeekHeight(coordinatorLayout.getHeight());
            coordinatorLayout.getParent().requestLayout();
        });
    }

    private void onClickSaveButton(){
        binding.registryFilterButonConfirm.setOnClickListener(v -> {

            getAllCategories();
            getParentFragmentManager().setFragmentResult(getRequestKey(),generateBundle());
            dismiss();
        });
    }

    protected abstract void getAllCategories();


    private void onClickInResetButton() {
        binding.registryFilterButtonReset.setOnClickListener(onClick -> {
            resetAllCategories();
            binding.registryFilterButonConfirm.callOnClick();
        });
    }

    protected abstract void resetAllCategories();

}
