package com.test.favherotest.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.test.favherotest.R;
import com.test.favherotest.view.fragment.ComicListFragment;

public class MainActivity extends AppCompatActivity implements ComicListFragment.ComicListFragmentListener {

    final static long FAV_HERO_ID = 1009220;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            ComicListFragment listFragment = ComicListFragment.newInstance(FAV_HERO_ID);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.comic_list_fragment_container, listFragment)
                    .commit();
        }
    }
}