package com.armandgray.shared.rx;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.PublishSubject;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;

public class CountedSubjectWrapperTest {

    private static final String TEST_STRING = "TEST_STRING";

    private CountedSubjectWrapper<String> testCountedSubjectWrapper;
    private boolean testFlag;

    @Before
    public void setUp() {
        testCountedSubjectWrapper = CountedSubjectWrapper.createBehavior();
        testFlag = false;
    }

    @Test
    public void testCreateBehavior() {
        CountedSubjectWrapper<Object> wrapper = CountedSubjectWrapper.createBehavior();
        Assert.assertThat(wrapper, is(notNullValue()));
        Assert.assertThat(wrapper.subject, instanceOf(BehaviorSubject.class));
    }

    @Test
    public void testCreatePublish() {
        CountedSubjectWrapper<Object> wrapper = CountedSubjectWrapper.createPublish();
        Assert.assertThat(wrapper, is(notNullValue()));
        Assert.assertThat(wrapper.subject, instanceOf(PublishSubject.class));
    }

    @Test
    public void testOnNext() {
        BehaviorSubject behaviorSubject = (BehaviorSubject) testCountedSubjectWrapper.subject;
        Assert.assertThat(behaviorSubject.getValue(), is(nullValue()));

        testCountedSubjectWrapper.onNext(TEST_STRING);

        Assert.assertThat(behaviorSubject.getValue(), is(TEST_STRING));
    }

    @Test
    public void testDoOnObserved() {
        testCountedSubjectWrapper.doOnObserved(() -> testFlag = true);
        testCountedSubjectWrapper.toObservable().subscribe();
        Assert.assertThat(testFlag, is(true));
    }

    @Test
    public void testDoOnUnObserved() {
        testCountedSubjectWrapper.doOnUnObserved(() -> testFlag = true);
        testCountedSubjectWrapper.toObservable().subscribe().dispose();
        Assert.assertThat(testFlag, is(true));
    }

    @After
    public void tearDown() {
        testCountedSubjectWrapper = null;
        testFlag = false;
    }
}