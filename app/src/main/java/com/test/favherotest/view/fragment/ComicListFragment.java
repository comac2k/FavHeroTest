package com.test.favherotest.view.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.R;
import com.test.favherotest.model.MarvelCharacter;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.presenter.ComicListPresenter;
import com.test.favherotest.view.adapter.MarvelResultAdapter;
import com.test.favherotest.view.viewDresser.ComicItemViewDresser;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ComicListFragmentListener} interface
 * to handle interaction events.
 * Use the {@link ComicListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComicListFragment extends BaseFragment implements ComicListPresenter.View, AdapterView.OnItemClickListener {
    private static final String ARG_HERO_ID = "heroId";

    private ComicListFragmentListener mListener;
    private ComicListPresenter mPresenter;

    @BindView(R.id.comic_list) ListView mComicListView;
    @BindView(R.id.character_name) TextView mCharacterName;
    @BindView(R.id.spinner_image) ImageView mSpinnerImage;
    @BindView(R.id.no_data_text) TextView mNoDataText;

    private MarvelResultAdapter adapter;
    private MarvelCharacter mCharacter;

    public ComicListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param character Parameter 1.
     * @return A new instance of fragment ComicListFragment.
     */
    public static ComicListFragment newInstance(MarvelCharacter character) {
        ComicListFragment fragment = new ComicListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_HERO_ID, character);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCharacter = (MarvelCharacter)getArguments().getSerializable(ARG_HERO_ID);
            mPresenter = ProviderModule.getInstance().getComicListPresenter(this, mCharacter.getId());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_list, container, false);
        ButterKnife.bind(this, view);
        mComicListView.setEmptyView(mNoDataText);
        mComicListView.setOnItemClickListener(this);
        mCharacterName.setText(mCharacter.getName());
        mSpinnerImage.setBackgroundResource(R.drawable.spinner);
        mSpinnerImage.setVisibility(View.VISIBLE);
        ((AnimationDrawable)mSpinnerImage.getBackground()).start();
        mPresenter.getOnComicsChanged().subscribe(request -> {
            if (adapter != null) { adapter.dispose(); }
            adapter = new MarvelResultAdapter(getContext(), R.layout.comic_item, request, new ComicItemViewDresser(getContext()));
            mSubscriptions.add(adapter.getOnReady().subscribe(ignored -> {
                ((AnimationDrawable)mSpinnerImage.getBackground()).stop();
                mSpinnerImage.setVisibility(View.GONE);
            }));
            mComicListView.setAdapter(adapter);
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        if (adapter != null) { adapter.dispose(); }
        super.onDestroyView();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ComicListFragmentListener) {
            mListener = (ComicListFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ComicListFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MarvelComic comicId = (MarvelComic)view.getTag();
        if (comicId != null) {
            mListener.onComicSelected(comicId);
        }
    }

    public interface ComicListFragmentListener {
        void onComicSelected(MarvelComic comicId);
    }
}
