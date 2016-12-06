package test.com.genesis.network;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import test.com.genesis.pojo.PostsResponse;

/**
 * Created by dima on 07.12.16.
 */

public class FbModel {
    private FbApi api;
    private Scheduler workScheduler;
    private Scheduler observeScheduler;

    public FbModel(FbApi api, Scheduler workScheduler, Scheduler observeScheduler) {
        this.api = api;
        this.workScheduler = workScheduler;
        this.observeScheduler = observeScheduler;
    }

    public Observable<PostsResponse> getPosts() {
        return api.getPosts()
                .compose(new ErrorTransformer<PostsResponse>())
                .subscribeOn(workScheduler)
                .observeOn(observeScheduler);
    }

}
