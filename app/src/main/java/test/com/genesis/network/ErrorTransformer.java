package test.com.genesis.network;

import java.io.IOException;

import retrofit.Result;
import rx.Observable;
import rx.functions.Func1;

/**
 * Created by dima on 07.12.16.
 */
class ErrorTransformer<T> implements Observable.Transformer<Result<T>, T> {

    @Override
    public Observable<T> call(Observable<Result<T>> resultObservable) {
        return resultObservable.flatMap(new Func1<Result<T>, Observable<T>>() {
            @Override
            public Observable<T> call(Result<T> result) {
                if (result.response().isSuccess()) {
                    return Observable.just(result.response().body());
                }
                //TODO fine-grained errors must be here
                return Observable.error(new IOException(result.error()));
            }
        });
    }
}
