package com.example.planeeandroid.ui.task_list;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TaskListViewModel extends ViewModel {
    private MutableLiveData<String> mText;

    public TaskListViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}
