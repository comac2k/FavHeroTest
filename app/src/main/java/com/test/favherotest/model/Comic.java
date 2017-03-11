package com.test.favherotest.model;

/**
 * Created by comac on 11/03/2017.
 */

public class Comic {
    protected final String mTitle;
    protected final String mCoverUrl;
    protected final long mId;

    public Comic(String mTitle, String mCoverUrl, long mId) {
        this.mTitle = mTitle;
        this.mCoverUrl = mCoverUrl;
        this.mId = mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getCoverUrl() {
        return mCoverUrl;
    }
}
