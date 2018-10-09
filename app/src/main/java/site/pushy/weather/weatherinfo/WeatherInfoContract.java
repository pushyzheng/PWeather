package site.pushy.weather.weatherinfo;

import site.pushy.weather.BasePresenter;
import site.pushy.weather.BaseView;
import site.pushy.weather.data.weather.Weather;

public interface WeatherInfoContract {

    interface View extends BaseView<Presenter> {

        void setWeather(Weather weather);

    }

    interface Presenter extends BasePresenter {


        void getWeatherInfo(String cityId);

    }


}
