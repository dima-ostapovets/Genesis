package test.com.genesis.ui.adapters.presenter;

import java.util.List;

import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import test.com.genesis.network.FbModel;
import test.com.genesis.pojo.Post;
import test.com.genesis.pojo.PostsResponse;
import test.com.genesis.pojo.User;
import test.com.genesis.ui.LceView;

/**
 * Created by dima on 07.12.16.
 */

public class MainPresenter extends BasePresenter<MainPresenter.MainView> {

    private FbModel model;

    public MainPresenter(FbModel model) {
        this.model = model;
    }

    @Override
    public void attachView(MainView view) {
        super.attachView(view);
        loadPosts();
        loadUser();
    }

    private void loadPosts() {
        view.showProgress(true);
        model.getPosts()
                .flatMap(new Func1<PostsResponse, Observable<Post>>() {
                    @Override
                    public Observable<Post> call(PostsResponse postsResponse) {
                        return Observable.from(postsResponse.data);
                    }
                })
                .toSortedList(new Func2<Post, Post, Integer>() {
                    @Override
                    public Integer call(Post post, Post post2) {
                        Integer totalCount = post.likes.summary.totalCount;
                        Integer totalCount2 = post2.likes.summary.totalCount;
                        if (totalCount > totalCount2) return 1;
                        if (totalCount < totalCount2) return -1;
                        return 0;
                    }
                })
                .finallyDo(new Action0() {
                    @Override
                    public void call() {
                        if (view != null) {
                            view.showProgress(false);
                        }
                    }
                })
                .subscribe(new Action1<List<Post>>() {
                    @Override
                    public void call(List<Post> posts) {
                        if (view != null) {
                            view.showContent(posts);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                        if (view != null) {
                            view.showError(throwable.getMessage());
                        }
                    }
                });
    }

    private void loadUser() {
        model.getUser()
                .subscribe(new Action1<User>() {
                    @Override
                    public void call(User user) {
                        if (view != null) {
                            view.showUser(user);
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        throwable.printStackTrace();
                    }
                });
    }

    public interface MainView extends LceView<List<Post>> {
        void showUser(User user);
    }
}
