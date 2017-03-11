package com.test.favherotest.model;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelComic {
    private Long id;
    private String title;
    private MarvelThumbnail thumbnail;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MarvelThumbnail getThumbnail() {
        return thumbnail;
    }
}
