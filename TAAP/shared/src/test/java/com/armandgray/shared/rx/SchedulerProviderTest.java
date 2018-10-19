package com.armandgray.shared.rx;

import org.junit.Assert;
import org.junit.Test;

import io.reactivex.Scheduler;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

public class SchedulerProviderTest {

    @Test
    public void testSchedulerProvider() {
        SchedulerProvider provider = new SchedulerProvider() {
            @Override
            public Scheduler ui() {
                return null;
            }

            @Override
            public Scheduler io() {
                return null;
            }

            @Override
            public Scheduler trampoline() {
                return null;
            }

            @Override
            public Scheduler computation() {
                return null;
            }

            @Override
            public Scheduler newThread() {
                return null;
            }

            @Override
            public Scheduler single() {
                return null;
            }
        };

        Assert.assertThat(provider, is(notNullValue()));
    }

    @Test
    public void testSchedulerProvider_DefaultCallsScheduler() {
        // TODO implement tests
    }
}
