package site.pushy.weather.weatherinfo;

import site.pushy.weather.base.BasePresenter;
import site.pushy.weather.base.BaseView;
import site.pushy.weather.data.weather.Weather;

public interface WeatherInfoContract {

    interface View extends BaseView<Presenter> {

        void setWeather(Weather weather);

    }

    interface Presenter extends BasePresenter {

        void getWeatherInfo(String cityId);

        void updateWeatherInfo(String cityId);

    }


}
