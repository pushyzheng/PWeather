package site.pushy.weather.selectarea;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import site.pushy.weather.BaseModel;
import site.pushy.weather.data.db.City;
import site.pushy.weather.data.db.County;
import site.pushy.weather.data.db.Province;
import site.pushy.weather.http.RetrofitServiceManager;

public class SelectAreaModel extends BaseModel {

    private SelectAreaService selectAreaService;

    public SelectAreaModel() {
        this.selectAreaService = RetrofitServiceManager.getInstance().create(SelectAreaService.class);
    }

    public Observable<List<Province>> listProvince() {
        return observe(selectAreaService.listProvince());
    }

    public Observable<List<City>> listCity(int id) {
        return observe(selectAreaService.listCity(id));
    }

    public Observable<List<County>> listCounty(int provinceId, int cityId) {
        return observe(selectAreaService.listCounty(provinceId, cityId));
    }

    interface SelectAreaService {

        /**
         * 获取中国省份和直辖市
         */
        @GET("china")
        Observable<List<Province>> listProvince();

        /**
         * 获取省份的所有城市
         * @param id province id
         */
        @GET("china/{id}")
        Observable<List<City>> listCity(@Path("id") int id);

        /**
         * 获取城市的所有区、县级市、县
         * @param provinceId province id
         * @param id city id
         */
        @GET("china/{provinceId}/{id}")
        Observable<List<County>> listCounty(@Path("provinceId") int provinceId, @Path("id") int id);

    }

}
