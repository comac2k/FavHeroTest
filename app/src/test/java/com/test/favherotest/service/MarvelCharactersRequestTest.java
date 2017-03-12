package com.test.favherotest.service;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.model.MarvelImage;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by comac on 12/03/2017.
 */
public class MarvelCharactersRequestTest {

    private static final String TEST_TITLE = "Test Comic";
    private static final String TEST_DESCRIPTION = "Test Description";

    @Mock
    MarvelApiService marvelApiServiceMock;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        ProviderModule mockedProviderModule = spy(new ProviderModule());
        doReturn(Observable.just(getMockedComicsReponse())).when(marvelApiServiceMock).getComics(anyLong(), anyInt(), anyInt());
        when(mockedProviderModule.provideMarvelApiService()).thenReturn(marvelApiServiceMock);
        ProviderModule.setInstance(mockedProviderModule);

    }

    private MarvelApiResponse<MarvelComic> getMockedComicsReponse() {
        List<MarvelComic> values = Arrays.asList(new MarvelComic[]{
                new MarvelComic(1l, TEST_TITLE, TEST_DESCRIPTION, new MarvelImage(), getMockedImageList()),
                new MarvelComic(2l, TEST_TITLE, TEST_DESCRIPTION, new MarvelImage(), getMockedImageList())
        });
        return new MarvelApiResponse<MarvelComic>().withValues(values, 22);
    }

    private List<MarvelImage> getMockedImageList() {
        return Arrays.asList(new MarvelImage[]{ new MarvelImage() });
    }

    @Test
    public void requestResultPage() throws Exception {
        MarvelComicsRequest request = new MarvelComicsRequest(1);

        // fetchMetadata is mandatory. Makes a first request to get the total count and caches the first page.
        request.fetchMetadata();
        verify(marvelApiServiceMock, times(1)).getComics(anyLong(), anyInt(), anyInt());

        // The first query to the first page prefetches the second page -> so, new request
        request.getItem(0);
        verify(marvelApiServiceMock, times(2)).getComics(anyLong(), anyInt(), anyInt());

        // Subsequent queries to the same page don't cause any more requests
        request.getItem(1);
        verify(marvelApiServiceMock, times(2)).getComics(anyLong(), anyInt(), anyInt());

        // Second page is already cached, so no new requests are necessary to access it's elements
        request.getItem(20);
        request.getItem(21);
        verify(marvelApiServiceMock, times(2)).getComics(anyLong(), anyInt(), anyInt());
    }

}