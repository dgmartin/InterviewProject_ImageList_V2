package com.android.danmartin.danmartininterview.main.listeners;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.android.danmartin.danmartininterview.R;
import com.android.danmartin.danmartininterview.main.activities.MainActivity;
import com.android.danmartin.danmartininterview.main.fragment.ImageFragment;
import com.android.danmartin.danmartininterview.main.model.FlickrObject;
import com.android.danmartin.danmartininterview.main.views.ImageListView;

/**
 * Created by Dan on 4/11/2015.
 */
public class ImageOnClickListener implements View.OnClickListener {
    @Override
    public void onClick(View view) {
        MainActivity activity = (MainActivity) view.getContext();
        Fragment frag = new ImageFragment();

        Bundle bundle = new Bundle();
        FlickrObject data = ((ImageListView) view).getData();
        bundle.putSerializable(ImageFragment.FLICKR_OBJECT, data);
        frag.setArguments(bundle);

        String tag = frag.getClass().getSimpleName();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment, frag, tag)
                .addToBackStack(tag);
        transaction.commit();
    }
}
