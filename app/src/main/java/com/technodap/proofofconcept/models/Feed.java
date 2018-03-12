package com.technodap.proofofconcept.models;

import java.util.List;
import com.squareup.moshi.Json;

public class Feed<RowItem> {
    @Json(name = "title")
    public String title;
    @Json(name = "rows")
    public List<RowItem> rows;
}
