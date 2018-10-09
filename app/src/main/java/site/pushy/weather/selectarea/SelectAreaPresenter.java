package site.pushy.weather.selectarea;

import android.util.Log;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import site.pushy.weather.data.db.City;
import site.pushy.weather.data.db.County;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.db.Province;

public class SelectAreaPresenter implements SelectAreaContract.Presenter {
    
    private static final String TAG = "SelectAreaPresenter";
    private SelectAreaContract.View view;
    private SelectAreaModel model;

    private List<Province> mProvinceList;
    private List<City> mCityList;
    private List<County> mCountyList;
    private Province selectedProvince;  // 当前选中的省份
    private City selectedCity;  // 当前选中的城市
    private County selectedCounty;  // 选中的地区

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
        if (provinceList.size() > 0) {  // 数据库数据不为空
            Log.d(TAG, "加载Province缓存数据 ...");
            mProvinceList = provinceList;
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
                        System.out.println("Got provinces from server => " + provinces);
                        mProvinceList = provinces;

                        List<String> dataList = new ArrayList<>();
                        for (Province province : provinces) {
                            /* 将id的值赋值给code，其是用来查询其附属城市的标识值 */
                            province.setCode(province.getId());
                            dataList.add(province.getName());
                        }
                        view.setDataList(dataList);
                        view.setProgressDialog(false);

                        /* 保存数据到数据库 */
                        LitePal.saveAll(provinces);
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

    /**
     * 查询某个省的所有的市区，优先从数据库查询，如果没有查询再到服务器上查询
     */
    @Override
    public void getCities(int position) {
        Province province = mProvinceList.get(position);
        this.selectedProvince = province;  // 设置当前选中的Province
        int provinceId = province.getId();

        /* 从数据中根据provinceId查找是否存储该省份归属的城市数据 */
        List<City> cityList = LitePal.where("provinceid = ?", String.valueOf(provinceId)).find(City.class);

        if (cityList.size() > 0) {
            Log.d(TAG, "加载city缓存数据 ...");
            mCityList = cityList;
            List<String> dataList = new ArrayList<>();
            for (City city : cityList) {
                dataList.add(city.getName());
            }
            view.setDataList(dataList);
            return;
        }
        /* 当数据数据为空时，从网络上获取数据 */
        model.listCity(provinceId)
                .subscribe(new Observer<List<City>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<City> cities) {
                        Log.d(TAG, "Got cities from server => " + cities);

                        mCityList = cities;
                        List<String> dataList = new ArrayList<>();
                        for (City city : cities) {
                            city.setProvinceId(provinceId);
                            city.setCode(city.getId());
                            dataList.add(city.getName());
                        }
                        view.setDataList(dataList);
                        view.setProgressDialog(false);

                        LitePal.saveAll(cities);
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



    /**
     * 查询某个市的所有的区县，优先从数据库查询，如果没有查询再到服务器上查询
     */
    @Override
    public void getCounties(int position) {
        City city = mCityList.get(position);
        this.selectedCity = city;  // 设置当前选中的City对象
        int cityId = city.getCode();

        /* 从数据中根据cityId查找是否存储该城市归属的区县的数据 */
        List<County> countyList = LitePal.where("cityid = ?", String.valueOf(cityId)).find(County.class);

        if (countyList.size() > 0) {
            Log.d(TAG, "加载County缓存数据 ...");
            mCountyList = countyList;
            List<String> dataList = new ArrayList<>();
            for (County county : countyList) {
                dataList.add(county.getName());
            }
            view.setDataList(dataList);
            return;
        }

        model.listCounty(selectedProvince.getId(), cityId)
                .subscribe(new Observer<List<County>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        view.setProgressDialog(true);
                    }

                    @Override
                    public void onNext(List<County> counties) {
                        System.out.println("Got counties from server => " + counties);

                        mCountyList = counties;
                        List<String> dataList = new ArrayList<>();
                        for (County county : counties) {
                            county.setCityId(cityId);
                            dataList.add(county.getName());
                        }
                        view.setDataList(dataList);
                        view.setProgressDialog(false);

                        LitePal.saveAll(counties);
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
    public void backToCityLevel() {
        List<City> cityList = LitePal.where("provinceid = ?", String.valueOf(selectedProvince.getCode())).find(City.class);
        List<String> dataList = new ArrayList<>();
        for (City city : cityList) {
            dataList.add(city.getName());
        }
        view.setDataList(dataList);
    }

    /**
     * 保存选中的地区信息
     * @return 选中的地区的天气代码
     */
    @Override
    public String saveMyArea(int position) {
        County county = mCountyList.get(position);
        /* 保存选择的地区到数据库 */
        MyArea myArea = new MyArea();
        myArea.setName(county.getName());
        myArea.setWeatherId(county.getWeatherId());
        myArea.save();

        return county.getWeatherId();
    }


}
