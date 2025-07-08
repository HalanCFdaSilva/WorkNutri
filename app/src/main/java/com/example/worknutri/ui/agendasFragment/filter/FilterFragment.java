package com.example.worknutri.ui.agendasFragment.filter;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.example.worknutri.databinding.FilterAgendaBinding;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGenerator;
import com.example.worknutri.ui.agendasFragment.filter.categoriesGenerator.CategoriesGeneratorUtil;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Collections;
import java.util.List;

public abstract class FilterFragment extends BottomSheetDialogFragment {



    private FilterAgendaBinding binding;
    protected List<CategoriesGenerator> categories;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FilterAgendaBinding.inflate(inflater, container, false);
        categories = Collections.emptyList();

        generateFilter(binding.filterBottomSheetParent.getContext());
        onClickSaveButton();

        return binding.filterBottomSheetParent;
    }

    protected abstract void generateFilter(Context context);

    protected void insertCategotyInLayout(CategoriesGenerator categoryGenerate) {
        ViewGroup viewGroup = categoryGenerate.generateCategory(getLayoutInflater());
        binding.filterLayoutCategories.addView(viewGroup);
        categories.add(categoryGenerate);
    }



    private void onClickSaveButton(){
        binding.filterButonConfirm.setOnClickListener(v -> {

            orderListOfSelecteds();
            getParentFragmentManager().setFragmentResult(getRequestKey(),generateBundle());
            dismiss();
        });
    }

    protected abstract void getAllCategories(ViewGroup viewGroup);
    protected abstract  void orderListOfSelecteds();
    protected abstract Bundle generateBundle();
    protected abstract String getRequestKey();



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        correctExpandedBottomSheet();
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



}
