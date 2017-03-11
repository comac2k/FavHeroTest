package com.test.favherotest.service;

import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;

import rx.Observable;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelComicsRequest extends MarvelApiRequest<MarvelComic> {

    protected long mCharacterId;

    public MarvelComicsRequest(long characterId) {
        super();
        this.mCharacterId = characterId;
    }

    @Override
    protected Observable<MarvelApiResponse<MarvelComic>> requestResultPage(int page) {
        if ((page < 0) || (page > getTotal() / PAGE_SIZE)) { return Observable.empty(); }
        return mMarvelService.getComics(mCharacterId, page * PAGE_SIZE, PAGE_SIZE);
    }
}
