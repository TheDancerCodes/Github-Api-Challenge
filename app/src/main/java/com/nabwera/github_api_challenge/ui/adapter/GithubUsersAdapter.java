package com.nabwera.github_api_challenge.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.api.model.GithubUsers;
import com.nabwera.github_api_challenge.ui.ProfileActivity;

/**
 * Created by nabwera on 24/08/2017.
 */

public class GithubUsersAdapter extends ListAdapter<GithubUsers, GithubUsersAdapter.MyViewHolder> {

    private Context mContext;

    private static DiffUtil.ItemCallback<GithubUsers> COMPARATOR = new DiffUtil.ItemCallback<GithubUsers>() {
        @Override
        public boolean areItemsTheSame(GithubUsers oldItem, GithubUsers newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(GithubUsers oldItem, GithubUsers newItem) {
            return oldItem.equals(newItem);
        }
    };

    public GithubUsersAdapter(Context mContext) {
        super(COMPARATOR);
        this.mContext = mContext;
    }

    // Setting bind data to views
    @Override
    public GithubUsersAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.git_user_card, viewGroup, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final GithubUsersAdapter.MyViewHolder viewHolder, int position) {
        GithubUsers user = getItem(position);
        viewHolder.username.setText(user.getLogin());
        viewHolder.profileURL.setText(user.getAvatarUrl());

        // Using Glide to load the Profile Photo
        Glide.with(mContext)
                .load(user.getAvatarUrl())
                .into(viewHolder.profile_photo);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView username, profileURL;
        public ImageView profile_photo;

        MyViewHolder(View view) {
            super(view);

            username = view.findViewById(R.id.username);
            profileURL = view.findViewById(R.id.profileURL);
            profile_photo = view.findViewById(R.id.profile_photo);

            // Handle an onClick listener on every card in the grid layout of the RecyclerView.
            view.setOnClickListener(v -> {
                int pos = getAdapterPosition();
                if (pos != RecyclerView.NO_POSITION) {
                    GithubUsers clickedDataItem = getItem(pos);
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    intent.putExtra("username", clickedDataItem.getLogin());
                    intent.putExtra("profile_photo", clickedDataItem.getAvatarUrl());
                    intent.putExtra("profileURL", clickedDataItem.getUrl());
                    intent.putExtra("id", clickedDataItem.getId());
                    intent.putExtra("user", clickedDataItem);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                    Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getLogin(), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}
