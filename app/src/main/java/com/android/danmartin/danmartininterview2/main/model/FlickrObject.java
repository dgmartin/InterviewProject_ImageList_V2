package com.android.danmartin.danmartininterview2.main.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Dan on 4/11/2015.
 */
public class FlickrObject implements Serializable {

    private static final long serialVersionUID = -7038443193512264096L;
    private String mTitle, mMedia, mDescription, mAuthor;

//    The following data is available via the api however it is unused. I comment them out incase I ever want to add this later on
//    private String mLink, mAuthorID,mTags;
//    private Date mDateTaken,mPublished;

    private FlickrObject() {
    }

    public FlickrObject(JSONObject json) {
        parseJson(json);
    }

    private void parseJson(JSONObject json) {
//        step through each value and parse the required field
        mTitle = getString(json, "title");
        mDescription = getString(json, "description");
        mAuthor = getString(json, "author");

//        Because the media link is stored within the media object we handle it slightly differently
        mMedia = getMedia(json);
    }

    private String getString(JSONObject json, String name) {
//        we put this in a seperate class to avoid using the try catch more than once
        String value = null;
        try {
            value = json.getString(name);
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        if the value is null change it to a blank string to avoid conflicts going forward
        if(value==null)value="";
        return value;
    }

    private String getMedia(JSONObject json) {
        String media = null;
        try {
            JSONObject mediaObj = json.getJSONObject("media");
            media = getString(mediaObj, "m");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return media;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getMedia() {
        return mMedia;
    }

//    Currently this data is unused but I left it here incase I try using it on the imagefragment page.  normally unused code would be cleaned up.
    public String getDescription() {
        return mDescription;
    }

    public String getAuthor() {
        return mAuthor;
    }
}
