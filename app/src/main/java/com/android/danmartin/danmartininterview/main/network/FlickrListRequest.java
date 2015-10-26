package com.android.danmartin.danmartininterview.main.network;

import android.content.Context;

import com.android.danmartin.danmartininterview.R;
import com.android.danmartin.danmartininterview.main.model.FlickrObject;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Dan on 4/11/2015.
 */

//The flickr request for json is returning the data in an invalid json format. Due to this there is
// much commented out code in this file.  normally this would be cleaned up but I wanted to leave it in the event they fix the response.

public class FlickrListRequest implements Response.Listener<String>, Response.ErrorListener {
    private FlickrListRequestListener mListener;

    public void requestNewData(Context context, FlickrListRequestListener listener) {
        mListener = listener;
        String url = context.getString(R.string.flickr_url);
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        StringRequest request = new StringRequest(Request.Method.GET, url, this, this);
        request.addMarker(VolleyUtils.DATA_REQUEST);
        VolleyUtils.getSharedInstance(context).addToRequestQueue(request);
    }

//    @Override
//    public void onResponse(JSONObject jsonObject) {
//        ArrayList<FlickrObject> parsedData = new ArrayList<>();
//        try {
//            JSONArray items = jsonObject.getJSONArray("items");
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject rawItem = items.getJSONObject(i);
//                FlickrObject item = new FlickrObject(rawItem);
//                parsedData.add(item);
//            }
//        } catch (JSONException e) {
//            mListener.onError();
//        }
//        if (!parsedData.isEmpty()) {
//            mListener.onComplete(parsedData);
//        } else {
//            mListener.onError();
//        }
//    }
    @Override
    public void onResponse(String rawResponse) {
        ArrayList<FlickrObject> parsedData = new ArrayList<>();
        try {
            String cleanedResponse = rawResponse.replaceFirst("jsonFlickrFeed\\(", "");
            cleanedResponse = cleanedResponse.substring(0, cleanedResponse.length() - 1);
            JSONObject jsonObject = new JSONObject(cleanedResponse);
            JSONArray items = jsonObject.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject rawItem = items.getJSONObject(i);
                FlickrObject item = new FlickrObject(rawItem);
                parsedData.add(item);
            }
        } catch (JSONException e) {
            mListener.onError();
        }
        if (!parsedData.isEmpty()) {
            mListener.onComplete(parsedData);
        } else {
            mListener.onError();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        mListener.onError();
    }

    public interface FlickrListRequestListener {
        public void onComplete(ArrayList<FlickrObject> data);

        public void onError();
    }
}
