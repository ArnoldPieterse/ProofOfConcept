package com.technodap.proofofconcept.activities;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.technodap.proofofconcept.R;
import com.technodap.proofofconcept.adapters.FeedAdapter;
import com.technodap.proofofconcept.async_tasks.GetFeedAsync;
import com.technodap.proofofconcept.interfaces.AsyncTaskCompleteListener;
import com.technodap.proofofconcept.interfaces.FeedApi;
import com.technodap.proofofconcept.models.Feed;
import com.technodap.proofofconcept.models.RowItem;

import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class FeedActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(R.layout.activity_feed);
        swipeContainer = findViewById(R.id.swipeContainer);
        recyclerView = findViewById(R.id.contentList);

        refreshFeed(); //Initialize the feed

        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshFeed(); //Refresh the feed on drag down
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

    }

    private void refreshFeed(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dl.dropboxusercontent.com")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        new GetFeedAsync(retrofit.create(FeedApi.class), new FeedCompleteListener()).execute(); //Initialize the async task which will call its complete listener once completed
    }

    private class FeedCompleteListener implements AsyncTaskCompleteListener<Feed, String> {
        @Override
        public void onTaskComplete(Feed feed, String message){
            if(feed != null) {
                setTitle(feed.title); //Setting the activity title
                FeedAdapter adapter = new FeedAdapter(feed.rows, activity); //Initialize the adapter with the latest feed data.
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                swipeContainer.setRefreshing(false); //Remove the loading spinner
            } else {
                Toast.makeText(activity, message, Toast.LENGTH_LONG).show(); //Display error feedback, this could include connection errors.
                swipeContainer.setRefreshing(false);
            }
        }
    }
}
