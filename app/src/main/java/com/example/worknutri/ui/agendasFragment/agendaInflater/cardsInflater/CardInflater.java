package com.example.worknutri.ui.agendasFragment.agendaInflater.cardsInflater;

import android.view.ViewGroup;

public interface CardInflater<T> {
    ViewGroup generateCard(ViewGroup layout, T element);
}
