package test.com.genesis.di;

import dagger.Module;
import dagger.Provides;
import test.com.genesis.network.FbModel;
import test.com.genesis.ui.adapters.presenter.MainPresenter;
import test.com.genesis.ui.adapters.presenter.SinglePostPresenter;

/**
 * Created by dima on 08.12.16.
 */

@Module
public class PresenterModule {
    @Provides
    MainPresenter getMainPresenter(FbModel fbModel) {
        return new MainPresenter(fbModel);
    }

    @Provides
    SinglePostPresenter getPostPresenter() {
        return new SinglePostPresenter();
    }
}
