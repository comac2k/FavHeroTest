package com.test.favherotest.view.viewDresser;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.favherotest.R;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.model.MarvelThumbnail;
import com.test.favherotest.view.adapter.MarvelResultAdapter;

/**
 * Created by comac on 11/03/2017.
 */

public class ComicItemViewDresser implements MarvelResultAdapter.ViewDresser<MarvelComic> {

    private Context mContext;

    public ComicItemViewDresser(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void resetView(View view) {
        ((TextView)view.findViewById(R.id.comic_item_title)).setText(null);
        ((ImageView)view.findViewById(R.id.comic_item_cover)).setImageDrawable(null);
    }

    @Override
    public void dressView(View view, MarvelComic data) {
        ((TextView)view.findViewById(R.id.comic_item_title)).setText(data.getTitle());
        Picasso.with(mContext)
                .load(data.getThumbnail().getUrl(MarvelThumbnail.Variants.PORTRAIT_INCREDIBLE))
                .into((ImageView)view.findViewById(R.id.comic_item_cover));
    }
}
