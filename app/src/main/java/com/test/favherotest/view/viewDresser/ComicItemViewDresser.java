package com.test.favherotest.view.viewDresser;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
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

        ImageView spinner = (ImageView) view.findViewById(R.id.spinner_image);
        spinner.setBackgroundResource(R.drawable.spinner);
        spinner.setVisibility(View.VISIBLE);
        ((AnimationDrawable) spinner.getBackground()).start();
    }

    @Override
    public void dressView(View view, MarvelImageAndText item) {
        ImageView spinner = (ImageView) view.findViewById(R.id.spinner_image);
        ((AnimationDrawable) spinner.getBackground()).stop();
        spinner.setVisibility(View.GONE);

        ((TextView)view.findViewById(R.id.comic_item_title)).setText(item.getText());
        Picasso.with(mContext)
                .load(item.getImageUrl(MarvelImage.Variants.PORTRAIT_INCREDIBLE))
                .placeholder(R.drawable.spinner)
                .into((ImageView)view.findViewById(R.id.comic_item_cover));
        view.setTag(item);
    }
}
