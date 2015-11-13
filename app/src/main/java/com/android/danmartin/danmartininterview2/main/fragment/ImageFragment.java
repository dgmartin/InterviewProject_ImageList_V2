package com.android.danmartin.danmartininterview2.main.fragment;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.danmartin.danmartininterview2.R;
import com.android.danmartin.danmartininterview2.main.model.FlickrObject;
import com.android.danmartin.danmartininterview2.main.network.VolleyUtils;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import android.support.v7.graphics.Palette;
import android.widget.ImageView;

/**
 * Created by Dan on 4/11/2015.
 */
public class ImageFragment extends Fragment implements Response.Listener<Bitmap>, Response.ErrorListener {
    public static final String FLICKR_OBJECT = "FLICKR_OBJECT";

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_image;
        View view = inflater.inflate(layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        retrieve data from arguments and load image into view.
        if (getArguments() != null && getArguments().containsKey(FLICKR_OBJECT)) {
            FlickrObject data = (FlickrObject) getArguments().getSerializable(FLICKR_OBJECT);
            String imageUrl = data.getMedia();

            //Because of the way the api works we swap the "m" to "h"
            // to get a larger image for displaying full screen. This is probably not the best way to handle this but
            // I felt this was fine for this project.
            imageUrl=imageUrl.replace("_m.jpg","_h.jpg");

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                view.findViewById(R.id.image).setTransitionName(data.getMedia());
            }

//          Normally I would use a standard NetworkImageView here but because I am using the pallette feature this
//          method allows me to grab the bitmap before it goes into the ImageView
            ImageRequest request = new ImageRequest(imageUrl, this, 0, 0, null, this);
            VolleyUtils.getSharedInstance(getActivity()).addToRequestQueue(request);
        }
    }

    @Override
    public void onResponse(Bitmap bitmap) {
        ImageView image = (ImageView) getView().findViewById(R.id.image);
        image.setImageBitmap(bitmap);
        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
            public void onGenerated(Palette palette) {
                View view = getView();
                if (view != null) {
                    int color = palette.getLightVibrantColor(Color.BLACK);
                    getView().setBackgroundColor(color);
                }
            }
        });
    }

    @Override
    public void onErrorResponse(VolleyError error) {
//        In the event the image doesn't load we have the option to load a placeholder.  I did not due to lack of an asset
//        ImageView image = (ImageView) getView().findViewById(R.id.image);
//                            image.setImageResource(R.drawable.image_load_error);
    }
}
