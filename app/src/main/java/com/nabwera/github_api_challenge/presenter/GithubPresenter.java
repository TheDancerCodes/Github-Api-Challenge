package com.nabwera.github_api_challenge.presenter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.nabwera.github_api_challenge.model.GithubUsers;
import com.nabwera.github_api_challenge.model.GithubUsersResponse;
import com.nabwera.github_api_challenge.service.GithubService;
import com.nabwera.github_api_challenge.ui.MainActivity;
import com.nabwera.github_api_challenge.ui.adapter.GithubUsersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by TheDancerCodes on 20/03/2018.
 */

public class GithubPresenter {

    private GithubService githubService;
    Context mContext;

    public GithubPresenter(Context context) {
        this.mContext = context;

        if(this.githubService == null) {
            this.githubService = new GithubService();
        }
    }

    public void getGithubUsers(final MainActivity mainActivity) {
        githubService
                .getAPI()
                .listOfJavaDevs()
                .enqueue(new Callback<GithubUsersResponse>() {
                    @Override
                    public void onResponse(Call<GithubUsersResponse> call,
                                           Response<GithubUsersResponse> response) {

                        List<GithubUsers> gitUsersList = response.body().getItems();

                        if (gitUsersList != null) {
//                            RecyclerView.Adapter adapter = new GithubUsersAdapter(gitUsersList, mContext);
//                            recyclerView.setAdapter(adapter);
                            mainActivity.onGithubResultsSuccessful((ArrayList<GithubUsers>) gitUsersList);
                        }

                    }

                    @Override
                    public void onFailure(Call<GithubUsersResponse> call, Throwable t) {

                        Toast.makeText(mContext, t.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });
    }


}
