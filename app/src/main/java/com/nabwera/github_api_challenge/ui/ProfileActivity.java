package com.nabwera.github_api_challenge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nabwera.github_api_challenge.R;

/**
 * Created by nabwera on 26/08/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    TextView userName, profileURL;
    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //  Display the back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // This method handles the swivel/drop of the Collapsing Toolbar.
        // This shows/hides the toolbar title on scroll.
        initCollapsingToolbar();

        imageView = (ImageView) findViewById(R.id.profile_photo_header);
        userName = (TextView) findViewById(R.id.userName);
        profileURL = (TextView) findViewById(R.id.profileURLink);

        // Test whether Intent received from a particular Activity has the required data.
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.hasExtra("username")){

            String profilePic = getIntent().getExtras().getString("profile_photo");
            String profileName = getIntent().getExtras().getString("username");
            String profileLink = getIntent().getExtras().getString("profileURL");

            // Setting up data [Strings] to appropriate views.
            Glide.with(this)
                    .load(profilePic)
                    .placeholder(R.drawable.load)
                    .into(imageView);

            userName.setText(profileName);
            profileURL.setText(profileLink);
        } else{
            // In case there is no data in the Intent.
            Toast.makeText(this, "No API Data!", Toast.LENGTH_SHORT).show();
        }

    }

    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset){
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle(getString(R.string.user_details));
                    isShow = true;
                } else if (isShow){
                    collapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
