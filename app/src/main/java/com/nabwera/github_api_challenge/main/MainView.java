package com.nabwera.github_api_challenge.main;

import com.nabwera.github_api_challenge.base.BaseView;
import com.nabwera.github_api_challenge.model.GithubUsers;
import com.nabwera.github_api_challenge.model.GithubUsersResponse;

import java.util.List;

/**
 * Created by TheDancerCodes on 21/03/2018.
 */

public interface MainView extends BaseView {

    // The methods below will interact with view layer to update user interface as needed.

    /**
     * show users
     *
     * @param githubUsersList : show list of users
     */
    void showUsers(List<GithubUsersResponse> githubUsersList);

    /**
     * show network error
     *
     * @param error : error message
     */
    void showNetworkError(String error);
}
