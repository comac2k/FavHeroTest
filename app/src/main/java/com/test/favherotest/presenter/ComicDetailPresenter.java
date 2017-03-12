package com.test.favherotest.presenter;

import com.test.favherotest.service.MarvelCharactersRequest;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by comac on 12/03/2017.
 */

public class ComicDetailPresenter {

    private final View mView;
    private final long mComicId;
    private BehaviorSubject<MarvelCharactersRequest> mOnComicsChanged = BehaviorSubject.create();

    public ComicDetailPresenter(View view, long comicId) {
        mView = view;
        mComicId = comicId;
        mOnComicsChanged.onNext(new MarvelCharactersRequest(comicId));
    }

    public Observable<MarvelCharactersRequest> getOnCharactersChanged() {
        return mOnComicsChanged;
    }

    public interface View {

    }
}
