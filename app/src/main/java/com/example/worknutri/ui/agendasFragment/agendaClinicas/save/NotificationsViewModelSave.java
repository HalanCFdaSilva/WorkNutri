package com.example.worknutri.ui.agendasFragment.agendaClinicas.save;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationsViewModelSave extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationsViewModelSave() {
        mText = new MutableLiveData<>();
        mText.setValue("This is notifications fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}