package com.test.favherotest.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelComic implements Serializable {
    private Long id;
    private String title;
    private String description;
    private MarvelImage thumbnail;
    private List<MarvelImage> images;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public MarvelImage getThumbnail() {
        return thumbnail;
    }

    public String getDescription() {
        return description;
    }

    public MarvelImage getRandomImage() {
        return images.get((int) (images.size() * Math.random()));
    }
}
