package com.example.carplate;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DeleteService {
    @GET("/delete/{scar}")
    Call<PostResult> getData(@Path("scar") String scar);
}
