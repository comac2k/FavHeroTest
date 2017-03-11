package com.test.favherotest.service;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;

import java.security.NoSuchAlgorithmException;

import rx.Observable;

/**
 * Created by comac on 19/02/2017.
 */

public class MarvelApiService {

    private static MarvelApiService singleton;
    private MarvelAPI marvelAPI = ProviderModule.getInstance().provideMarvelApi();

    protected static final String PUBLICKEY = "6a7ed890b4b941a925202a5630d5b162";
    protected static final String PRIVATEKEY = "0f1d0fdf46a0bf32f962b0b9997233c0395cdf8e";

    public static MarvelApiService getInstance() {
        if (singleton == null) { singleton = new MarvelApiService(); }
        return singleton;
    }

    protected String getHash(String ts) {
        return encodeMD5(ts + PRIVATEKEY + PUBLICKEY);
    }

    private String encodeMD5(String text)  {
        String result = "";

        try {
            byte[] mdbytes = java.security.MessageDigest.getInstance("MD5").digest((text).getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mdbytes.length; i++) {
                sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            result = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    protected String getTimestamp() {
        return "1";
    }

    public Observable<MarvelApiResponse<MarvelComic>> getComics(long characterId, int offset, int pageSize) {
        String ts = getTimestamp();
        String hash = getHash(ts);

        return marvelAPI.getComics(characterId, PUBLICKEY, ts, hash, offset, pageSize);
    }

}
