package com.example.athina.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("“The best book is the one you can’t put down. The best exercise is the one you enjoy doing every day. The best health food is the one you find tasty. The best work is the work you’d do for free.” – Naval Ravikant");
    }

    public LiveData<String> getText() {
        return mText;
    }
}