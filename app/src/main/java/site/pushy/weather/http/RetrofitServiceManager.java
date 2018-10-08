package site.pushy.weather.http;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 统一生成接口实例的管理类
 */
public class RetrofitServiceManager {

    private static final String BASE_URL = "http://api.bbs.pushy.site";
    private static final int DEFAULT_TIME_OUT = 5;  //超时时间 5s
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private Retrofit mRetrofit;

    public RetrofitServiceManager() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_WRITE_TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_READ_TIME_OUT, TimeUnit.SECONDS)
                .build();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create()) // 支持Gson自动解析JSON
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // 支持RxJava
                .build();
    }

    private static class SingletonHolder{
        private static final RetrofitServiceManager INSTANCE = new RetrofitServiceManager();
    }

    public static RetrofitServiceManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T create(Class<T> service) {
        return mRetrofit.create(service);
    }

}
