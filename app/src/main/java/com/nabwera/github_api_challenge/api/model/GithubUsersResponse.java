package com.nabwera.github_api_challenge.api.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by nabwera on 25/08/2017.
 */

public class GithubUsersResponse {

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private List<GithubUsers> items = null;


    public Integer getTotalCount() {
        return totalCount;
    }


    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }


    public Boolean getIncompleteResults() {
        return incompleteResults;
    }


    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }


    public List<GithubUsers> getItems() {
        return items;
    }


    public void setItems(List<GithubUsers> items) {
        this.items = items;
    }

    public int size(){
        return items.size();
    }
}
