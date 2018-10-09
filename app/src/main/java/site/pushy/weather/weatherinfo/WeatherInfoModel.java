package site.pushy.weather.weatherinfo;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import site.pushy.weather.BaseModel;
import site.pushy.weather.data.weather.BaseWeather;
import site.pushy.weather.http.RetrofitServiceManager;

public class WeatherInfoModel extends BaseModel {

    private static final String key = "d85f244d80fc48bb980c8b19176fb43b";

    private WeatherInfoService weatherInfoService;

    public WeatherInfoModel() {
        this.weatherInfoService = RetrofitServiceManager.getInstance().create(WeatherInfoService.class);
    }

    public Observable<BaseWeather> getWeather(String cityId) {
        return observe(weatherInfoService.getWeather(cityId, key));
    }

    interface WeatherInfoService {

        /**
         * 获取城市天气信息
         * @param cityid city id
         * @param key the application key
         */
        @GET("weather")
        Observable<BaseWeather> getWeather(@Query("cityid") String cityid, @Query("key") String key);

    }


}
