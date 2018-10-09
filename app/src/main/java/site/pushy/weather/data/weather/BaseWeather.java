package site.pushy.weather.data.weather;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BaseWeather {

    @SerializedName("HeWeather")
    public List<Weather> weathers;

    @Override
    public String toString() {
        return "BaseWeather{" +
                "weathers=" + weathers +
                '}';
    }
}
