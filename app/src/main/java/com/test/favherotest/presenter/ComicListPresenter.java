package com.test.favherotest.presenter;

import com.test.favherotest.model.Comic;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by comac on 11/03/2017.
 */

public class ComicListPresenter {

    private final View mView;
    private BehaviorSubject<List<Comic>> mOnComicsChanged = BehaviorSubject.create();

    public ComicListPresenter(View view) {
        mView = view;
    }

    public void setHeroId(long id) {
        mOnComicsChanged.onNext(createMockComicList(id));
    }

    private List<Comic> createMockComicList(long heroId) {
        return Arrays.asList(new Comic[] {
                new Comic("Title1 ("+heroId+")", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 1),
                new Comic("Title2 ("+heroId+")", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 2),
                new Comic("Title3 ("+heroId+")", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 3)
        });
    }

    public Observable<List<Comic>> getOnComicsChanged() {
        return mOnComicsChanged;
    }

    public interface View {
    }
}
