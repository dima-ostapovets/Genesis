package test.com.genesis.di;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import test.com.genesis.repository.AuthRepository;
import test.com.genesis.repository.IAuthRepository;

/**
 * Created by dima on 07.12.16.
 */

@Module
public class RepositoryModule {
    private Context context;

    public RepositoryModule(Context context) {
        this.context = context;
    }

    @Singleton
    @Provides
    public IAuthRepository createAuthRepository() {
        return new AuthRepository(context);
    }
}
