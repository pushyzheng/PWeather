package site.pushy.weather.data.weather;

import com.google.gson.annotations.SerializedName;

public class Suggestion {

    @SerializedName("comf")
    public Comfort comfort;  // 天气适宜

    @SerializedName("cw")
    public CarWash carWash;  // 洗车

    public Sport sport;  // 运动

    public class Comfort {

        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String gist;
    }

    public class CarWash {

        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String gist;

    }

    public class Sport {

        @SerializedName("txt")
        public String info;

        @SerializedName("brf")
        public String gist;

    }
}
