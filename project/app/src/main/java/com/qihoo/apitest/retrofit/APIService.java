package com.qihoo.apitest.retrofit;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by caiyongjian on 16-11-4.
 */

public interface APIService {
    @Headers({
            "Accept: application/vnd.github.v3.full+json",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("users/{user}/repos")
    Call<List<Repo>> loadRepo(@Path("user") String user);
}
