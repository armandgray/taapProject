package com.armandgray.shared.api;

import android.util.Log;
import android.util.SparseArray;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.armandgray.shared.api.ApiClient.BASE_URL;

/**
 * Created by armandgray on 7/12/18
 */
@NetworkManager.NetworkScope
public class ApiServiceGenerator {

    private static final String TAG = "API_SERVICE_GENERATOR";

    private static final Set<Integer> AVAILABLE_SERVICES;

    // static Builders allows for extension while maintaining built-in performance improvements
    private static final OkHttpClient.Builder HTTP_CLIENT_BUILDER;
    private static final Retrofit.Builder RETROFIT_BUILDER;

    @SuppressWarnings("CanBeFinal") // Suppressed considering potential Lazy Instantiation
    private static Retrofit retrofit;

    private final SparseArray<Object> services;

    static {
        //noinspection RedundantArrayCreation
        AVAILABLE_SERVICES = new HashSet<>(Arrays.asList(new Integer[]{
                ApiClient.TAAPApiService.class.hashCode()
        }));

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        HTTP_CLIENT_BUILDER = new OkHttpClient.Builder() // extend with newBuilder()
                .addInterceptor(loggingInterceptor);

        // Reusable Converters
        ScalarsConverterFactory scalarConverter = ScalarsConverterFactory.create();
        GsonConverterFactory gsonConverter = GsonConverterFactory.create();

        RETROFIT_BUILDER = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(scalarConverter)
                .addConverterFactory(gsonConverter);
    }

    @Inject
    ApiServiceGenerator(NetworkInterceptor networkInterceptor) {
        this.services = new SparseArray<>(AVAILABLE_SERVICES.size());

        HTTP_CLIENT_BUILDER.addInterceptor(networkInterceptor);
        retrofit = RETROFIT_BUILDER.client(HTTP_CLIENT_BUILDER.build()).build();
    }

    @SuppressWarnings({"SameParameterValue", "unchecked"})
    @Nullable
    Object getService(Class sClass) {
        if (!AVAILABLE_SERVICES.contains(sClass.hashCode())) {
            Log.e(TAG, "createService failed: Specified ApiService does not exist");
            return null;
        }

        Object service = services.get(sClass.hashCode());
        if (service == null) {
            service = retrofit.create(sClass);
            services.put(sClass.hashCode(), service);
        }

        return service;
    }
}
