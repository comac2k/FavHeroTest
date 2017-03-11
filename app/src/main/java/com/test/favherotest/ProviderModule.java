package com.test.favherotest;

/**
 * Created by comac on 11/03/2017.
 */

public class ProviderModule {

    private static ProviderModule singleton;

    public ProviderModule() {}

    public static ProviderModule getInstance() {
        if (singleton == null) {
            singleton = new ProviderModule();
        }
        return singleton;
    }

    public ComicListPresenter getComicListPresenter(ComicListPresenter.View view) {
        return new ComicListPresenter(view);
    }
}
