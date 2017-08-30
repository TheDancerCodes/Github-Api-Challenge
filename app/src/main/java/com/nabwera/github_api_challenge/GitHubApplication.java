package com.nabwera.github_api_challenge;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Thomas Kioko
 */

public class GitHubApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    /**
     * Helper method that instantiates retorfit object
     * @return an instance of retrofit
     */
    public static Retrofit getRetrofitObject(){
        // Build Retrofit Objects
         Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/search/")
                .addConverterFactory(GsonConverterFactory.create());

        return builder.build();
    }
}
