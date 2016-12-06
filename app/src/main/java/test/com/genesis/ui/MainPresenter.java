package test.com.genesis.ui;

import android.support.annotation.Nullable;

import java.util.List;

import rx.functions.Action0;
import rx.functions.Action1;
import test.com.genesis.network.FbModel;
import test.com.genesis.pojo.Post;
import test.com.genesis.pojo.PostsResponse;

/**
 * Created by dima on 07.12.16.
 */

public class MainPresenter implements BasePresenter<LceView<List<Post>>> {

    @Nullable
    private LceView<List<Post>> view;
    private FbModel model;

    public MainPresenter(FbModel model) {
        this.model = model;
    }

    @Override
    public void attachView(LceView<List<Post>> view) {
        this.view = view;
        loadPosts();
    }

    @Override
    public void detachView() {
        view = null;
    }

    private void loadPosts() {
        view.showProgress(true);
        model.getPosts()
                .finallyDo(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) {
                            view.showProgress(false);
                        }
                    }
                })
                .subscribe(new Action1<PostsResponse>() {
                    @Override
                    public void call(PostsResponse postsResponse) {
                        if (view != null) {
                            view.showContent(postsResponse.data);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (view != null) {
                            view.showError(throwable.getMessage());
                        }
                    }
                });
    }
}
