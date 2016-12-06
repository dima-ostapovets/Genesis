package test.com.genesis.ui;

/**
 * Created by dima on 06.12.16.
 */

public interface BasePresenter<T> {
    void attachView(T view);

    void detachView();
}
