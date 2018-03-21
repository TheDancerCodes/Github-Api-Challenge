package com.nabwera.github_api_challenge.main;

import com.nabwera.github_api_challenge.model.GithubUsers;
import com.nabwera.github_api_challenge.model.GithubUsersResponse;
import com.nabwera.github_api_challenge.service.GithubAPI;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TheDancerCodes on 21/03/2018.
 */


// MainInteractorImpl class contains pure java code with core business logic only.
// There is no single android component or context existing in it,
// which makes it easy for testing. Also, the code becomes more simpler to understand.

public class MainInteractorImpl implements MainInteractor {

    // API Interface
    private GithubAPI mGithubAPI;

    // Constructor
    public MainInteractorImpl(final GithubAPI githubAPI) {
        mGithubAPI = githubAPI;
    }

    @Override
    public void fetchPosts(final MainInteractor.OnPostFetchCallback callback) {

        mGithubAPI.listOfJavaDevs().enqueue(new Callback<GithubUsersResponse>() {
            @Override
            public void onResponse(Call<GithubUsersResponse> call,
                                   Response<GithubUsersResponse> response) {
                List<GithubUsersResponse> githubUsersList = (List<GithubUsersResponse>) response.body();
                callback.onSuccess(githubUsersList);

            }

            @Override
            public void onFailure(Call<GithubUsersResponse> call, Throwable t) {
                callback.onFailure(t.getMessage());

            }
        });

    }
}
