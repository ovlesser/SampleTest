package com.ovlesser.sampletest.viewModel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.ovlesser.sampletest.TestSchedulerRule;
import com.ovlesser.sampletest.model.Photo;
import com.ovlesser.sampletest.model.Photos;
import com.ovlesser.sampletest.model.Users;
import com.ovlesser.sampletest.network.ContentApiService;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AlbumViewModelTest {
    Users users = new Users();
    Photos photos = new Photos();
    List<PhotoViewModel> photoViewModelList = new ArrayList<>();

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule public TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Mock
    ContentApiService contentApiService;

    @InjectMocks
    AlbumViewModel albumViewModel;

    @Before
    public void setUp() {
        albumViewModel = new AlbumViewModel(mock(Context.class), users.users[1], contentApiService);
        Arrays.asList(photos.photos).forEach(it -> photoViewModelList.add( new PhotoViewModel(mock(Context.class), it)));
    }

    private boolean compare2List( List<PhotoViewModel> list1, List<PhotoViewModel> list2) {
        Map<Integer, PhotoViewModel> map = new HashMap<>();
        if (list1.size() != list2.size()) return false;
        list1.forEach(it -> map.put(it.getPhoto().getId(), it));
        return list2.stream().allMatch(it -> it.equal( map.get(it.getPhoto().getId())));
    }

    // test the mapping from response of the user API to the List<UserViewModel> with mock data
    @Test
    public void checkFetch() {
        MutableLiveData<List<Photo>> liveData = new MutableLiveData<List<Photo>>();
        liveData.setValue((Arrays.asList(photos.photos)));
        when(contentApiService.photos(users.users[1].getId())).thenReturn(  mock(LiveData.class));

        TestObserver<List<PhotoViewModel>> testObserver = albumViewModel.fetch().test();

        testSchedulerRule.getTestScheduler().advanceTimeBy(2, TimeUnit.SECONDS);

        testObserver.assertNoErrors()
                .assertValue(new Predicate<List<PhotoViewModel>>() {
                    @Override
                    public boolean test(List<PhotoViewModel> l) throws Exception {
                        return compare2List(l, photoViewModelList);
                    }
                });
    }
}