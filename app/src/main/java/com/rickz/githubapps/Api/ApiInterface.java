package com.rickz.githubapps.Api;

import com.rickz.githubapps.Model.ResponseUser;
import com.rickz.githubapps.Model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("/search/users")
    @Headers("Authorization: token bca7a7f91d7eb9c74289b6a98044e2117fe1da2d")
    Call<ResponseUser> getListUser(@Query("q") String username);

    @GET("/users/{username}")
    @Headers("Authorization: token bca7a7f91d7eb9c74289b6a98044e2117fe1da2d")
    Call<Users> getDetailUser(@Path("username") String username);

    @GET("/users/{username}/followers")
    @Headers("Authorization: token bca7a7f91d7eb9c74289b6a98044e2117fe1da2d")
    Call<List<Users>> getListFollowers(@Path("username") String username);

    @GET("/users/{username}/following")
    @Headers("Authorization: token bca7a7f91d7eb9c74289b6a98044e2117fe1da2d")
    Call<List<Users>> getListFollowing(@Path("username") String username);
}
