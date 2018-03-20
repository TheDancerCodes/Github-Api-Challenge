package com.nabwera.github_api_challenge.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.model.GithubUsers;
import com.nabwera.github_api_challenge.util.ShareUtil;

/**
 * Created by nabwera on 26/08/2017.
 */

public class ProfileActivity extends AppCompatActivity {
    TextView userName, profileURL;
    ImageView imageView;

    String gitUserName;

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

        // Button that shares the users' profile when clicked
        Button shareButton = (Button) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareGithubUserProfile();
            }
        });

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
        collapsingToolbarLayout.setTitle("Java User: ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener(){
            boolean isShow = false;
            int scrollRange = -1;


            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset){

                // Adding the Github Username on the CollapsingBar Layout
                GithubUsers user = getIntent().getParcelableExtra("user");
                if (user != null){
                    gitUserName = user.getLogin();
                }

                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbarLayout.setTitle("Java User: " + gitUserName);
                    isShow = true;
                } else if (isShow){
                    collapsingToolbarLayout.setTitle("Java User: ");
                    isShow = false;
                }
            }
        });
    }

    // Method that connects to ShareUtil and enables sharing of data to other apps
    public void  shareGithubUserProfile(){
        GithubUsers user = getIntent().getParcelableExtra("user");

        String message = "Check out this awesome developer @" + user.getLogin() + ", " + user.getUrl();
        ShareUtil.shareCustomUtil(message, this);

    }

}
