package com.ovlesser.sampletest.viewModel;

import android.content.Context;

import com.ovlesser.sampletest.TestSchedulerRule;
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

import io.reactivex.functions.Predicate;
import io.reactivex.observers.TestObserver;

import static io.reactivex.Single.just;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserListViewModelTest {
    Users users = new Users();
    List<UserViewModel> userViewModelList = new ArrayList<>();

    @Rule public MockitoRule rule = MockitoJUnit.rule();

    @Rule public TestSchedulerRule testSchedulerRule = new TestSchedulerRule();

    @Mock
    ContentApiService contentApiService;

    @InjectMocks
    UserListViewModel userListViewModel;

    @Before
    public void setUp() {
        userListViewModel = new UserListViewModel(mock(Context.class), contentApiService);
        Arrays.asList(users.users).forEach( it -> userViewModelList.add( new UserViewModel(mock(Context.class), it)));
    }

    private boolean compare2List( List<UserViewModel> list1, List<UserViewModel> list2) {
        Map<Integer, UserViewModel> map = new HashMap<>();
        if (list1.size() != list2.size()) return false;
        list1.forEach(it -> map.put(it.getUser().getId(), it));
        return list2.stream().allMatch(it -> it.equal( map.get(it.getUser().getId())));
    }

    // test the mapping from response of the photos API to the List<PhotoViewModel> with mock data
    @Test public void checkFetch() {
        when(contentApiService.users()).thenReturn(
                just(Arrays.asList(users.users))
        );

        TestObserver<List<UserViewModel>> testObserver = userListViewModel.fetch().test();

        testSchedulerRule.getTestScheduler().advanceTimeBy(2, TimeUnit.SECONDS);

        testObserver.assertNoErrors()
                .assertValue(new Predicate<List<UserViewModel>>() {
                    @Override
                    public boolean test(List<UserViewModel> l) throws Exception {
                        return compare2List(l, userViewModelList);
                    }
                });
    }
}