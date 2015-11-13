package com.android.danmartin.danmartininterview2.main.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.danmartin.danmartininterview2.main.listeners.ImageOnClickListener;
import com.android.danmartin.danmartininterview2.main.model.FlickrObject;
import com.android.danmartin.danmartininterview2.main.views.ImageListView;
import com.android.danmartin.danmartininterview2.main.widget.CustomViewHolder;

import java.util.ArrayList;

/**
 * Created by Dan on 4/11/2015.
 */
public class ImageAdapter extends RecyclerView.Adapter<CustomViewHolder> {


    private int mLayout = ImageListView.LAYOUT_LIST; //Used to determine which layout file to use. Default to linear
    // list view

    private ArrayList<FlickrObject> mData = new ArrayList<>();
    private ImageOnClickListener mListener = new ImageOnClickListener();

    private ImageAdapter() {
    }

    public ImageAdapter(int layout) {
        mLayout = layout;
    }

    public void setData(ArrayList<FlickrObject> data) {
        if (data != null) {
            mData = data;
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        //We don't have different view types so we always return 1
        return 1;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        ImageListView view = new ImageListView(context);
        view.setLayout(context, mLayout);
        view.setOnClickListener(mListener);

        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }


    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        FlickrObject data = mData.get(position);
        ((ImageListView) holder.itemView).setData(data);
    }
}

