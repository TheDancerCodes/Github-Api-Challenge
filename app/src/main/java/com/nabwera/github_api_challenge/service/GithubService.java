package com.nabwera.github_api_challenge.service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by TheDancerCodes on 20/03/2018.
 */

public class GithubService {

    private static Retrofit retrofit = null;


    /**
     * This method creates a new instance of the API interface.
     *
     * @return - The API interface
     */
    public static GithubAPI getAPI() {
        String BASE_URL = "https://api.github.com/search/";

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(GithubAPI.class);
    }
}
