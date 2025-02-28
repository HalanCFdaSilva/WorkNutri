package com.example.worknutri.ui.agendasFragment.agendaClinicas.save;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.worknutri.MainActivity;
import com.example.worknutri.databinding.FragmentAgendaClinicas2Binding;


public class AgendaClinicasFragmentSave extends Fragment {

    private FragmentAgendaClinicas2Binding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModelSave notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModelSave.class);

        binding = FragmentAgendaClinicas2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        MainActivity.isPaciente = false;
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}