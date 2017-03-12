package com.test.favherotest.service;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.model.MarvelApiResponse;

import java.util.HashMap;
import java.util.Map;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subjects.BehaviorSubject;
import rx.subscriptions.CompositeSubscription;

import static rx.Observable.just;

/**
 * Created by comac on 19/02/2017.
 */

public abstract class MarvelApiRequest<RequestType> {
    protected static final int PAGE_SIZE = 20;
    protected static final int MAX_PAGES_IN_MEMORY = 5;

    protected MarvelApiService mMarvelService = ProviderModule.getInstance().provideMarvelApiService();
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();
    private MarvelApiResponse<RequestType> mMetadata;
    private ResultsPageCache mResultsPageCache = new ResultsPageCache();

    public Observable<MarvelApiResponse<RequestType>> fetchMetadata() {
        if (mMetadata != null) { return just(mMetadata); }
        return requestResultPage(0)
                .onErrorResumeNext(just(new MarvelApiResponse<RequestType>().empty()))
                .observeOn(AndroidSchedulers.mainThread())
                .map(this::metadataReceived);
    }

    private MarvelApiResponse<RequestType> metadataReceived(MarvelApiResponse<RequestType> response) {
        mMetadata = response;
        mResultsPageCache.pageReceived(response);
        return response;
    }

    public int getTotal() {
        return mMetadata != null ? mMetadata.getData().getTotal() : 0;
    }

    public void dispose() {
        mSubscriptions.clear();
    }

    public Observable<RequestType> getItem(int position) {
        final int pageNr = position / PAGE_SIZE;
        return mResultsPageCache.getPage(pageNr).map(page -> extractItemFromPage(page, position));
    }

    private RequestType extractItemFromPage(MarvelApiResponse<RequestType> page, int position) {
        return page.getData().getResults().get(position - page.getData().getOffset());
    }

    protected abstract Observable<MarvelApiResponse<RequestType>> requestResultPage(int pageNr);

    public boolean isEmpty() {
        return (mMetadata != null) && (mMetadata.getData().getTotal() == 0);
    }


    class ResultsPageCache {

        HashMap<Integer, Observable<MarvelApiResponse<RequestType>>> pageRequests = new HashMap<>();
        HashMap<Integer, MarvelApiResponse<RequestType>> pagesLoaded = new HashMap<>();
        private int mLastPageRequested;

        private Observable<MarvelApiResponse<RequestType>> getPage(int pageNr) {
            mLastPageRequested = pageNr;
            Observable<MarvelApiResponse<RequestType>> result = getPageInt(mLastPageRequested);
            getPageInt(mLastPageRequested-1);
            getPageInt(mLastPageRequested+1);
            return result;
        }

        private Observable<MarvelApiResponse<RequestType>> getPageInt(int pageNr) {
            synchronized (pageRequests) {
                if (pagesLoaded.containsKey(pageNr)) {
                    // We have the page in memory -> return it right away!
                    return just(pagesLoaded.get(pageNr));
                }

                // Add a new request only if we're not already waiting for a response
                if (!pageRequests.containsKey(pageNr)) {
                    BehaviorSubject<MarvelApiResponse<RequestType>> pageRequest = BehaviorSubject.create();
                    pageRequests.put(pageNr, pageRequest);

                    // Do the request once and emmit the response to pageRequests observable so that all the
                    // getItem() calls for the same page reuse the same response.
                    // TODO: debounce to avoid requesting pages while scrolling too fast
                    requestResultPage(pageNr)
                            .onErrorResumeNext(just(new MarvelApiResponse<RequestType>().empty()))
                            .subscribe(data -> {        // TODO: unsubscribe
                                pageReceived(data);
                                pageRequest.onNext(data);
                            });
                }

                // Either case, return a reference to the request (pre-existing or new)
                return pageRequests.get(pageNr);
            }
        }

        private void pageReceived(MarvelApiResponse<RequestType> page) {
            synchronized (pageRequests) {
                int pageNr = page.getData().getOffset() / PAGE_SIZE;
                pageRequests.remove(pageNr);

                // Add the page to the cache and purge excess entries
                pagesLoaded.put(pageNr, page);
                while (pagesLoaded.size() > MAX_PAGES_IN_MEMORY) {
                    int pageToRemove = getFurthest(pagesLoaded, mLastPageRequested);
                    pagesLoaded.remove(pageToRemove);
                }

            }
        }

        private int getFurthest(Map<Integer, MarvelApiResponse<RequestType>> items, int pageNr) {
            int maxDistance = -1;
            int furthestItem = 0;
            for (Integer item : items.keySet()) {
                int distance = Math.abs(item - pageNr);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    furthestItem = item;
                }
            }
            return furthestItem;
        }
    }
}
