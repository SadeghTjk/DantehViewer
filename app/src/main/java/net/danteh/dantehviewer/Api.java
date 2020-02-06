package net.danteh.dantehviewer;


import com.google.gson.JsonObject;

import net.danteh.Record;

import org.json.JSONObject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {
    @Headers("Content-Type: application/json")
    @POST("api.php/login")
    Call<Login> adminlogin(@Body JsonObject body);

    @GET("wp-json/wc/v2/products")
    Call<List<Record>> getAllusers();
}
