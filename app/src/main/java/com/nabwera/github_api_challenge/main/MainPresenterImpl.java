package com.nabwera.github_api_challenge.main;

import com.nabwera.github_api_challenge.model.GithubUsersResponse;
import com.nabwera.github_api_challenge.service.GithubService;

import java.util.List;

/**
 * Created by TheDancerCodes on 21/03/2018.
 */

public class MainPresenterImpl implements MainPresenter, MainInteractor.OnPostFetchCallback {

    private MainInteractor mMainInteractor;

    private MainView mMainView;
    private GithubUsersResponse mGithubUsersResponse;

     MainPresenterImpl(MainView mainView, GithubUsersResponse githubUsersResponse) {
        mMainInteractor = new MainInteractorImpl(GithubService.getAPI());
        mMainView = mainView;
        mGithubUsersResponse = githubUsersResponse;
    }

    @Override
    public void onFetchUsers() {
         mMainView.showProgress();
         mMainInteractor.fetchPosts(this);

    }

    @Override
    public void onSuccess(List<GithubUsersResponse> githubUsersList) {
         if (githubUsersList != null) {
             mMainView.hideProgress();
             mMainView.showUsers(githubUsersList);
         }

    }

    @Override
    public void onFailure(String error) {
        mMainView.showNetworkError(error);
        mMainView.hideProgress();
//        mMainView.showNoPostLabel();

    }
}
