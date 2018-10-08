package site.pushy.weather.selectarea;

import java.util.List;

import site.pushy.weather.BasePresenter;
import site.pushy.weather.BaseView;
import site.pushy.weather.weatherinfo.WeatherInfoContract;

public class SelectAreaContract {

    interface View extends BaseView<WeatherInfoContract.Presenter> {

        void setDataList(List<String> data);

        void setProgressDialog(boolean v);

    }

    interface Presenter extends BasePresenter {


        void getProvinces();

        void getCities(int id);

        void getCounties(int provinceId, int cityId);

    }

}
