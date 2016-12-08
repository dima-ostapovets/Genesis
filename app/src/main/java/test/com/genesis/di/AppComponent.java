package test.com.genesis.di;

import javax.inject.Singleton;

import dagger.Component;
import test.com.genesis.repository.IAuthRepository;
import test.com.genesis.presenter.MainPresenter;
import test.com.genesis.presenter.SinglePostPresenter;

/**
 * Created by dima on 06.12.16.
 */

@Singleton
@Component(modules = {NetworkModule.class, RepositoryModule.class, PresenterModule.class})
public interface AppComponent {
    IAuthRepository getAuthRepository();

    MainPresenter getMainPresenter();

    SinglePostPresenter getPostPresenter();
}
