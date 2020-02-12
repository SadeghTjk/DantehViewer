package net.danteh.dantehviewer;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;
import static net.danteh.dantehviewer.ApiCaller.expires;
import static net.danteh.dantehviewer.ApiCaller.sessionid;

public class LinksViewModel extends AndroidViewModel {

    private Retrofit retrofit = null;
    private MutableLiveData<DataLinks> dataLinks= null;

    public LinksViewModel(@NonNull Application application) {
        super(application);
    }


    public MutableLiveData<DataLinks> getLinks() {
        if (dataLinks == null) {
            dataLinks = new MutableLiveData<>();
            loadLinks();
        }
        return dataLinks;
    }

    public MutableLiveData<DataLinks> loadLinks() {
        // Do an asynchronous operation to fetch users.
        OkHttpClient httpClient = new OkHttpClient();

        OkHttpClient.Builder client = httpClient.newBuilder();
        client.connectTimeout(10, TimeUnit.SECONDS);
        client.readTimeout(10, TimeUnit.SECONDS);
        client.writeTimeout(10, TimeUnit.SECONDS);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Expires", expires)
                        .header("Cookie",sessionid)
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://danteh.net/")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(client.build())
                .build();

        Api apiInterface = retrofit.create(Api.class);

//        try {
//            ConnectivityManager connectivityManager = (ConnectivityManager) getApplication().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

     //       if (networkInfo.isConnected()) {
//                    final ProgressDialog dialog;
//                    dialog = new ProgressDialog(context);
//                    dialog.setMessage("Loading...");
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();

                Call<DataLinks> registerCall = apiInterface.getLinks();
                registerCall.enqueue(new retrofit2.Callback<DataLinks>() {
                    @Override
                    public void onResponse(Call<DataLinks> registerCall, retrofit2.Response<DataLinks> response) {

                        if (response.isSuccessful()) {
                            //  dialog.dismiss();
                            // dataLinks = ;
                            dataLinks.setValue(response.body());
                            //progressBar.dismiss();
                            Log.e(TAG, "getlinks ApiCaller: " +" SYNCED "+ dataLinks.getValue().getRecords().size());

                        } else {
                           // progressBar.dismiss();
                            Toast.makeText(getApplication().getApplicationContext(), "links else respone" + response.errorBody() + response.code(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<DataLinks> call, Throwable t) {
                        try {
                            Log.e("Tag", "error" + t.toString());

                           // progressBar.dismiss();
                        } catch (Resources.NotFoundException e) {
                            e.printStackTrace();
                        }
                        dataLinks.setValue(null);
                    }
                });

//            }
//            else {
//                Log.e("Tag", "no internet available");
//
//
//            }
////        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
        return dataLinks;
    }
}
