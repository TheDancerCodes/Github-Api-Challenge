package com.nabwera.github_api_challenge.ui;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.nabwera.github_api_challenge.GitHubApplication;
import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.api.model.GithubUsers;
import com.nabwera.github_api_challenge.api.model.GithubUsersResponse;
import com.nabwera.github_api_challenge.api.service.GithubClient;
import com.nabwera.github_api_challenge.ui.adapter.GithubUsersAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private final String KEY_RECYCLER_STATE = "recycler_state";
    private RecyclerView recyclerView;
    private GithubUsersAdapter adapter;
//    private List<GithubUsers> usersList = new ArrayList<>();
    ProgressDialog progressDialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String LOG_TAG = GithubUsersAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        if (savedInstanceState != null && savedInstanceState.containsKey(KEY_RECYCLER_STATE)) {
            restorePreviousState(savedInstanceState); // Restore data found in the Bundle
        } else {
            //Loading User Data
            fetchGithubUsers();
        }

    }

    private void initViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Fetching Github Users...");
        progressDialog.setCancelable(false);

        recyclerView = findViewById(R.id.recycler_view);

        adapter = new GithubUsersAdapter(this);

        // Setting up the Orientation
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout = findViewById(R.id.main_content);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            initViews();
            Toast.makeText(MainActivity.this, "Users Refreshed", Toast.LENGTH_SHORT).show();
        });


    }

    // Handling Loading of JSON Data
    private void fetchGithubUsers() {
        progressDialog.show();
        Retrofit retrofit = GitHubApplication.getRetrofitObject();

        // Simple REST adapter which points the GitHub API endpoint.
        GithubClient client = retrofit.create(GithubClient.class);

        Call<GithubUsersResponse> call = client.listOfJavaDevs("language:java location:lagos");
        call.enqueue(new Callback<GithubUsersResponse>() {
            @Override
            public void onResponse(@NonNull Call<GithubUsersResponse> call,
                                   @NonNull Response<GithubUsersResponse> response) {
                adapter.submitList(response.body().getItems());
                adapter.notifyDataSetChanged();

                recyclerView.smoothScrollToPosition(0);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                progressDialog.dismiss();

                //Log.d(LOG_TAG, "Number of users received: " + usersList.size());
            }

            @Override
            public void onFailure(@NonNull Call<GithubUsersResponse> call, @NonNull Throwable t) {
                Log.d("Error", t.getMessage());

                // Code to check for network connectivity
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(false);
                builder.setTitle("No Internet");
                builder.setMessage("Internet is required. Please Retry.");

                builder.setNegativeButton("Cancel", (dialog, which) -> {
                    dialog.dismiss();
                    finish();
                });

                builder.setPositiveButton("Retry", (dialog, which) -> {
                    dialog.dismiss();
                    fetchGithubUsers();
                });
                AlertDialog dialog = builder.create(); // calling builder.create after adding buttons
                dialog.show();
                Snackbar.make(findViewById(android.R.id.content), "Network Unavailable!", Snackbar.LENGTH_LONG)
                        .setActionTextColor(Color.RED)
                        .show();

            }
        });

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        //outState.putParcelableArrayList(KEY_RECYCLER_STATE, (ArrayList<? extends Parcelable>) usersList);
        super.onSaveInstanceState(outState);
    }

    public void restorePreviousState(Bundle savedInstanceState) {
        // restore RecyclerView state
        if (savedInstanceState != null) {

            ArrayList<GithubUsers> resultArrayList = savedInstanceState.getParcelableArrayList(KEY_RECYCLER_STATE);
            if (resultArrayList != null) {
                adapter.submitList(resultArrayList);
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
