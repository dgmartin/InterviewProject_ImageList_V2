package com.android.danmartin.danmartininterview.main.fragment;

import android.content.Context;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.danmartin.danmartininterview.R;
import com.android.danmartin.danmartininterview.main.adapters.ImageAdapter;
import com.android.danmartin.danmartininterview.main.model.FlickrObject;
import com.android.danmartin.danmartininterview.main.providers.FlickrProvider;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FlickrProvider.FlickrProviderListener {
    ImageAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layout = R.layout.fragment_recyclerview;
        View view = inflater.inflate(layout, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ImageAdapter();
        recyclerView.setAdapter(mAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean eaten = true;
        switch (item.getItemId()) {
            case R.id.action_refresh:
                FlickrProvider.getSharedInstance().requestData(getActivity());
                break;
            default:
                eaten = super.onOptionsItemSelected(item);
                break;
        }
        return eaten;
    }

    @Override
    public void onPause() {
        FlickrProvider.getSharedInstance().unRegisterListener(this);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        FlickrProvider provider = FlickrProvider.getSharedInstance();
        provider.registerListener(this);
        provider.requestData(getActivity());
    }

    @Override
    public void onComplete(ArrayList<FlickrObject> data) {
//        new data has been loaded. update the adapter
        mAdapter.setData(data);
    }

    @Override
    public void onError() {
//        There was an error loading the data. In a client app I would most likely be more detailed as simple toast message is used for this example
        Toast.makeText(getActivity(), R.string.load_error, Toast.LENGTH_LONG).show();
    }
}