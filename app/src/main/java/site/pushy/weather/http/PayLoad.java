package site.pushy.weather.http;

import io.reactivex.functions.Function;

public class PayLoad<T> implements Function<BaseResponse<T>, T> {

    @Override
    public T apply(BaseResponse<T> resp) {
        if (!resp.isSuccess()) {
            throw new RuntimeException(resp.message);
        }
        return resp.data;
    }

}
