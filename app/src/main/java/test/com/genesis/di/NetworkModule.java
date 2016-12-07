package test.com.genesis.di;

import com.google.gson.GsonBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.util.concurrent.Executors;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import test.com.genesis.BuildConfig;
import test.com.genesis.network.AuthInterceptor;
import test.com.genesis.network.FbApi;
import test.com.genesis.network.FbModel;
import test.com.genesis.repository.IAuthRepository;

/**
 * Created by dima on 06.12.16.
 */

@Module
public class NetworkModule {

    public static final String FP_ENDPOINT = "https://graph.facebook.com";
    public static final String NETWORK = "network";
    public static final String UI = "ui";

    @Singleton
    @Provides
    public FbApi createFbApi(Retrofit retrofit) {
        return retrofit.create(FbApi.class);
    }

    @Provides
    public Retrofit createRetrofit(OkHttpClient client) {
        return new Retrofit.Builder()
                .baseUrl(FP_ENDPOINT)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                        .create()))
                .client(client)
                .build();
    }

    @Provides
    public OkHttpClient createClient(IAuthRepository authRepository) {
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new AuthInterceptor(authRepository));
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        client.interceptors().add(interceptor);
        return client;
    }

    @Provides
    @Singleton
    @Named(NETWORK)
    public Scheduler createNetworkScheduler() {
        return Schedulers.from(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
    }

    @Provides
    @Singleton
    @Named(UI)
    public Scheduler createUiScheduler() {
        return AndroidSchedulers.mainThread();
    }

    @Provides
    @Singleton
    public FbModel createFbModel(FbApi fbApi, @Named(NETWORK) Scheduler workScheduler, @Named(UI) Scheduler uiScheduler) {
        return new FbModel(fbApi, workScheduler, uiScheduler);
    }
}
