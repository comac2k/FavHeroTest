package com.test.favherotest.view;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;

import com.test.favherotest.R;
import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.service.MarvelApiRequest;

import java.util.HashMap;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by comac on 19/02/2017.
 */

public class MarvelResultAdapter<RequestType> implements ListAdapter {

    private final Context mContext;
    private final ViewDresser mDresser;
    MarvelApiRequest<RequestType> mRequest;
    CompositeSubscription mSubscriptions = new CompositeSubscription();

    public MarvelResultAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull MarvelApiRequest<RequestType> request, ViewDresser dresser) {
        this.mRequest = request;
        this.mContext = context;
        this.mDresser = dresser;
    }

    public Observable<MarvelApiResponse<RequestType>> fetchMetadata() {
        return this.mRequest.fetchMetadata();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.comic_item, parent, false);
        }
        else {
            cleanSubscriptionsForView(convertView);
            mDresser.resetView(convertView);
        }
        View finalConvertView = convertView;
        trackViewSubscription(convertView, mRequest.getItem(position)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(comicData -> itemDataReceived(finalConvertView, comicData)));
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private HashMap<View, Subscription> viewSubscriptions = new HashMap<>();

    private void trackViewSubscription(View view, Subscription subscription) {
        viewSubscriptions.put(view, subscription);
        mSubscriptions.add(subscription);
    }

    private void cleanSubscriptionsForView(View view) {
        if (!viewSubscriptions.containsKey(view)) { return; }
        Subscription subscription = viewSubscriptions.get(view);
        mSubscriptions.remove(subscription);
        viewSubscriptions.remove(view);
    }

    private void itemDataReceived(View view, RequestType comic) {
        cleanSubscriptionsForView(view);
        mDresser.dressView(view, comic);
    }

    public Observable<RequestType> getItemForPosition(int position) {
        return mRequest.getItem(position);
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        mSubscriptions.add(fetchMetadata().subscribe(ignored -> observer.onInvalidated() ));
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mRequest.getTotal();
    }

    @Override
    public Object getItem(int position) {
        return getItemForPosition(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    public void dispose() {
        mRequest.dispose();
        mSubscriptions.clear();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    interface ViewDresser<RequestType> {
        void resetView(View view);
        void dressView(View view, RequestType data);
    }
}
