package com.test.favherotest.view.viewDresser;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.favherotest.R;
import com.test.favherotest.model.MarvelImage;
import com.test.favherotest.model.MarvelImageAndText;
import com.test.favherotest.view.adapter.MarvelResultAdapter;

/**
 * Created by comac on 11/03/2017.
 */

public class ComicItemViewDresser implements MarvelResultAdapter.ViewDresser<MarvelImageAndText> {

    private Context mContext;

    public ComicItemViewDresser(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void resetView(View view) {
        ((TextView)view.findViewById(R.id.comic_item_title)).setText(null);
        ((ImageView)view.findViewById(R.id.comic_item_cover)).setImageDrawable(null);
        view.setTag(null);
    }

    @Override
    public void dressView(View view, MarvelImageAndText item) {
        ((TextView)view.findViewById(R.id.comic_item_title)).setText(item.getText());
        Picasso.with(mContext)
                .load(item.getImageUrl(MarvelImage.Variants.PORTRAIT_INCREDIBLE))
                .into((ImageView)view.findViewById(R.id.comic_item_cover));
        view.setTag(item);
    }
}
