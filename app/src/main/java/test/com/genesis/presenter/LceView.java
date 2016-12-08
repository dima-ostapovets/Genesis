package test.com.genesis.presenter;

import java.util.List;

import test.com.genesis.pojo.Post;

/**
 * Created by dima on 07.12.16.
 */

public interface LceView<T> {
    void showProgress(boolean show);
    void showContent(T t);
    void showError(String error);
}
