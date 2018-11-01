package com.armandgray.shared.voice;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

public class VoiceEventTest {

    @Test
    public void testVoiceEvent_Instances() {
        VoiceEvent[] values = VoiceEvent.values();
        Assert.assertThat(values.length, is(8));
        Assert.assertThat(Arrays.asList(values), containsInAnyOrder(
                VoiceEvent.NONE,
                VoiceEvent.TIMEOUT,
                VoiceEvent.MISSING_PERMISSION,
                VoiceEvent.ACTIVE,
                VoiceEvent.INACTIVE,
                VoiceEvent.CLAP,
                VoiceEvent.DOUBLE_CLAP,
                VoiceEvent.SPEECH));
    }
}
