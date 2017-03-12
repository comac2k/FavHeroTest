package com.test.favherotest.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.favherotest.R;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.model.MarvelImage;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComicDetailFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ComicDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComicDetailFragment extends Fragment {
    private static final String ARG_COMIC_ID = "comicId";

    private MarvelComic mComic;
    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.description) WebView mDescription;
    @BindView(R.id.background_image) ImageView mImage;

    private ComicDetailFragmentListener mListener;

    public ComicDetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param comicId Parameter 1.
     * @return A new instance of fragment ComicDetailFragment.
     */
    public static ComicDetailFragment newInstance(MarvelComic comicId) {
        ComicDetailFragment fragment = new ComicDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_COMIC_ID, comicId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mComic = (MarvelComic) getArguments().getSerializable(ARG_COMIC_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_comic_detail, container, false);
        ButterKnife.bind(this, view);
        mTitle.setText(mComic.getTitle());
        mDescription.loadData("<style>* { color: white; font-size: 13pt; text-shadow: 2px 2px 2px black; }\nbody { background-color:transparent; margin: 0; padding: 0; }</style>" + mComic.getDescription(), "text/html", "UTF-8");
        mDescription.setTag(mComic.getDescription());
        mDescription.setBackgroundColor(Color.TRANSPARENT);
        Picasso.with(getContext())
                .load(mComic.getRandomImage().getUrl(MarvelImage.Variants.PORTRAIT_INCREDIBLE)) // TODO: switch land / port
                .fit()
                .into(mImage);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComicDetailFragmentListener) {
            mListener = (ComicDetailFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ComicDetailFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface ComicDetailFragmentListener {
    }
}
