package com.test.favherotest.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.test.favherotest.R;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.view.fragment.ComicDetailFragment;
import com.test.favherotest.view.fragment.ComicListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ComicListFragment.ComicListFragmentListener, ComicDetailFragment.ComicDetailFragmentListener {

    final static long FAV_HERO_ID = 1009220;
    final static String DETAIL_FRAGMENT_STACK_NAME = "detail";

    @Nullable @BindView(R.id.comic_detail_fragment_container) FrameLayout mComicDetailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState == null) {
            ComicListFragment listFragment = ComicListFragment.newInstance(FAV_HERO_ID);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.comic_list_fragment_container, listFragment)
                    .commit();
        }
    }

    @Override
    public void onComicSelected(MarvelComic comic) {
        ComicDetailFragment detailFragment = ComicDetailFragment.newInstance(comic);
        final FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (mComicDetailContainer == null) { transaction.addToBackStack(DETAIL_FRAGMENT_STACK_NAME); }
        transaction.replace(mComicDetailContainer == null ? R.id.comic_list_fragment_container : R.id.comic_detail_fragment_container, detailFragment);
        transaction.commit();
    }
}
