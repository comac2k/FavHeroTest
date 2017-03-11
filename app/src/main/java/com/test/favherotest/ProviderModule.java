package com.test.favherotest;

import com.test.favherotest.presenter.ComicListPresenter;
import com.test.favherotest.service.MarvelAPI;
import com.test.favherotest.service.MarvelApiService;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.schedulers.Schedulers;

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

    public ComicListPresenter getComicListPresenter(ComicListPresenter.View view, long heroId) {
        return new ComicListPresenter(view, heroId);
    }

    public MarvelAPI provideMarvelApi() {
        RxJavaCallAdapterFactory rxAdapter = RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gateway.marvel.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(rxAdapter)
                .build();

        return retrofit.create(MarvelAPI.class);
    }

    public MarvelApiService provideMarvelApiService() {
        return MarvelApiService.getInstance();
    }

}
