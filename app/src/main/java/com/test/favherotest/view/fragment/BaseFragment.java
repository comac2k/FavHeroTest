package com.test.favherotest.view.fragment;

import android.support.v4.app.Fragment;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by comac on 11/03/2017.
 */

abstract class BaseFragment extends Fragment {
    protected CompositeSubscription mSubscriptions = new CompositeSubscription();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mSubscriptions.clear();
    }

}
