package com.android.danmartin.danmartininterview2.main.fragment;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.danmartin.danmartininterview2.R;
import com.android.danmartin.danmartininterview2.main.adapters.ImageAdapter;
import com.android.danmartin.danmartininterview2.main.model.FlickrObject;
import com.android.danmartin.danmartininterview2.main.providers.FlickrProvider;
import com.android.danmartin.danmartininterview2.main.text.TagTextWatcher;
import com.android.danmartin.danmartininterview2.main.views.ImageListView;

import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements FlickrProvider.FlickrProviderListener, TagTextWatcher
        .TagsChangeListener {
    public static final String SAVED_TAGS = "SAVED_TAGS";

    private ImageAdapter mAdapter;
    private EditText mEditText;
    private ImageButton mClear;

    private String mTags;

    private View.OnClickListener mClearListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//          When the clear button is clicked clear the text from the edit text and reset the tags variable.
            mEditText.setText("");
            updateTags("");
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mTags = prefs.getString(SAVED_TAGS, "");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle
            savedInstanceState) {
        int layout = R.layout.fragment_main;
        View view = inflater.inflate(layout, container, false);

        mEditText = (EditText) view.findViewById(R.id.edittext);
        mEditText.setText(mTags);
        mEditText.addTextChangedListener(new TagTextWatcher(this));

        mClear = (ImageButton) view.findViewById(R.id.clear);
        mClear.setOnClickListener(mClearListener);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);

//        To swap back to a linear list layout comment out the line above and uncomment the two lines below.
//        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,
//                false);
        recyclerView.setLayoutManager(mLayoutManager);

//      The following line isn't necessary but I kept it in the event I ever needed to swap back to a Linear List
//      version
        int layoutStyle = (mLayoutManager instanceof GridLayoutManager) ? ImageListView.LAYOUT_GRID
                : ImageListView.LAYOUT_LIST;
        mAdapter = new ImageAdapter(layoutStyle);
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
                FlickrProvider.getSharedInstance().requestData(getActivity(), mTags);
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

        if (mAdapter.getItemCount() == 0) {
            provider.requestData(getActivity(), mTags);
        }
    }

    @Override
    public void onComplete(ArrayList<FlickrObject> data) {
//        new data has been loaded. update the adapter
        mAdapter.setData(data);
    }

    @Override
    public void onError() {
//      There was an error loading the data. In a client app I would most likely be more detailed as simple toast
//      message is used for this example
        Toast.makeText(getActivity(), R.string.load_error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onTagsChanged(String tags) {
        updateTags(tags);
    }

    private void updateTags(String tags) {
        if (validateTags(tags) && !mTags.equalsIgnoreCase(tags)) {
            mTags = tags;

            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(SAVED_TAGS,mTags);
            editor.apply();

            FlickrProvider.getSharedInstance().requestData(getActivity(), mTags);
        }
    }

    private boolean validateTags(String tags) {
        return true; //TODO add validation to allow for comma seperated list
    }
}