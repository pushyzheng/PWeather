package site.pushy.weather.data.weather;

import com.google.gson.annotations.SerializedName;

public class Forecast {

    public String data;

    @SerializedName("tmp")
    public Temperature temperature;

    @SerializedName("cond")
    public More more;

    private class Temperature {

        public String max;  // 最高温度

        public String min;  // 最低温度

    }

    private class More {

        @SerializedName("txt")
        public String info;
    }

}
