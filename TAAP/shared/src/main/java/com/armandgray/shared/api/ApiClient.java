package com.armandgray.shared.api;

import com.armandgray.shared.model.TAAPResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by armandgray on 7/12/18
 */
public class ApiClient {

    static final String BASE_URL = "https://api.flickr.com/services/rest/";

    private static ApiClient instance;

    private final ApiServiceGenerator generator;

    private ApiClient(ApiServiceGenerator generator) {
        this.generator = generator;
    }

    // Object is Dependency Injection Candidate (ie. under Dagger2)
    static ApiClient get(ApiServiceGenerator generator) {
        if (instance == null) {
            instance = new ApiClient(generator);
        }

        return instance;
    }

    // Nullable not propagated up
    public TAAPApiService taapApi() {
        return (TAAPApiService) generator.getService(TAAPApiService.class);
    }

    // Consideration for abstracting api for modification
    public interface TAAPApiService {

        @GET("?method=flickr.photos.getRecent")
        Call<TAAPResponse> getRecent(@Query("per_page") int countPerPage,
                                     @Query("page") int page);

        @GET("?method=flickr.photos.search")
        Call<TAAPResponse> searchImages(@Query("text") String search,
                                        @Query("per_page") int countPerPage,
                                        @Query("page") int pageNumber);
    }
}
