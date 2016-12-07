package test.com.genesis.ui.adapters.presenter;

import android.support.annotation.Nullable;

/**
 * Created by dima on 06.12.16.
 */

public abstract class BasePresenter<T> {
    @Nullable
    protected T view;

    public void attachView(T view) {
        this.view = view;
    }

    public void detachView() {
        this.view = null;
    }
}
