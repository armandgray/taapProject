package com.armandgray.shared.db;

import android.content.Context;
import android.content.SharedPreferences;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.util.HashSet;
import java.util.Set;

import static com.armandgray.shared.db.SharedPreferencesWrapper.REQUESTED_PERMISSIONS;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class SharedPreferencesWrapperTest {

    private static final HashSet<String> TEST_STRING_SET = new HashSet<>();

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();

    @Mock
    private Context mockContext;

    @Mock
    private SharedPreferences mockSharedPreferences;

    @Mock
    private SharedPreferences.Editor mockEditor;

    private SharedPreferencesWrapper testWrapper;

    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        Mockito.when(mockContext.getSharedPreferences(Mockito.anyString(), Mockito.anyInt()))
                .thenReturn(mockSharedPreferences);

        Mockito.when(mockSharedPreferences.getStringSet(Mockito.anyString(),
                Mockito.any(Set.class))).thenReturn(TEST_STRING_SET);
        Mockito.when(mockSharedPreferences.edit()).thenReturn(mockEditor);
        Mockito.when(mockEditor.putStringSet(Mockito.anyString(),
                Mockito.any(Set.class))).thenReturn(mockEditor);

        testWrapper = new SharedPreferencesWrapper(mockContext);
    }

    @Test
    public void testConstructor_SharedPreferencesParams() {
        Mockito.verify(mockContext, Mockito.times(1))
                .getSharedPreferences("TAAP_SHARED_PREFERENCES", Context.MODE_PRIVATE);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testGetRequestedPermissions() {
        // Arrange
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Set<String>> defaultCaptor = ArgumentCaptor.forClass(Set.class);

        // Act
        Assert.assertThat(testWrapper.getRequestedPermissions(), is(notNullValue()));

        // Assert
        Mockito.verify(mockSharedPreferences, Mockito.times(1))
                .getStringSet(keyCaptor.capture(), defaultCaptor.capture());

        Assert.assertThat(keyCaptor.getValue(), is(REQUESTED_PERMISSIONS));
        Assert.assertThat(defaultCaptor.getValue().size(), is(0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetRequestedPermissions() {
        // Arrange
        ArgumentCaptor<String> keyCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Set<String>> defaultCaptor = ArgumentCaptor.forClass(Set.class);

        // Act
        testWrapper.setRequestedPermissions(TEST_STRING_SET);

        // Assert
        Mockito.verify(mockEditor, Mockito.times(1))
                .putStringSet(keyCaptor.capture(), defaultCaptor.capture());

        Assert.assertThat(keyCaptor.getValue(), is(REQUESTED_PERMISSIONS));
        Assert.assertThat(defaultCaptor.getValue(), is(TEST_STRING_SET));
    }

    @After
    public void tearDown() {
        testWrapper = null;
    }
}
