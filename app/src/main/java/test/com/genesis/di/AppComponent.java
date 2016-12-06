package test.com.genesis.di;

import javax.inject.Singleton;

import dagger.Component;
import test.com.genesis.network.FbApi;
import test.com.genesis.network.FbModel;
import test.com.genesis.repository.IAuthRepository;

/**
 * Created by dima on 06.12.16.
 */

@Singleton
@Component(modules={NetworkModule.class, RepositoryModule.class})
public interface AppComponent {
    FbApi getFbApi();
    IAuthRepository getAuthRepository();
    FbModel getFbModel();
}
