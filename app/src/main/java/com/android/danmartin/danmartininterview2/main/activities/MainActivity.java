package com.android.danmartin.danmartininterview2.main.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

import com.android.danmartin.danmartininterview2.R;
import com.android.danmartin.danmartininterview2.main.network.VolleyUtils;
import com.android.volley.RequestQueue;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onStop() {
        super.onStop();
        //When the activity ends we cancel all network requests
        RequestQueue queue = VolleyUtils.getSharedInstance(getApplicationContext()).getRequestQueue();
        queue.cancelAll(VolleyUtils.DATA_REQUEST);
        queue.cancelAll(VolleyUtils.IMAGE_REQUEST);
    }
}
