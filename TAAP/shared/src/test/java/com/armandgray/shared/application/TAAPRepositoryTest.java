package com.armandgray.shared.application;

import android.util.Log;

import com.armandgray.shared.helpers.StringHelpers;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.robolectric.annotation.Config;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@Config(manifest = Config.NONE)
@PrepareForTest({TAAPRepositoryTest.class, StringHelpers.class, Log.class})
@RunWith(PowerMockRunner.class)
public class TAAPRepositoryTest {

    private static final String TAAP_REPOSITORY = "TAAP_REPOSITORY";

    private TAAPRepository testRepository;
    private TAAPRepository.RepositoryObserver testObserver;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Log.class);

        PowerMockito.mockStatic(StringHelpers.class);
        PowerMockito.when(StringHelpers.toLogTag(Mockito.anyString())).thenReturn(TAAP_REPOSITORY);

        testRepository = new TAAPRepository() {
        };

        testObserver = testRepository.new RepositoryObserver() {

            @Override
            public void onNext(Object o) {

            }
        };
    }

    @Test
    public void testTAG() {
        Assert.assertThat(testRepository.TAG, is(TAAP_REPOSITORY));
    }

    @Test
    public void testDisposables() {
        Assert.assertThat(testRepository.disposables, is(notNullValue()));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testRepository.toString(),
                is("@" + Integer.toHexString(testRepository.hashCode())));
    }

    @Test
    public void testRepositoryObserver_Type() {
        Assert.assertThat(testObserver, instanceOf(Observer.class));
    }

    @Test
    public void testRepositoryObserver_OnSubscribe() {
        testObserver.onSubscribe(new Disposable() {
            @Override
            public void dispose() {
            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        });

        Assert.assertThat(testRepository.disposables.size(), is(1));
    }

    @Test
    public void testRepositoryObserver_OnError() {
        testObserver.onError(new Throwable());
        PowerMockito.verifyStatic(Log.class, Mockito.times(1));
        Log.e(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testRepositoryObserver_OnComplete() {
        testObserver.onComplete();
        // Nothing
    }

    @After
    public void tearDown() {
        testRepository = null;
        testObserver = null;
    }
}