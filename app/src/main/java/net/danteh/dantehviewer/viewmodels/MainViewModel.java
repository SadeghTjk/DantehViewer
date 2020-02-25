package net.danteh.dantehviewer.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.parse.livequery.LiveQueryException;
import com.parse.livequery.ParseLiveQueryClient;
import com.parse.livequery.ParseLiveQueryClientCallbacks;

import static net.danteh.dantehviewer.DantehApplication.parseLiveQueryClient;
import static net.danteh.dantehviewer.MainActivity.TAG;

public class MainViewModel extends ViewModel {

    MutableLiveData<String> liveQuery;

    public LiveData<String> setupLiveQuery(){
        if (liveQuery == null) {

            parseLiveQueryClient = ParseLiveQueryClient.Factory.getClient();
            parseLiveQueryClient.registerListener(new ParseLiveQueryClientCallbacks() {
                @Override
                public void onLiveQueryClientConnected(ParseLiveQueryClient client) {
                    Log.e(TAG, "onLiveQueryClientConnected: CONNECTED");
                    liveQuery = new MutableLiveData<>("CONNECTED");
                }
                @Override
                public void onLiveQueryClientDisconnected(ParseLiveQueryClient client, boolean userInitiated) {
                    Log.e(TAG, "onLiveQueryClientDisconnected: "+userInitiated  );
                    liveQuery = new MutableLiveData<>("DISCONNECTED");
                }
                @Override
                public void onLiveQueryError(ParseLiveQueryClient client, LiveQueryException reason) {
                    Log.e(TAG, "onLiveQueryError: " +reason.getMessage()+"\n"+client.toString() );
                    liveQuery = new MutableLiveData<>("QUERYERROR");
                    reason.printStackTrace();
                }
                @Override
                public void onSocketError(ParseLiveQueryClient client, Throwable reason) {
                    Log.e(TAG, "onSocketError: "+reason.getMessage() );
                    liveQuery = new MutableLiveData<>("SOCKETERROR");
                    reason.printStackTrace();
                }
            });
        }
        return liveQuery;
    }
}
