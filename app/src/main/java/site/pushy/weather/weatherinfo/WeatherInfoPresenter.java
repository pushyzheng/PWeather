package site.pushy.weather.weatherinfo;

import android.widget.Toast;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.PWeatherApplication;
import site.pushy.weather.data.weather.BaseWeather;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.uitls.StorageUtil;
import site.pushy.weather.uitls.ToastUtil;

public class WeatherInfoPresenter implements WeatherInfoContract.Presenter {

    private WeatherInfoContract.View view;
    private WeatherInfoModel model;

    public WeatherInfoPresenter(WeatherInfoContract.View view, WeatherInfoModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void getWeatherInfo(String weatherId) {
        Weather weather = StorageUtil.getWeather(weatherId);
        if (weather != null) {
            System.out.println("getWeatherInfo 使用缓存数据");
            view.setWeather(weather);
            return;
        }
        /* 无缓存时从网络上获取数据 */
        getWeatherInfoFromServer(weatherId);
    }

    @Override
    public void updateWeatherInfo(String cityId) {
        getWeatherInfoFromServer(cityId);
    }

    private void getWeatherInfoFromServer(String weatherId) {
        model.getWeather(weatherId)
                .subscribe(new Observer<BaseWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseWeather baseWeather) {
                        Weather weather = baseWeather.weathers.get(0);
                        if (!baseWeather.weathers.isEmpty()) {
                            view.setWeather(weather);
                        }
                        /* 将获取到天气数据存入缓存 */
                        StorageUtil.saveWeather(weather.basic.weatherId, weather);
                    }

                    @Override
                    public void onError(Throwable e) {
                        ToastUtil.showToast("获取天气数据失败 " + e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
