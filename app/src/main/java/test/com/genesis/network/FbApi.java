package test.com.genesis.network;

import retrofit.Result;
import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;
import test.com.genesis.pojo.PostsResponse;
import test.com.genesis.pojo.User;

/**
 * Created by dima on 06.12.16.
 */

public interface FbApi {
    @GET("/v2.8/me/posts")
    Observable<Result<PostsResponse>> getPosts(@Query("fields") String fields);

    @GET("/v2.8/me")
    Observable<Result<User>> getUser(@Query("fields") String fields);
}
