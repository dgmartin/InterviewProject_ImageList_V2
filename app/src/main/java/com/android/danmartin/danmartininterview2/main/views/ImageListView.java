package com.android.danmartin.danmartininterview2.main.views;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.danmartin.danmartininterview2.R;
import com.android.danmartin.danmartininterview2.main.model.FlickrObject;
import com.android.danmartin.danmartininterview2.main.network.VolleyUtils;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

/**
 * Created by Dan on 4/11/2015.
 */
public class ImageListView extends RelativeLayout {
    public static final int LAYOUT_LIST = 0;
    public static final int LAYOUT_GRID = 1;

    private FlickrObject mData = null;

    public ImageListView(Context context) {
        super(context);
    }

    public ImageListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setLayout(Context context, int layout) {
        int layoutRes = layout == LAYOUT_LIST ? R.layout.view_image_list : R.layout.view_grid_image_list;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(layoutRes, this, true);
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
