package net.danteh.dantehviewer;

import android.util.Log;
import net.danteh.Record;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class ApiCaller {

    public static List<Record> records = new ArrayList<>();
    public static Retrofit retrofit = null;

    public static Api getClient() {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient();

            OkHttpClient.Builder client = httpClient.newBuilder();
            client.connectTimeout(10, TimeUnit.SECONDS);
            client.readTimeout(10, TimeUnit.SECONDS);
            client.writeTimeout(10, TimeUnit.SECONDS);

            retrofit = new Retrofit.Builder()
                    .baseUrl("https://danteh.net/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(client.build())
                    .build();
            Log.e("errrrrrrrr", "getClient: to if");

            Callback<List<Record>> cb = new Callback<List<Record>>() {
                @Override
                public void onFailure(Call<List<Record>> call, Throwable e) {
                    Log.e("teeest", "EREORORROROROROR: "+e.getMessage() );
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call<List<Record>> call, Response<List<Record>> response){
                    if(response.isSuccessful()){

                        records = response.body();

//                        TinyDB tinydb = new TinyDB(rv.getContext());
//
//                        tinydb.putListObject(PRODUCTS,products);
//                        List<Product> n = new ArrayList<>();
//                        n = tinydb.getListObject(PRODUCTS,Product.class);
                    }
                }
            };

            (ApiCaller.getClient().getAllusers()).enqueue(cb);
        }
        Log.e("errrrrrrrr", "getClient: out if");
        return retrofit.create(Api.class);
    }



}