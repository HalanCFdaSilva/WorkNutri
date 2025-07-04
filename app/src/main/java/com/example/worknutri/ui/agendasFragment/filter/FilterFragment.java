package com.example.worknutri.ui.agendasFragment.filter;



import static com.example.worknutri.ui.agendasFragment.filter.ConstantsFilters.PACIENTE_FILTER_POJO;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.example.worknutri.databinding.FilterAgendaBinding;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public abstract class FilterFragment extends BottomSheetDialogFragment {



    private FilterAgendaBinding binding;
    protected AgendaFilter agendaFilter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = FilterAgendaBinding.inflate(inflater, container, false);
        agendaFilter = new AgendaFilter(binding.filterBottomSheetParent.getContext());
        generateFilter();


        onClickSaveButton();

        return binding.filterBottomSheetParent;
    }
    protected abstract void generateFilter();

    private void onClickSaveButton(){
        binding.filterButonConfirm.setOnClickListener(v -> {
            getParentFragmentManager().setFragmentResult(getRequestKey(),generateBundle());
            dismiss();
        });
    }

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

    protected void setFilterTitle(String title) {
        binding.filterTitle.setText(title);
    }

    protected ViewGroup getLayoutToCategories() {
        return binding.filterLayoutCategories;
    }

}
