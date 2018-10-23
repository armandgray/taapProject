package com.armandgray.shared.application;

import android.util.Log;

import com.armandgray.shared.helpers.StringHelper;

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
@PrepareForTest({TAAPViewModel.class, StringHelper.class, Log.class})
@RunWith(PowerMockRunner.class)
public class TAAPViewModelTest {

    private static final String TAAP_VIEW_MODEL = "TAAP_VIEW_MODEL";

    private TAAPViewModel testViewModel;
    private TAAPViewModel.ViewModelObserver testObserver;

    @Before
    public void setUp() {
        PowerMockito.mockStatic(Log.class);
        
        PowerMockito.mockStatic(StringHelper.class);
        PowerMockito.when(StringHelper.toLogTag(Mockito.anyString())).thenReturn(TAAP_VIEW_MODEL);

        testViewModel = new TAAPViewModel() {
        };


        testObserver = testViewModel.new ViewModelObserver() {
            @Override
            public void onNext(Object o) {
                
            }
        };
    }

    @Test
    public void testTAG() {
        Assert.assertThat(testViewModel.TAG, is(TAAP_VIEW_MODEL));
    }

    @Test
    public void testDisposables() {
        Assert.assertThat(testViewModel.disposables, is(notNullValue()));
    }

    @Test
    public void testOnCleared() {
        testViewModel.disposables.add(new Disposable() {
            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        });

        Assert.assertThat(testViewModel.disposables.size(), is(1));
        testViewModel.onCleared();
        Assert.assertThat(testViewModel.disposables.size(), is(0));
    }

    @Test
    public void testToString() {
        Assert.assertThat(testViewModel.toString(),
                is("@" + Integer.toHexString(testViewModel.hashCode())));
    }

    @Test
    public void testViewModelObserver_Type() {
        Assert.assertThat(testObserver, instanceOf(Observer.class));
    }

    @Test
    public void testViewModelObserver_OnSubscribe() {
        testObserver.onSubscribe(new Disposable() {
            @Override
            public void dispose() {
            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        });

        Assert.assertThat(testViewModel.disposables.size(), is(1));
    }

    @Test
    public void testViewModelObserver_OnError() {
        testObserver.onError(new Throwable());
        PowerMockito.verifyStatic(Log.class, Mockito.times(1));
        Log.e(Mockito.anyString(), Mockito.anyString());
    }

    @Test
    public void testViewModelObserver_OnComplete() {
        testObserver.onComplete();
        // Nothing
    }

    @After
    public void tearDown() {
        testViewModel = null;
        testObserver = null;
    }
}