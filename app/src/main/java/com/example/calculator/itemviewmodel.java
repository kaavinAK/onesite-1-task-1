package com.example.calculator;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class itemviewmodel extends ViewModel {

    private final MutableLiveData<String>shareddata=new MutableLiveData<String>();

    public void setdata(String data)
    {
        shareddata.setValue(data);
    }
    public MutableLiveData<String> getShareddata()
    {
        return shareddata;
    }
}
