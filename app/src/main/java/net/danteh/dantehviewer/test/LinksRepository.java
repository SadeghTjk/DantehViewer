package net.danteh.dantehviewer.test;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import net.danteh.dantehviewer.Api;
import net.danteh.dantehviewer.DataLinks;
import net.danteh.dantehviewer.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static net.danteh.dantehviewer.MainActivity.TAG;

public class LinksRepository {

    private static LinksRepository newsRepository;

    public static LinksRepository getInstance(){
        if (newsRepository == null){
            newsRepository = new LinksRepository();
        }
        return newsRepository;
    }

    private Api linksApi;

    public LinksRepository(){
        linksApi = RetrofitService.cteateService(Api.class);
    }

    public LiveData<DataLinks> getlinks(){
        MutableLiveData<DataLinks> linksData = new MutableLiveData<>();
        linksApi.getLinks().enqueue(new Callback<DataLinks>() {
            @Override
            public void onResponse(Call<DataLinks> call,
                                   Response<DataLinks> response) {
                if (response.isSuccessful()){
                    linksData.setValue(response.body());
                    Log.e(TAG, "onLinksRepos: "+ linksData.getValue().getRecords().size() );
                }
                else
                    Log.e(TAG, "onElse: "+response.message() + "\n "+response.raw() );
            }

            @Override
            public void onFailure(Call<DataLinks> call, Throwable t) {
                Log.e(TAG, "onElse: "+t.getMessage() + "\n "+t.getCause() );
                t.printStackTrace();
                linksData.setValue(null);
            }
        });
        return linksData;
    }
}
