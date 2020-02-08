package net.danteh.dantehviewer;


import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {
    @Headers("Content-Type: application/json")
    @POST("api.php/login")
    Call<Login> adminlogin(@Body JsonObject body);

  //  @GET("wp-json/wc/v2/products")
  //  Call<List<Users>> getAllusers();

    @GET("api.php/records/users/{id}")
    Call<Record> userInfo(@Path("id") int id);

    @PUT("api.php/records/users/{id}")
    Call<Integer> updatePoints(@Path("id") int id, @Body JsonObject body);

}
