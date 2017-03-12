package com.test.favherotest.service;

import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelCharacter;

import rx.Observable;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelCharactersRequest extends MarvelApiRequest<MarvelCharacter> {

    protected long mComicId;

    public MarvelCharactersRequest(long comicId) {
        super();
        this.mComicId = comicId;
    }

    @Override
    protected Observable<MarvelApiResponse<MarvelCharacter>> requestResultPage(int page) {
        if ((page < 0) || (page > getTotal() / PAGE_SIZE)) { return Observable.empty(); }
        return mMarvelService.getCharacters(mComicId, page * PAGE_SIZE, PAGE_SIZE);
    }
}
