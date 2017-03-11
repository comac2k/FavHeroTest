package com.test.favherotest.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.test.favherotest.ProviderModule;
import com.test.favherotest.R;
import com.test.favherotest.model.Comic;
import com.test.favherotest.presenter.ComicListPresenter;

import java.util.List;

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
public class ComicListFragment extends Fragment implements ComicListPresenter.View {
    private static final String ARG_HERO_ID = "heroId";

    private ComicListFragmentListener mListener;
    private ComicListPresenter mPresenter = ProviderModule.getInstance().getComicListPresenter(this);

    @BindView(R.id.comic_list) ListView mComicListView;
    private ComicListAdapter mListAdapter;

    public ComicListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param heroId Parameter 1.
     * @return A new instance of fragment ComicListFragment.
     */
    public static ComicListFragment newInstance(long heroId) {
        ComicListFragment fragment = new ComicListFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_HERO_ID, heroId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            setHeroId(getArguments().getLong(ARG_HERO_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comic_list, container, false);
        ButterKnife.bind(this, view);
        if (mListAdapter != null) {
            mComicListView.setAdapter(mListAdapter);
        }
        return view;
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

    public void setHeroId(long id) {
        mPresenter.setHeroId(id);
    }

    @Override
    public void showComicList(List<Comic> comicList) {
        setListAdapter(comicList);
    }

    private void setListAdapter(List<Comic> comicList) {
        mListAdapter = new ComicListAdapter(getContext(), comicList);
        if (mComicListView != null) {
            mComicListView.setAdapter(mListAdapter);
        }

    }

    public interface ComicListFragmentListener {
    }
}
