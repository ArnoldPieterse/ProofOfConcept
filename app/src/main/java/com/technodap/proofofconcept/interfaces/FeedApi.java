package com.technodap.proofofconcept.interfaces;

import com.technodap.proofofconcept.models.Feed;
import com.technodap.proofofconcept.models.RowItem;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 ////////////
 // API
 ////////////
 */

public interface FeedApi {
    @GET("/s/2iodh4vg0eortkl/facts.json")
    Call<Feed<RowItem>> feed();
}
