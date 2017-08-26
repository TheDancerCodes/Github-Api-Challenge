package com.nabwera.github_api_challenge.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.widget.ListView;
import android.widget.Toast;

import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.api.model.GithubUsers;
import com.nabwera.github_api_challenge.api.model.GithubUsersResponse;
import com.nabwera.github_api_challenge.api.service.GithubClient;
import com.nabwera.github_api_challenge.ui.adapter.GithubUsersAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    //    private ListView listView;
    private RecyclerView recyclerView;
    private GithubUsersAdapter adapter;
    private List<GithubUsers> usersList;
    ProgressDialog progessDialog;

    private SwipeRefreshLayout swipeRefreshLayout;
    public static final String LOG_TAG = GithubUsersAdapter.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.main_content);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initViews();
                Toast.makeText(MainActivity.this, "Users Refreshed", Toast.LENGTH_SHORT).show();
            }
        });

//        // Listview to the display the Github users
//        listView = (ListView) findViewById(R.id.paginator_list);
//
//        // Build Retrofit Objects
//        final Retrofit.Builder builder = new Retrofit.Builder()
//                .baseUrl("https://api.github.com/search/")
//                .addConverterFactory(GsonConverterFactory.create());
//
//        Retrofit retrofit = builder.build();
//
//        // Simple REST adapter which points the GitHub API endpoint.
//        GithubClient client = retrofit.create(GithubClient.class);
//
//        // Fetch a list of the Github User.
//        Call<List<GithubUsers>> call = client.listOfJavaDevs("language:java location:lagos");
//
//        // Execute the call asynchronously. Get a positive or negative callback.
//        call.enqueue();
    }

    public Activity getActivity() {
        Context context = this;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }

    private void initViews() {
        progessDialog = new ProgressDialog(this);
        progessDialog.setMessage("Fetting Github Users...");
        progessDialog.setCancelable(false);
        progessDialog.show();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        usersList = new ArrayList<>();
        adapter = new GithubUsersAdapter(this, usersList);

        // Setting up the Orientation
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        //Loading User Data
        loadJSON();
    }

    // Handling Loading of JSON Data
    private void loadJSON() {
        try{

        // Build Retrofit Objects
        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/search/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // Simple REST adapter which points the GitHub API endpoint.
        GithubClient client = retrofit.create(GithubClient.class);

        Call<GithubUsersResponse> call = client.listOfJavaDevs("language:java location:lagos");
        call.enqueue(new Callback<GithubUsersResponse>() {
            @Override
            public void onResponse(Call<GithubUsersResponse> call, Response<GithubUsersResponse> response) {
                List<GithubUsers> gitUsers = response.body().getItems();
                recyclerView.setAdapter(new GithubUsersAdapter(getApplicationContext(), gitUsers));
                recyclerView.smoothScrollToPosition(0);
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
                progessDialog.dismiss();

                Log.d(LOG_TAG, "Number of users received: " + gitUsers.size());
            }

            @Override
            public void onFailure(Call<GithubUsersResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
                Toast.makeText(MainActivity.this, "Error Fetching User Data :(", Toast.LENGTH_SHORT).show();
            }
        });
    }catch(Exception e){
        Log.d("Error", e.getMessage());
        Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
    }

}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
