package test.com.genesis;

import android.app.Application;

import com.facebook.FacebookSdk;

import test.com.genesis.di.AppComponent;
import test.com.genesis.di.DaggerAppComponent;
import test.com.genesis.di.RepositoryModule;

/**
 * Created by dima on 06.12.16.
 */

public class App extends Application {
    public static App app;

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        FacebookSdk.sdkInitialize(getApplicationContext());
        appComponent = DaggerAppComponent.builder()
                .repositoryModule(new RepositoryModule(this))
                .build();
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
