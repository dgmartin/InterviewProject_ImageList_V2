package com.android.danmartin.danmartininterview2.main.text;

import android.text.Editable;
import android.text.TextWatcher;

/**
 * @author Dan Martin (Fuzz)
 */
public class TagTextWatcher implements TextWatcher {
    private TagsChangeListener mListener;

    private TagTextWatcher() {
    }

    public TagTextWatcher(TagsChangeListener listener) {
        super();
        mListener = listener;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String tags = s.toString();
        mListener.onTagsChanged(tags);
    }


    public interface TagsChangeListener {
        void onTagsChanged(String tags);
    }
}
