package com.nabwera.github_api_challenge.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.model.GithubUsers;
import com.nabwera.github_api_challenge.main.ProfileActivity;


import java.util.List;

/**
 * Created by nabwera on 24/08/2017.
 */

public class GithubUsersAdapter extends RecyclerView.Adapter<GithubUsersAdapter.MyViewHolder> {

    private Context context;

    private List<GithubUsers> gitUsersList;

    public GithubUsersAdapter(List<GithubUsers> gitUsers){
        this.gitUsersList = gitUsers;
    }

    // Setting bind data to views
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        context = viewGroup.getContext();
        View view = LayoutInflater.from(context)
                .inflate(R.layout.git_user_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {

        int pos = viewHolder.getAdapterPosition();

        GithubUsers githubUsers = gitUsersList.get(pos);

        if (githubUsers != null) {

            viewHolder.username.setText(gitUsersList.get(position).getLogin());
            viewHolder.profileURL.setText(gitUsersList.get(position).getAvatarUrl());

            // Using Glide to load the Profile Photo
            Glide.with(context)
                    .load(gitUsersList.get(position).getAvatarUrl())
                    .placeholder(R.drawable.load)
                    .into(viewHolder.profile_photo);
        }
    }

    @Override
    public int getItemCount(){
        return gitUsersList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView username,profileURL;
        public ImageView profile_photo;

        public MyViewHolder(View view) {
            super(view);

            username = (TextView) view.findViewById(R.id.username);
            profileURL = (TextView) view.findViewById(R.id.profileURL);
            profile_photo = (ImageView) view.findViewById(R.id.profile_photo);

            // Handle an onClick listener on every card in the grid layout of the RecyclerView.
            view.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        GithubUsers clickedDataItem = gitUsersList.get(pos);
                        Intent intent = new Intent(context, ProfileActivity.class);
                        intent.putExtra("username", gitUsersList.get(pos).getLogin());
                        intent.putExtra("profile_photo", gitUsersList.get(pos).getAvatarUrl());
                        intent.putExtra("profileURL", gitUsersList.get(pos).getUrl());
                        intent.putExtra("id", gitUsersList.get(pos).getId());
                        intent.putExtra("user", clickedDataItem);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
