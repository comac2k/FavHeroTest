package com.test.favherotest.presenter;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.service.MarvelApiService;

import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by comac on 11/03/2017.
 */

public class ComicListPresenter {

    private final View mView;
    private BehaviorSubject<Observable<MarvelApiResponse<MarvelComic>>> mComicListRequests = BehaviorSubject.create();
    private Observable<List<MarvelComic>> mOnComicsChanged = BehaviorSubject.switchOnNext(mComicListRequests).map(this::comicsResponseToList);
    protected MarvelApiService marvelService = ProviderModule.getInstance().provideMarvelApiService();

    public ComicListPresenter(View view) {
        mView = view;
    }

    public void setHeroId(long id) {
        mComicListRequests.onNext(marvelService.getComics(id));
    }

    private List<MarvelComic> comicsResponseToList(MarvelApiResponse<MarvelComic> comicsResponse) {
        return comicsResponse.getData().getResults();
    }

    public Observable<List<MarvelComic>> getOnComicsChanged() {
        return mOnComicsChanged;
    }

    public interface View {
    }
}
