package com.example.planeeandroid.ui.add;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class addViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public addViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
