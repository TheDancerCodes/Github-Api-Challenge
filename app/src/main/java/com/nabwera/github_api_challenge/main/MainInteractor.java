package com.nabwera.github_api_challenge.main;

import com.nabwera.github_api_challenge.base.BasePresenter;
import com.nabwera.github_api_challenge.model.GithubUsers;

import java.util.List;

/**
 * Created by TheDancerCodes on 21/03/2018.
 */

public interface MainInteractor {
    /**
     * fetch posts from api
     *
     * @param callback : callback for api listener
     */
    void fetchPosts(OnPostFetchCallback callback);

    interface OnPostFetchCallback {

        /**
         * if api is hit successfully
         *
         * @param githubUsersList : list of users
         */
        void onSuccess(List<GithubUsers> githubUsersList);


        /**
         * if api hit fails
         *
         * @param error : error message
         */
        void onFailure(String error);
    }
}
