package com.armandgray.shared.api;

import com.armandgray.shared.model.TAAPResponse;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.armandgray.shared.api.NetworkManager.*;

/**
 * Created by armandgray on 7/12/18
 */
@NetworkScope
public class ApiClient {

    static final String BASE_URL = "https://api.flickr.com/services/rest/";

    private static ApiClient instance;

    private final ApiServiceGenerator generator;

    @Inject
    ApiClient(ApiServiceGenerator generator) {
        this.generator = generator;
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
