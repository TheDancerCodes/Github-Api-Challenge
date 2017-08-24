package com.nabwera.github_api_challenge.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.nabwera.github_api_challenge.R;
import com.nabwera.github_api_challenge.api.model.GithubUsers;
import com.nabwera.github_api_challenge.api.service.GithubClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Listview to the display the Github users
        listView = (ListView) findViewById(R.id.list_item_users_text);

        // Build Retrofit Objects
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("https://api.github.com/search/")
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        // Simple REST adapter which points the GitHub API endpoint.
        GithubClient client = retrofit.create(GithubClient.class);

        // Fetch a list of the Github User.
        Call<List<GithubUsers>> call = client.listOfJavaDevs("");

        // Execute the call asynchronously. Get a positive or negative callback.
        call.enqueue(new Callback<List<GithubUsers>>() {
            @Override
            public void onResponse(Call<List<GithubUsers>> call, Response<List<GithubUsers>> response) {

                // Successful Network Call. Received a response
                List<GithubUsers> java_devs = response.body();

                // pass the data to the list view
                listView.setAdapter(new GithubUsersAdapter(MainActivity.this, java_devs));
            }

            @Override
            public void onFailure(Call<List<GithubUsers>> call, Throwable t) {

                // Failed network call
                Toast.makeText(MainActivity.this, "Network Error! :(", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
