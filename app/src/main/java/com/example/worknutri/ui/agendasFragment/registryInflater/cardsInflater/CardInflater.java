package com.example.worknutri.ui.agendasFragment.registryInflater.cardsInflater;

import android.view.ViewGroup;

public interface CardInflater<T> {
    void configureOnClickInCard(ViewGroup card, T element);
    ViewGroup inflateCard(T element);

}
