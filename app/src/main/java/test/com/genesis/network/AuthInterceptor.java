package test.com.genesis.network;

import com.squareup.okhttp.HttpUrl;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

import test.com.genesis.repository.IAuthRepository;

/**
 * Created by dima on 07.12.16.
 */
public class AuthInterceptor implements com.squareup.okhttp.Interceptor {
    public AuthInterceptor(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    IAuthRepository authRepository;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (authRepository.isLoggedIn()) {
            HttpUrl url = request.httpUrl().newBuilder().addQueryParameter("access_token",
                    authRepository.getAuthToken()).build();
            request = request.newBuilder().url(url).build();
        }
        return chain.proceed(request);
    }
}
