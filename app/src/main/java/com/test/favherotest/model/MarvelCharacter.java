package com.test.favherotest.model;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelCharacter implements MarvelImageAndText {
    private Long id;
    private String name;
    private MarvelImage thumbnail;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public MarvelImage getThumbnail() {
        return thumbnail;
    }

    @Override
    public String getText() {
        return getName();
    }

    @Override
    public String getImageUrl(MarvelImage.Variants variant) {
        return getThumbnail().getUrl(variant);
    }
}
