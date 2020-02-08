package net.danteh.dantehviewer;

import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Handler;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.Callback;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ApiCaller {
    public static String sessionid;
    public static String expires;
    public static Record user = new Record();
    public static List<String> headers = new ArrayList<>();
    public static List<Record> users = new ArrayList<>();
    public static Retrofit retrofit = null;
//
//    public static Api getClient() {
//        if (retrofit == null) {
//            OkHttpClient httpClient = new OkHttpClient();
//
//            OkHttpClient.Builder client = httpClient.newBuilder();
//            client.connectTimeout(10, TimeUnit.SECONDS);
//            client.readTimeout(10, TimeUnit.SECONDS);
//            client.writeTimeout(10, TimeUnit.SECONDS);
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl("https://danteh.net/")
//                    .addConverterFactory(JacksonConverterFactory.create())
//                    .client(client.build())
//                    .build();
//            Log.e("errrrrrrrr", "getClient: to if");
//
//            Callback<List<Users>> cb = new Callback<List<Users>>() {
//                @Override
//                public void onFailure(Call<List<Users>> call, Throwable e) {
//                    Log.e("teeest", "EREORORROROROROR: "+e.getMessage() );
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call<List<Users>> call, Response<List<Users>> response){
//                    if(response.isSuccessful()){
//
//                        users = response.body();
//
////                        TinyDB tinydb = new TinyDB(rv.getContext());
////
////                        tinydb.putListObject(PRODUCTS,products);
////                        List<Product> n = new ArrayList<>();
////                        n = tinydb.getListObject(PRODUCTS,Product.class);
//                    }
//                }
//            };
//
//            (ApiCaller.getClient().getAllusers()).enqueue(cb);
//        }
//        Log.e("errrrrrrrr", "getClient: out if");
//        return retrofit.create(Api.class);
//    }

    public static void adminloger(Context context, Retrofit retrofit) {
        if (retrofit == null) {
            OkHttpClient httpClient = new OkHttpClient();

            OkHttpClient.Builder client = httpClient.newBuilder();
            client.connectTimeout(10, TimeUnit.SECONDS);
            client.readTimeout(10, TimeUnit.SECONDS);
            client.writeTimeout(10, TimeUnit.SECONDS);
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl("https://danteh.net/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .client(client.build())
                    .build();

            Api apiInterface = retrofit.create(Api.class);

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject paramObject = new JSONObject();
                paramObject.put("username", "sadegh");
                paramObject.put("password", "tajik");
                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo.isConnected()) {
//                    final ProgressDialog dialog;
//                    dialog = new ProgressDialog(context);
//                    dialog.setMessage("Loading...");
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();

                    Call<Login> registerCall = apiInterface.adminlogin(gsonObject);
                    registerCall.enqueue(new retrofit2.Callback<Login>() {
                        @Override
                        public void onResponse(Call<Login> registerCall, retrofit2.Response<Login> response) {

                            if (response.isSuccessful()) {
                                //  dialog.dismiss();
                                Login admin = response.body();
                                if (admin.getid() == 1 && admin.getUsername().equals("sadegh")) {
                                    List<String> cookieList = response.headers().values("Set-Cookie");
                                    sessionid = cookieList.get(0);
                                    expires = response.headers().value(0);
                                    headers.add(sessionid);
                                    headers.add(expires);

                                    Log.e(TAG, "onResponse: "+headers.get(0) +" \n "+headers.get(1) );
                                    Toast.makeText(context, "ادمین وارد شد" + response.body().getUsername(), Toast.LENGTH_SHORT).show();
                                   //
                                    //sessionid = (cookieList.get(0).split(";"))[0];
                                  //
                                }
                            } else {
                                //   dialog.dismiss();
                                Toast.makeText(context, "" + response.errorBody() + response.code(), Toast.LENGTH_SHORT).show();

                            }
                        }

                        @Override
                        public void onFailure(Call<Login> call, Throwable t) {
                            try {
                                Log.e("Tag", "error" + t.toString());

                                //    dialog.dismiss();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                } else {
                    Log.e("Tag", "error= Alert no internet");


                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
      //  return headers;
    }

    public static Record userInfo(Context context, Retrofit retrofit, int id) {
        if (retrofit == null) {
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
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client.build())
                    .build();

            Api apiInterface = retrofit.create(Api.class);



            try {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo.isConnected()) {
//                    final ProgressDialog dialog;
//                    dialog = new ProgressDialog(context);
//                    dialog.setMessage("Loading...");
//                    dialog.setCanceledOnTouchOutside(false);
//                    dialog.show();

                    Call<Record> registerCall = apiInterface.userInfo(id);
                    registerCall.enqueue(new retrofit2.Callback<Record>() {
                        @Override
                        public void onResponse(Call<Record> registerCall, retrofit2.Response<Record> response) {

                            if (response.isSuccessful()) {
                                //  dialog.dismiss();
                                user = response.body();
                                Log.e(TAG, "onResponse:  SUUUUUUUC" );
                                    Toast.makeText(context, "" + user.getUsername()+user.getPassword(), Toast.LENGTH_SHORT).show();

                            } else {
                                //   dialog.dismiss();
                                Log.e(TAG, "onResponse:  EROOOOOOOOR"+response.errorBody() +"\n "+ response.code() );
                                //Toast.makeText(context, "" + , Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Record> call, Throwable t) {
                            try {
                                Log.e("Tag", "error" + t.toString());

                                //    dialog.dismiss();
                            } catch (Resources.NotFoundException e) {
                                e.printStackTrace();
                            }
                        }

                    });

                } else {
                    Log.e("Tag", "error= Alert no internet");


                }
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }

        return user;
    }



}