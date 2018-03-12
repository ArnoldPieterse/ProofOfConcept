package com.technodap.proofofconcept.async_tasks;

import android.os.AsyncTask;
import android.util.Log;

import com.technodap.proofofconcept.interfaces.AsyncTaskCompleteListener;
import com.technodap.proofofconcept.interfaces.FeedApi;
import com.technodap.proofofconcept.models.Feed;
import com.technodap.proofofconcept.models.RowItem;

import retrofit2.Call;
import retrofit2.Response;


public class GetFeedAsync extends AsyncTask<String, String, Feed> {
    private FeedApi api;
    private AsyncTaskCompleteListener<Feed, String> listener;
    private String errorMessage;
    private final String TAG = "GetFeedAsync";

    public GetFeedAsync(FeedApi api, AsyncTaskCompleteListener<Feed, String> listener){
        this.listener = listener;
        this.api = api;
    }

    @Override
    protected Feed doInBackground(String... params) {
        try{
            Call<Feed<RowItem>> call = api.feed();
            Response<Feed<RowItem>> resp = call.execute();

            if(!resp.body().title.isEmpty()){
                return resp.body();
            } else {
                return null;
            }
        } catch (Exception e){
            Log.i(TAG, "Error: " + e);
            errorMessage = "Error: " + e; //Set the error message in order to give user feedback
            return null;
        }
    }

    @Override
    protected void onPostExecute(Feed result) {
        listener.onTaskComplete(result, errorMessage);
    }
}
