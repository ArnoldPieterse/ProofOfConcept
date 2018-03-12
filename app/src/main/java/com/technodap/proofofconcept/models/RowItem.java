package com.technodap.proofofconcept.models;

import com.squareup.moshi.Json;

public class RowItem {
    @Json(name = "title")
    public String title;
    @Json(name = "description")
    public String description;
    @Json(name = "imageHref")
    public Object imageHref;
}
