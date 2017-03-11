package com.test.favherotest;

import com.test.favherotest.model.Comic;

import java.util.Arrays;
import java.util.List;

/**
 * Created by comac on 11/03/2017.
 */

class ComicListPresenter {

    private final View mView;

    public ComicListPresenter(View view) {
        mView = view;
    }

    public void setHeroId(long id) {
        mView.showComicList( createMockComicList() );
    }

    private List<Comic> createMockComicList() {
        return Arrays.asList(new Comic[] {
                new Comic("Title1", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 1),
                new Comic("Title2", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 2),
                new Comic("Title3", "http://4.bp.blogspot.com/-SgsiPs5q970/Ts67EnxtItI/AAAAAAAAGRM/CC4_fw2wNLg/s400/ASM1-100.jpg", 3)
        });
    }

    public interface View {
        void showComicList(List<Comic> comicList);
    }
}
