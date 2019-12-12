package com.armandgray.shared.api;

import java.io.IOException;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by armandgray on 7/13/18
 */
@NetworkManager.NetworkScope
class NetworkInterceptor implements Interceptor {

    @SuppressWarnings("SpellCheckingInspection")
    private static final String JSON_ONLY_QUERY_PARAM = "nojsoncallback";
    private static final String JSON_ONLY_TRUE = "1";
    private static final String API_KEY_QUERY_PARAM = "api_key";
    private static final String API_KEY = "1508443e49213ff84d566777dc211f2a";
    private static final String FORMAT_QUERY_PARAM = "format";
    private static final String JSON = "json";
    private static final String SAFE_SEARCH_QUERY_PARAM = "safe_search";
    private static final String RESTRICTED = "3";

    @Inject
    NetworkInterceptor() {
    }

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder()
                .addQueryParameter(JSON_ONLY_QUERY_PARAM, JSON_ONLY_TRUE)
                .addQueryParameter(API_KEY_QUERY_PARAM, API_KEY)
                .addQueryParameter(FORMAT_QUERY_PARAM, JSON)
                .addQueryParameter(SAFE_SEARCH_QUERY_PARAM, RESTRICTED) // Flickr is Scary (this doesn't work! eek!!!)
                .build();

        return chain.proceed(request.newBuilder().url(url).build());
    }
}
