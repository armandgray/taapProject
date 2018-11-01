package com.armandgray.shared.voice;

import com.armandgray.shared.application.TAAPAppComponent;
import com.armandgray.shared.permission.DangerousPermission;
import com.armandgray.shared.permission.PermissionManager;
import com.armandgray.shared.rx.CountedSubjectWrapper;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import io.reactivex.Observable;
import io.reactivex.functions.Action;

import static org.hamcrest.CoreMatchers.is;

public class VoiceManagerImplTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private TAAPAppComponent mockAppComponent;

    @Mock
    private VoiceManager.Component mockComponent;

    @Mock
    private VoiceManager.Component.Builder mockBuilder;

    @Mock
    private ClapRecognizer mockClapRecognizer;

    @Mock
    private PermissionManager mockPermissionManager;

    @Mock
    private CountedSubjectWrapper<VoiceEvent> mockCountedSubjectWrapper;

    @Mock
    private Observable<VoiceEvent> mockObservable;

    private VoiceManagerImpl testVoiceManager;

    @Before
    public void setUp() {
        testVoiceManager = new VoiceManagerImpl();
        testVoiceManager.clapRecognizer = mockClapRecognizer;
        testVoiceManager.permissionManager = mockPermissionManager;

        Mockito.when(mockAppComponent.voiceBuilder()).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.voiceManager(testVoiceManager)).thenReturn(mockBuilder);
        Mockito.when(mockBuilder.managerModule(Mockito.any(VoiceManager.ManagerModule.class)))
                .thenReturn(mockBuilder);
        Mockito.when(mockBuilder.build()).thenReturn(mockComponent);

        Mockito.when(mockClapRecognizer.getEventSubject())
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.doOnObserved(Mockito.any()))
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.doOnUnObserved(Mockito.any()))
                .thenReturn(mockCountedSubjectWrapper);
        Mockito.when(mockCountedSubjectWrapper.toObservable()).thenReturn(mockObservable);

        Mockito.when(mockPermissionManager.hasPermission(DangerousPermission.MICROPHONE))
                .thenReturn(true);
    }

    @Test
    public void testInject() {
        testVoiceManager.inject(mockAppComponent);

        Mockito.verify(mockAppComponent, Mockito.times(1)).voiceBuilder();
        Mockito.verify(mockBuilder, Mockito.times(1)).voiceManager(testVoiceManager);
        Mockito.verify(mockBuilder, Mockito.times(1))
                .managerModule(Mockito.any(VoiceManager.ManagerModule.class));
        Mockito.verify(mockBuilder, Mockito.times(1)).build();
        Mockito.verify(mockComponent, Mockito.times(1)).inject(testVoiceManager);
    }

    @Test
    public void testGetClapObservable_ReturnsMissingPermission() {
        Mockito.when(mockPermissionManager.hasPermission(DangerousPermission.MICROPHONE))
                .thenReturn(false);

        Assert.assertThat(testVoiceManager.getClapObservable().blockingLast(),
                is(VoiceEvent.MISSING_PERMISSION));
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_GetsEventListenerSubject() {
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockClapRecognizer, Mockito.times(1)).getEventSubject();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_DoOnObserver() {
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnObserved(Mockito.any());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_DoOnObserver_PassesRegisterCall() throws Exception {
        // Arrange
        ArgumentCaptor<Action> captor = ArgumentCaptor.forClass(Action.class);
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnObserved(captor.capture());

        // Act
        captor.getValue().run();

        // Assert
        Mockito.verify(mockClapRecognizer, Mockito.times(1)).registerListener();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_DoOnObserver_PassesUnRegisterCall() throws Exception {
        // Arrange
        ArgumentCaptor<Action> captor = ArgumentCaptor.forClass(Action.class);
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnUnObserved(captor.capture());

        // Act
        captor.getValue().run();

        // Assert
        Mockito.verify(mockClapRecognizer, Mockito.times(1)).unregisterListener();
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_DoOnUnObserver() {
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).doOnUnObserved(Mockito.any());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testGetClapObservable_ToObservable() {
        testVoiceManager.getClapObservable().subscribe();
        Mockito.verify(mockCountedSubjectWrapper, Mockito.times(1)).toObservable();
    }

    @Test
    public void testGetCallOutObservable_ReturnsMissingPermission() {
        Mockito.when(mockPermissionManager.hasPermission(DangerousPermission.MICROPHONE))
                .thenReturn(false);

        Assert.assertThat(testVoiceManager.getCallOutObservable().blockingLast(),
                is(VoiceEvent.MISSING_PERMISSION));
    }

    @Ignore
    @Test
    public void testGetCallOutObservable() {

    }

    @Test
    public void testToString() {
        Assert.assertThat(testVoiceManager.toString(), is("VoiceManagerImpl@"
                + Integer.toHexString(testVoiceManager.hashCode())));
    }

    @After
    public void tearDown() {
        testVoiceManager = null;
    }
}
