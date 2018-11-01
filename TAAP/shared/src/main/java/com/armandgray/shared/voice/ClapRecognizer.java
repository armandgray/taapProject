package com.armandgray.shared.voice;

import android.os.Handler;
import android.os.Looper;

import com.armandgray.shared.rx.CountedSubjectWrapper;
import com.armandgray.shared.rx.SchedulerProvider;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.android.AudioDispatcherFactory;
import be.tarsos.dsp.onsets.OnsetHandler;
import be.tarsos.dsp.onsets.PercussionOnsetDetector;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

@VoiceManager.VoiceScope
public class ClapRecognizer {

    private static final int SAMPLE_RATE = 22050;
    private static final int BUFFER_SIZE = 1024;
    private static final double THRESHOLD = 8;
    private static final double SENSITIVITY = 20;
    private static final int DOUBLE_CLAP_TIME_THRESHOLD = 1;

    @Inject
    SchedulerProvider schedulers;

    @Nullable
    private Disposable lastEventDisposable;
    private double lastEventTime;

    @NonNull
    private CountedSubjectWrapper<VoiceEvent> eventSubject = CountedSubjectWrapper.createBehavior();

    private Handler handler;
    private AudioDispatcher dispatcher;

    @Inject
    ClapRecognizer() {
    }

    synchronized void registerListener() {
        unregisterListener();
        captureHandler();

        eventSubject.onNext(VoiceEvent.ACTIVE);
        dispatcher = AudioDispatcherFactory.fromDefaultMicrophone(SAMPLE_RATE, BUFFER_SIZE, 0);
        dispatcher.addAudioProcessor(new PercussionOnsetDetector(SAMPLE_RATE, BUFFER_SIZE,
                onsetHandler(), SENSITIVITY, THRESHOLD));

        handler.post(dispatcher);
    }

    synchronized void unregisterListener() {
        if (handler != null && dispatcher != null) {
            handler.removeCallbacks(dispatcher);
            handler = null;
        }
    }

    private void captureHandler() {
        if (Looper.myLooper() == null) {
            throw new IllegalStateException("Execution required on Scheduler with Looper");
        }

        handler = new Handler(Looper.myLooper());
    }

    private synchronized OnsetHandler onsetHandler() {
        return (time, salience) -> {
            if (isDoubleEvent(time)) {
                eventSubject.onNext(VoiceEvent.DOUBLE_CLAP);
                disposeLastEvent();
            } else {
                sendDelayed();
            }

            System.out.println(time + ", " + salience);
            lastEventTime = time;
        };
    }

    private boolean isDoubleEvent(double time) {
        return Math.abs(lastEventTime - time) < DOUBLE_CLAP_TIME_THRESHOLD;
    }

    private void disposeLastEvent() {
        if (lastEventDisposable != null) {
            lastEventDisposable.dispose();
            lastEventDisposable = null;
        }
    }

    private void sendDelayed() {
        lastEventDisposable = Observable.just(VoiceEvent.CLAP)
                .delay(DOUBLE_CLAP_TIME_THRESHOLD, TimeUnit.SECONDS)
                .subscribeOn(schedulers.io())
                .subscribe(eventSubject::onNext);
    }

    @NonNull
    public CountedSubjectWrapper<VoiceEvent> getEventSubject() {
        return eventSubject;
    }
}
