package site.pushy.weather.selectarea;

import android.util.Log;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.data.City;
import site.pushy.weather.data.County;
import site.pushy.weather.data.Province;

public class SelectAreaPresenter implements SelectAreaContract.Presenter {
    
    private static final String TAG = "SelectAreaPresenter";
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
        List<Province> provinceList = LitePal.findAll(Province.class);
        if (provinceList.size() > 0) { // 数据库数据不为空
            Log.d(TAG, "Province 数据库数据不为空，从数据库获取数据");
            List<String> dataList = new ArrayList<>();
            for (Province province : provinceList) {
                dataList.add(province.getName());
            }
            view.setDataList(dataList);
            return;
        }
        /* 当数据数据为空时，从网络上获取数据 */
        model.listProvince()
                .subscribe(new Observer<List<Province>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setProgressDialog(true);
                    }

                    @Override
                    public void onNext(List<Province> provinces) {
                        System.out.println("province => " + provinces);
                        /* 保存数据到数据库 */
                        LitePal.saveAll(provinces);

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
        List<City> cityList = LitePal.findAll(City.class);
        if (cityList.size() > 0) {
            Log.d(TAG, "City 数据库数据不为空，从数据库获取数据");
            List<String> dataList = new ArrayList<>();
            for (City city : cityList) {
                dataList.add(city.getName());
            }
            view.setDataList(dataList);
            return;
        }
        /* 当数据数据为空时，从网络上获取数据 */
        model.listCity(id)
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<City> cities) {
                        System.out.println("Got cities => " + cities);

                        LitePal.saveAll(cities);

                        List<String> dataList = new ArrayList<>();
                        for (City city : cities) {
                            dataList.add(city.getName());
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
    public void getCounties(int provinceId, int cityId) {
        List<County> countyList = LitePal.findAll(County.class);
        if (countyList.size() > 0) {
            Log.d(TAG, "County 数据库数据不为空，从数据库获取数据");
            List<String> dataList = new ArrayList<>();
            for (County county : countyList) {
                dataList.add(county.getName());
            }
            view.setDataList(dataList);
            return;
        }

        model.listCounty(provinceId, cityId)
                .subscribe(new Observer<List<County>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setProgressDialog(true);
                    }

                    @Override
                    public void onNext(List<County> counties) {
                        System.out.println("Got counties => " + counties);

                        LitePal.saveAll(counties);

                        List<String> dataList = new ArrayList<>();
                        for (County county : counties) {
                            dataList.add(county.getName());
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


}
