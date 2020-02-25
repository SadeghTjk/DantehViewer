package net.danteh.dantehviewer.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.parse.livequery.LiveQueryException;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.ParseLiveQueryClientCallbacks;

import static net.danteh.dantehviewer.MainActivity.TAG;

public class MainViewModel extends ViewModel {

    MutableLiveData<String> liveQuery;


    public LiveData<String> setupLiveQuery() {
        if (liveQuery == null) {


        }
        return liveQuery;
    }
}
