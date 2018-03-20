package com.nabwera.github_api_challenge.service;

import com.nabwera.github_api_challenge.model.GithubUsersResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by nabwera on 24/08/2017.
 */

// Interface containing the Endpoint to query the API.
public interface GithubClient {

    @GET("users")
    Call<GithubUsersResponse> listOfJavaDevs(@Query("q") String devs_params);
}