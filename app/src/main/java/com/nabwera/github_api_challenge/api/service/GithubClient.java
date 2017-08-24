package com.nabwera.github_api_challenge.api.service;

import retrofit2.http.GET;

/**
 * Created by nabwera on 24/08/2017.
 */

public interface GithubClient {

    @GET("users?q=location:lagos+language:java")
    Call<List<GithubUsers>> listOfJavaDevs(String devs);
}
