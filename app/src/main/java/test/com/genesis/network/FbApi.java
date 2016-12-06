package test.com.genesis.network;

import retrofit.Result;
import retrofit.http.GET;
import rx.Observable;
import test.com.genesis.pojo.PostsResponse;

/**
 * Created by dima on 06.12.16.
 */

public interface FbApi {
    @GET("/v2.8/me/posts")
    Observable<Result<PostsResponse>> getPosts();
}
