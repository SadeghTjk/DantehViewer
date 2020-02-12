package net.danteh.dantehviewer.test;

import android.app.ProgressDialog;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.danteh.dantehviewer.DataLinks;

public class TestViewModel extends ViewModel {

    private LiveData<DataLinks> mutableLiveData;
    private LinksRepository newsRepository;

    public void init(){
        if (mutableLiveData != null){
            return;
        }
        newsRepository = LinksRepository.getInstance();
        mutableLiveData = newsRepository.getlinks();

    }

    public LiveData<DataLinks> getNewsRepository() {
        return mutableLiveData;
    }

}