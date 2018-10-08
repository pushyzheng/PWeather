package site.pushy.weather.selectarea;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.data.City;
import site.pushy.weather.data.County;
import site.pushy.weather.data.Province;

public class SelectAreaPresenter implements SelectAreaContract.Presenter {

    private SelectAreaContract.View view;
    private SelectAreaModel model;

    public SelectAreaPresenter(SelectAreaContract.View view, SelectAreaModel model) {
        this.view = view;
        this.model = model;
    }

    @Override
    public void start() {

    }

    /**
     * 查询所有的省，优先从数据库查询，如果没有查询再到服务器上查询
     */
    @Override
    public void getProvinces() {
        model.listProvince()
                .subscribe(new Observer<List<Province>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setProgressDialog(true);
                    }

                    @Override
                    public void onNext(List<Province> provinces) {
                        System.out.println("province => " + provinces);
                        List<String> dataList = new ArrayList<>();
                        for (Province province : provinces) {
                            dataList.add(province.getName());
                        }
                        view.setDataList(dataList);
                        view.setProgressDialog(false);
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

    @Override
    public void getCities(int id) {
        model.listCity(id)
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<City> cities) {
                        System.out.println("Got cities => " + cities);

                        List<String> dataList = new ArrayList<>();
                        for (City city : cities) {
                            dataList.add(city.getName());
                        }
                        view.setDataList(dataList);
                        view.setProgressDialog(false);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getCounties(int provinceId, int cityId) {
        model.listCounty(provinceId, cityId)
                .subscribe(new Observer<List<County>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<County> counties) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
