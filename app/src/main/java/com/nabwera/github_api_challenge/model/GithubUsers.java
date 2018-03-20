package com.nabwera.github_api_challenge.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by nabwera on 24/08/2017.
 */

public class GithubUsers implements Parcelable {

    @SerializedName("login")
    @Expose
    private String login;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("avatar_url")
    @Expose
    private String avatarUrl;
    @SerializedName("html_url")
    @Expose
    private String url;

    public GithubUsers(String login, int id, String avatarUrl, String url){
        this.login = login;
        this.id = id;
        this.avatarUrl = avatarUrl;
        this.url = url;
    }

    public GithubUsers(Parcel in){
        login = in.readString();
        id = in.readInt();
        avatarUrl = in.readString();
        url = in.readString();
    }



    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
    }


    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public String getAvatarUrl() {
        return avatarUrl;
    }


    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }


    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeString(login);
        dest.writeInt(id);
        dest.writeString(url);
        dest.writeString(avatarUrl);
    }

    public static final Parcelable.Creator<GithubUsers> CREATOR = new Parcelable.Creator<GithubUsers>() {
        @Override
        public GithubUsers createFromParcel(Parcel in) {
            return new GithubUsers(in);
        }

        @Override
        public GithubUsers[] newArray(int size) {
            return new GithubUsers[size];
        }
    };

}