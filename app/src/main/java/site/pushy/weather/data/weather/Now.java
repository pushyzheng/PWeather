package site.pushy.weather.data.weather;

import com.google.gson.annotations.SerializedName;

public class Now {

    @SerializedName("tmp")
    public String temperature;  // 温度

    @SerializedName("wind_dir")
    public String windDir;  // 风向

    @SerializedName("wind_sc")
    public String windSc; // 风力

    @SerializedName("hum")
    public String humidity;  // 湿度

    @SerializedName("cond")
    public More more;  // 云状态

    public class More {

        @SerializedName("txt")
        public String info;

    }

    @Override
    public String toString() {
        return "Now{" +
                "temperature='" + temperature + '\'' +
                ", windDir='" + windDir + '\'' +
                ", more=" + more +
                '}';
    }
}
