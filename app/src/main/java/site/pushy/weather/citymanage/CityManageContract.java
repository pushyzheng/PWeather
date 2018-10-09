package site.pushy.weather.citymanage;

import java.util.List;

import site.pushy.weather.BasePresenter;
import site.pushy.weather.BaseView;
import site.pushy.weather.data.weather.Weather;

public class CityManageContract {

    interface View extends BaseView<CityManageContract.Presenter> {

        void setLoading(boolean v);

        void setMyAreaWeatherInfo(List<Weather> weathers);

    }

    interface Presenter extends BasePresenter {

        /**
         * 获取“我的城市管理”的城市的天气信息
         */
        void getMyAreaWeatherInfo();

    }

}
