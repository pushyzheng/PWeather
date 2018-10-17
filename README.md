![ic_launcher.png](https://upload-images.jianshu.io/upload_images/7366843-8d09b2bd785be9b3.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

<h2 align="center">PWeather</h2>

一款像素级模仿MIUI10的天气App，采用主流MVP模式开发，封装Retrofit2  + RxJava + okHTTP3 网络请求。具有城市管理，生活建议等播报功能。

![2018-10-16_214702.png](https://upload-images.jianshu.io/upload_images/7366843-108a20db4f28782d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

## 特性

- 采用主流的MVP开发模式，Activity只负责处理UI逻辑，不再处理网络请求的逻辑，而交给Presenter来处理。
- 封装主流的网络请求框架OkHttp3 + Retrofit2  + RxJava。
- 城市选择、天气数据优先从缓存中读取，提升用户体验。

## 封装过程

通过RetrofitServiceManager统一管理Retrofit2请求Service代理类：

```java
public class RetrofitServiceManager {

    private static final String BASE_URL = "http://guolin.tech/api/";
    private static final int DEFAULT_TIME_OUT = 5;
    private static final int DEFAULT_READ_TIME_OUT = 10;
    private static final int DEFAULT_WRITE_TIME_OUT = 10;

    private Retrofit mRetrofit;

    public RetrofitServiceManager() {
        /* 初始化OkHttpClient对象 */
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
```

获取`service`代理类：

```java
weatherInfoService = RetrofitServiceManager.getInstance().create(WeatherInfoService.class);
```

调用请求：

```
public Observable<BaseWeather> getWeather(String weatherId) {
	return observe(weatherInfoService.getWeather(weatherId, key));
}
```

Presenter中处理请求：

```java
model.getWeather(weatherId)
    .subscribe(new Observer<BaseWeather>() {
        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(BaseWeather baseWeather) {
			/* 处理请求成功逻辑 */
        }

        @Override
        public void onError(Throwable e) {
			/* 处理请求失败逻辑 */
        }

        @Override
        public void onComplete() {

        }
    });
```

## 联系我

Email：1437876073@qq.com / pushy.zhengzuqin@gmail.com

## License

```
MIT License

Copyright (c) 2018 Pushy

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```

