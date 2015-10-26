package com.android.danmartin.danmartininterview.main.providers;

import android.content.Context;
import android.os.AsyncTask;

import com.android.danmartin.danmartininterview.main.model.FlickrObject;
import com.android.danmartin.danmartininterview.main.network.FlickrListRequest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * Created by Dan on 4/11/2015.
 */
public class FlickrProvider implements FlickrListRequest.FlickrListRequestListener {
    private static FlickrProvider mSharedInstance;
    private Context mContext;
    private static final String DATA_CACHE = "Data_Cache.dmi";
    private ArrayList<FlickrObject> mData = new ArrayList<>();
//    Normally a single listener will do but I find with photo galleries there is always a list view
//    and a paging image view so I left a place for multiple listeners if needed.
    private ArrayList<FlickrProviderListener> mListeners = new ArrayList<>();

    private FlickrProvider() {
    }

    public static FlickrProvider getSharedInstance() {
        if (mSharedInstance == null) {
            mSharedInstance = new FlickrProvider();
        }
        return mSharedInstance;
    }

    public void requestData(Context context) {
        mContext = context.getApplicationContext();

//      The first time data is requested during a session we check to see if there is cached data
//      before we make a new request
        if (mData == null || mData.isEmpty()) {
            new InflateDataTask().execute();
        } else {
            new FlickrListRequest().requestNewData(context, this);
        }
    }

    @Override
    public void onComplete(ArrayList<FlickrObject> data) {
        if (data != null && !data.isEmpty()) {
//          The network request has completed and returned data. Save the data to cache and notify
//          listeners
            mData = data;
            new SaveDataTask().execute();
            for (FlickrProviderListener listener : mListeners) {
                listener.onComplete((ArrayList<FlickrObject>) mData.clone());
            }
        }
    }

    @Override
    public void onError() {
        for (FlickrProviderListener listener : mListeners) {
            listener.onError();
        }
    }

    public void registerListener(FlickrProviderListener listener) {
        mListeners.add(listener);
    }

    public void unRegisterListener(FlickrProviderListener listener) {
        mListeners.remove(listener);
    }

    public interface FlickrProviderListener {
        public void onComplete(ArrayList<FlickrObject> data);

        public void onError();
    }

    private class InflateDataTask extends AsyncTask<Void, Void, ArrayList<FlickrObject>> {

        @Override
        protected ArrayList<FlickrObject> doInBackground(Void... params) {
            ArrayList<FlickrObject> data = null;
            try {
                File cacheDir = mContext.getCacheDir();
                File file = new File(cacheDir, DATA_CACHE);
                if (file.exists()) {
                    FileInputStream fileInputStream = new FileInputStream(file);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    data = (ArrayList<FlickrObject>) objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(ArrayList<FlickrObject> result) {
//          The new data has been restored from cache.  Notify the listeners and make make a call
//          to update the data
            if (result != null) {
                synchronized (mData) {
                    mData = result;
                    for (FlickrProviderListener listener : mListeners) {
                        listener.onComplete((ArrayList<FlickrObject>) mData.clone());
                    }
                }
            }
            new FlickrListRequest().requestNewData(mContext, FlickrProvider.this);
        }
    }

    private class SaveDataTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            if (mContext != null) {
                synchronized (mData) {
                    try {
                        File cacheDir = mContext.getCacheDir();
                        File file = new File(cacheDir, DATA_CACHE);
                        FileOutputStream fileOutputStream = new FileOutputStream(file);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(mData);
                        objectOutputStream.close();
                        fileOutputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }
}
