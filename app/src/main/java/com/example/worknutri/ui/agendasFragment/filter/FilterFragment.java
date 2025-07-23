package com.example.worknutri.ui.agendasFragment.filter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.example.worknutri.databinding.FilterAgendaBinding;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.FilterCategories;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class FilterFragment extends BottomSheetDialogFragment {



    private FilterAgendaBinding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FilterAgendaBinding.inflate(inflater, container, false);


        generateFilter(binding.filterBottomSheetParent.getContext());

        return binding.filterBottomSheetParent;
    }

    protected abstract void generateFilter(Context context);

    protected void insertCategotyInLayout(FilterCategories categoryGenerate) {
        ViewGroup viewGroup = categoryGenerate.generateCategory(getLayoutInflater());
        binding.filterLayoutCategories.addView(viewGroup);
    }



    protected abstract void getAllCategories();
    protected abstract  void orderListOfSelecteds();
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
            BottomSheetBehavior<ConstraintLayout> behavior = BottomSheetBehavior.from(binding.filterBottomSheet);
            behavior.setHideable(false);
            CoordinatorLayout coordinatorLayout = binding.filterBottomSheetParent;


            BottomSheetBehavior.from((View) coordinatorLayout.getParent()).setPeekHeight(coordinatorLayout.getHeight());

            behavior.setPeekHeight(coordinatorLayout.getHeight());
            coordinatorLayout.getParent().requestLayout();
        });
    }

    private void onClickSaveButton(){
        binding.filterButonConfirm.setOnClickListener(v -> {

            getAllCategories();
            orderListOfSelecteds();
            getParentFragmentManager().setFragmentResult(getRequestKey(),generateBundle());
            dismiss();
        });
    }

    private void onClickInResetButton() {
        binding.btnResetAll.setOnClickListener(onClick -> {
            resetAllCategories();
            binding.filterButonConfirm.callOnClick();
        });
    }

    protected abstract void resetAllCategories();



}
