package net.danteh.dantehviewer.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.parse.ConfigCallback;
import com.parse.ParseConfig;
import com.parse.ParseException;
import com.parse.livequery.LiveQueryException;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.ParseLiveQueryClientCallbacks;

import org.json.JSONException;
import org.json.JSONObject;

import static net.danteh.dantehviewer.MainActivity.TAG;

public class MainViewModel extends ViewModel {

    MutableLiveData<String> liveQuery;
    MutableLiveData<JSONObject> imageConfig;

    public LiveData<String> setupLiveQuery() {
        if (liveQuery == null) {

        }
        return liveQuery;
    }

    public LiveData<JSONObject> getParseConfig() {
        if (imageConfig == null) {
        //    getConfigs();
        }
        return imageConfig;
    }


}
