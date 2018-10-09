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
        model.getWeather("CN101230909")
                .subscribe(new Observer<BaseWeather>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BaseWeather baseWeather) {
                        System.out.println("baseweather => " + baseWeather);
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
