package com.android.danmartin.danmartininterview.main.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.danmartin.danmartininterview.R;
import com.android.danmartin.danmartininterview.main.model.FlickrObject;
import com.android.danmartin.danmartininterview.main.network.FlickrListRequest;
import com.android.danmartin.danmartininterview.main.network.VolleyUtils;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Dan on 4/11/2015.
 */
public class ImageListView extends RelativeLayout {
    private FlickrObject mData = null;

    public ImageListView(Context context) {
        super(context);
        setUpUI(context);
    }

    public ImageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpUI(context);
    }

    public ImageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpUI(context);
    }

    public ImageListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setUpUI(context);
    }

    protected void setUpUI(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.view_image_list, this, true);
        setBackgroundResource(R.color.image_listview_bg);
    }

    public void setData(FlickrObject data) {
        if (data != null) {
            mData = data;
            ((TextView) findViewById(R.id.title)).setText(data.getTitle());
            ((TextView) findViewById(R.id.author)).setText(data.getAuthor());

            String url = data.getMedia();
            if (!TextUtils.isEmpty(url)) {
                NetworkImageView image = (NetworkImageView) findViewById(R.id.image);
                ImageLoader loader = VolleyUtils.getSharedInstance(getContext()).getImageLoader();
                image.setImageUrl(url, loader);
            }
        }
    }

    public FlickrObject getData() {
        return mData;
    }
}
