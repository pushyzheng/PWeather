package site.pushy.weather.selectarea;

import java.util.List;

import site.pushy.weather.base.BasePresenter;
import site.pushy.weather.base.BaseView;

public class SelectAreaContract {

    interface View extends BaseView<SelectAreaContract.Presenter> {

        void setDataList(List<String> data);

        void setProgressDialog(boolean v);

    }

    interface Presenter extends BasePresenter {

        void getProvinces();

        void getCities(int position);

        void getCounties(int position);

        void backToCityLevel();

        String saveMyArea(int position);

    }

}
