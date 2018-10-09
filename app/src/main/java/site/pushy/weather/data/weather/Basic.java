package site.pushy.weather.data.weather;

import com.google.gson.annotations.SerializedName;

public class Basic {

    public String location;

    @SerializedName("id")
    public String weatherId;

    public Update update;

    @SerializedName("parent_city")
    public String city;

    @SerializedName("admin_area")
    public String province;

    public String lat;

    public String lon;

    public class Update {

        @SerializedName("loc")
        public String updateTime;

    }

}
