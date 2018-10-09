package site.pushy.weather.citymanage;

import com.google.gson.Gson;

import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.weather.BaseWeather;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.uitls.StorageUtil;
import site.pushy.weather.weatherinfo.WeatherInfoModel;

public class CityManagePresenter implements CityManageContract.Presenter {

    private CityManageContract.View view;
    private WeatherInfoModel model;

    public CityManagePresenter(CityManageContract.View view, WeatherInfoModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {

    }

    @Override
    public void getMyAreaWeatherInfo() {
        List<MyArea> myAreas = LitePal.findAll(MyArea.class);

        List<Weather> weatherList = new LinkedList<>();
        for (MyArea myArea : myAreas) {
            String weatherId = myArea.getWeatherId();
            /* 首先先从SharedPreferences中获取数据 */
            Weather weather = StorageUtil.getWeather(weatherId);
            if (weather != null) {
                System.out.println("*** SharedPreferences不为空 ***");
                weatherList.add(weather);
                continue;
            }

            /* 如果本地没有缓存，则从网络获取数据 */
            model.getWeather(weatherId)
                    .subscribe(new Observer<BaseWeather>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            view.setLoading(true);
                        }

                        @Override
                        public void onNext(BaseWeather baseWeather) {
                            Weather weather = baseWeather.weathers.get(0);
                            StorageUtil.saveWeather(weather.basic.weatherId, weather);

                            weatherList.add(weather);
                            view.setMyAreaWeatherInfo(weatherList);
                            view.setLoading(false);
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

        view.setMyAreaWeatherInfo(weatherList);
        view.setLoading(false);
    }
}
