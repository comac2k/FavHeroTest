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

    public ComicListPresenter(View view) {
        mView = view;
    }

    public void setHeroId(long id) {
        mOnComicsChanged.onNext(new MarvelComicsRequest(id));
    }

    public Observable<MarvelComicsRequest> getOnComicsChanged() {
        return mOnComicsChanged;
    }

    public interface View {
    }
}
