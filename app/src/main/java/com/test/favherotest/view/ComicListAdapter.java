package com.test.favherotest.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.favherotest.R;
import com.test.favherotest.model.Comic;

import java.util.List;

/**
 * Created by comac on 11/03/2017.
 */

class ComicListAdapter extends ArrayAdapter<Comic> {

    private final List<Comic> mComics;
    private Context mContext;

    public ComicListAdapter(Context context, List<Comic> comics) {
        super(context, R.layout.comic_item, comics);
        mComics = comics;
        mContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.comic_item, parent, false);
        }

        Comic comic = getItem(position);

        ((TextView)convertView.findViewById(R.id.comic_item_title)).setText(comic.getTitle());
        Picasso.with(mContext).load(comic.getCoverUrl()).into((ImageView)convertView.findViewById(R.id.comic_item_cover));

        return convertView;
    }
}
