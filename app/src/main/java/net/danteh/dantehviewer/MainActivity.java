package net.danteh.dantehviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MainActivity extends AppCompatActivity {
    WebView webView;
    EditText editText;
    int i = 0;
    public Retrofit retrofit = null;
    public final static String TAG = "webview";
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        webView = findViewById(R.id.wv);
        editText = findViewById(R.id.et);
        adminloger(context, retrofit);
        final String[] urls = {"https://www.all.ir/", "https://www.all.ir/%d8%b3%d8%a7%d9%86%d8%af%d8%a8%d8%a7%d8%b1-%d8%b3%d8%a7%d9%85%d8%b3%d9%88%d9%86%da%af-hw-j7591/", "https://www.google.com/"};
        webView.getSettings().setJavaScriptEnabled(true);

        editText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e(TAG, "What?!");
                Toast.makeText(context, "WTF", Toast.LENGTH_SHORT).show();
            }
        });
       // webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        webView.setWebViewClient(new WebViewClient() {

            public void onPageFinished(WebView view, String url) {

                Log.e(TAG, "onPageFinished: LOADED");

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        Toast.makeText(MainActivity.this, "یک امتیاز اضافه شد!", Toast.LENGTH_SHORT).show();
//                        if (i < urls.length) {
//                            webView.loadUrl(urls[i]);
//                            i++;
//                        }
//                    }
//
//                }, 5000);
            }
        });
        webView.loadUrl(urls[i]);
        i++;

    }

    public void adminloger(final Context context, Retrofit retrofit) {
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

//            Callback<Login> login = new Callback<Login>() {
//                @Override
//                public void onFailure(Call<Login> call, Throwable e) {
//                    Log.e("teeest", "EREORORROROROROR: "+e.getMessage() );
//                    e.printStackTrace();
//                }
//
//                @Override
//                public void onResponse(Call<Login> call, Response<Login> response){
//                    if(response.isSuccessful()){
//                        Log.e(TAG, "onResponse: "+response.body() );
//                        if (response.body().getUsername().equals("sadegh") )
//                            Toast.makeText(context, "Welcome admin!", Toast.LENGTH_SHORT).show();
////                        TinyDB tinydb = new TinyDB(rv.getContext());
////
////                        tinydb.putListObject(PRODUCTS,products);
////                        List<Product> n = new ArrayList<>();
////                        n = tinydb.getListObject(PRODUCTS,Product.class);
//                    }
//                    else
//                        Log.e(TAG, "RRROR ERORR ERRROOORRRR!" );
//                    Log.e(TAG, "onFailure: "+response.body() );
//                    Log.e(TAG, "onFailure: "+response.message() );
//                }
//            };

            JsonObject gsonObject = new JsonObject();
            try {
                JSONObject paramObject = new JSONObject();
                paramObject.put("username", "sadegh");
                paramObject.put("password", "tajik");
                JsonParser jsonParser = new JsonParser();
                gsonObject = (JsonObject) jsonParser.parse(paramObject.toString());
                Log.e("MY gson.JSON:  ", "AS PARAMETER  " + gsonObject);
//                Call<Login> admincall = apiInterface.adminlogin(paramObject);
//                admincall.enqueue(login);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                ConnectivityManager connectivityManager = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
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

                            try {
                                //print respone
                               // Log.e(" Full json gson => ", new Gson().toJson(response));
//                                JSONObject jsonObj = new JSONObject(new Gson().toJson(response).toString());
//                                Log.e(" responce => ", jsonObj.getJSONObject("body").toString());

                                if (response.isSuccessful()) {
                                  //  dialog.dismiss();
                                    Login admin = response.body();
                                    if (admin.getid() == 1 && admin.getUsername().equals("sadegh"))
                                        Toast.makeText(MainActivity.this, "ادمین وارد شد"+ response.body().getUsername(), Toast.LENGTH_SHORT).show();

                                }
                                else {
                                 //   dialog.dismiss();
                                    Toast.makeText(MainActivity.this, ""+response.errorBody() + response.code(), Toast.LENGTH_SHORT).show();

                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                                try {
                                    Log.e("Tag", "error=" + e.toString());

                                //    dialog.dismiss();
                                } catch (Resources.NotFoundException e1) {
                                    e1.printStackTrace();
                                }

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
    }
}
