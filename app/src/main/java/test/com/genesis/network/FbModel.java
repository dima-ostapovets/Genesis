package test.com.genesis.network;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Scheduler;
import test.com.genesis.pojo.PostsResponse;
import test.com.genesis.pojo.User;

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
        return api.getPosts("id,message,story,link,attachments,likes.summary(true),created_time")
                .compose(new ErrorTransformer<PostsResponse>())
                .subscribeOn(workScheduler)
                .observeOn(observeScheduler);
    }

    public Observable<User> getUser() {
        return api.getUser("id,email,picture,name")
                .compose(new ErrorTransformer<User>())
                .subscribeOn(workScheduler)
                .observeOn(observeScheduler);
    }

}
