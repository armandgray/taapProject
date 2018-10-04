package org.mockito.configuration;

/**
 * Created by armandgray on 4/13/18
 */

@SuppressWarnings("unused")
public class MockitoConfiguration extends DefaultMockitoConfiguration {

    @Override
    public boolean enableClassCache() {
        return false;
    }
}
