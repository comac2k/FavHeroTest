package com.test.favherotest.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.test.favherotest.ProviderModule;
import com.test.favherotest.R;
import com.test.favherotest.model.MarvelCharacter;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.model.MarvelImage;
import com.test.favherotest.presenter.ComicDetailPresenter;
import com.test.favherotest.view.adapter.MarvelResultAdapter;
import com.test.favherotest.view.viewDresser.ComicItemViewDresser;

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
public class ComicDetailFragment extends Fragment implements ComicDetailPresenter.View, AdapterView.OnItemClickListener {
    private static final String ARG_COMIC_ID = "comicId";

    private MarvelComic mComic;
    @BindView(R.id.title) TextView mTitle;
    @BindView(R.id.description) WebView mDescription;
    @BindView(R.id.background_image) ImageView mImage;
    @BindView(R.id.character_list) ListView mCharacterListView;

    private ComicDetailFragmentListener mListener;
    private ComicDetailPresenter mPresenter;
    private MarvelResultAdapter adapter;

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
            mPresenter = ProviderModule.getInstance().getComicDetailPresenter(this, mComic.getId());
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
                .load(mComic.getRandomImage().getUrl(MarvelImage.Variants.DETAIL))
                .into(mImage);

        mPresenter.getOnCharactersChanged().subscribe(request -> {
            if (adapter != null) { adapter.dispose(); }
            adapter = new MarvelResultAdapter(getContext(), R.layout.character_item, request, new ComicItemViewDresser(getContext()));
            mCharacterListView.setAdapter(adapter);
            mCharacterListView.setOnItemClickListener(this);
        });

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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MarvelCharacter character = (MarvelCharacter) view.getTag();
        if (character != null) {
            mListener.onCharacterSelected(character);
        }

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
        void onCharacterSelected(MarvelCharacter character);
    }
}
