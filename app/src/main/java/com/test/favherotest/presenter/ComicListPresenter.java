package com.test.favherotest.presenter;

import com.test.favherotest.service.MarvelComicsRequest;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by comac on 11/03/2017.
 */

public class ComicListPresenter {

    private final View mView;
    private BehaviorSubject<MarvelComicsRequest> mOnComicsChanged = BehaviorSubject.create();

    public ComicListPresenter(View view, long heroId) {
        mView = view;
        mOnComicsChanged.onNext(new MarvelComicsRequest(heroId));
    }

    public Observable<MarvelComicsRequest> getOnComicsChanged() {
        return mOnComicsChanged;
    }

    public interface View {
    }
}
