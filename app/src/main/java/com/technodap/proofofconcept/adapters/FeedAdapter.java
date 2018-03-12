package com.technodap.proofofconcept.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.technodap.proofofconcept.R;
import com.technodap.proofofconcept.models.RowItem;

import java.util.List;

public class FeedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<RowItem> rowItems;
    private Activity activity;

    private final String TAG = "FeedAdapter";

    public FeedAdapter(List<RowItem> rowItems, Activity activity){
        this.rowItems = rowItems;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_item, parent, false);
        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final RowItem rowItem = rowItems.get(position);
        try{
            final MainViewHolder mainViewHolder = (MainViewHolder)holder;
            mainViewHolder.title.setText(rowItem.title);
            mainViewHolder.description.setText(rowItem.description);
            if(rowItem.imageHref != null) //If the image URL is null then the placeholder image will be removed. If the image failed to load, then show the error image.
                Glide.with(activity).load(rowItem.imageHref).apply(new RequestOptions().error(R.drawable.ic_error_black_24dp).placeholder(R.drawable.image_placeholder)).into(mainViewHolder.previewImage);
            else
                mainViewHolder.previewImage.setImageDrawable(null); //Removing the placeholder image.
        } catch (Exception e){
            Log.d(TAG, "Error: " + e);
        }
    }

    @Override
    public int getItemCount() {
        if(rowItems != null){
            return rowItems.size();
        } else {
            return 0;
        }
    }

    private class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView description;
        private ImageView previewImage;
        private MainViewHolder(View v){
            super(v);
            title = v.findViewById(R.id.itemTitle);
            description = v.findViewById(R.id.itemDescription);
            previewImage = v.findViewById(R.id.itemImage);
        }
    }
}
