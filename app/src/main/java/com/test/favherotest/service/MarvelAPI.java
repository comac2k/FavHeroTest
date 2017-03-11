package com.test.favherotest.service;

import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;


/**
 * Created by comac on 19/02/2017.
 */

public interface MarvelAPI {
    @GET("/v1/public/characters/{mCharacterId}/comics")
    Observable<MarvelApiResponse<MarvelComic>> getComics(
            @Path("mCharacterId") long characterId,
            @Query("apikey") String publickey,
            @Query("ts") String timestamp,
            @Query("hash") String hash,
            @Query("offset") int offset,
            @Query("limit") int limit);
}
