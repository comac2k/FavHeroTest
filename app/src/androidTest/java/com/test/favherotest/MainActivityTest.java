package com.test.favherotest;

import android.content.Intent;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.test.favherotest.model.MarvelApiResponse;
import com.test.favherotest.model.MarvelComic;
import com.test.favherotest.model.MarvelImage;
import com.test.favherotest.service.MarvelAPI;
import com.test.favherotest.view.activity.MainActivity;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import rx.Observable;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withTagValue;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {


    private static final String TEST_TITLE = "Test Comic";
    private static final String TEST_DESCRIPTION = "Test Description";
    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Mock MarvelAPI marvelApiMock;

    @Before
    public void initMocks() {
        MockitoAnnotations.initMocks(this);
        ProviderModule mockedProviderModule = spy(new ProviderModule());
        doReturn(Observable.just(getMockedComicsReponse())).when(marvelApiMock).getComics(anyLong(), anyString(), anyString(), anyString(), anyInt(), anyInt());
        when(mockedProviderModule.provideMarvelApi()).thenReturn(marvelApiMock);
        ProviderModule.setInstance(mockedProviderModule);
        mActivityRule.launchActivity(new Intent());
    }

    @Test
    public void comicsShowInList() throws Exception {
        onView(withId(R.id.comic_list)).check(matches(hasDescendant(withText(TEST_TITLE))));
    }

    @Test
    public void listClicksShowDetails() throws Exception {
        onView(withText(TEST_TITLE)).perform(click());
        onView(withId(R.id.description)).check(matches(withTagValue(Matchers.equalTo(TEST_DESCRIPTION))));
    }

    private MarvelApiResponse<MarvelComic> getMockedComicsReponse() {
        List<MarvelComic> values = Arrays.asList(new MarvelComic[]{ new MarvelComic(1l, TEST_TITLE, TEST_DESCRIPTION, new MarvelImage(), getMockedImageList()) });
        return new MarvelApiResponse<MarvelComic>().withValues(values);
    }

    private List<MarvelImage> getMockedImageList() {
        return Arrays.asList(new MarvelImage[]{ new MarvelImage() });
    }
}
