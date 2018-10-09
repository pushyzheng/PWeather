package site.pushy.weather.weatherinfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.data.weather.BaseWeather;

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
    public void getWeatherInfo(String cityId) {
        model.getWeather(cityId)
                .subscribe(new Observer<BaseWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseWeather baseWeather) {
                        System.out.println("baseweather => " + baseWeather);
                        if (!baseWeather.weathers.isEmpty()) {
                            view.setWeather(baseWeather.weathers.get(0));
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
