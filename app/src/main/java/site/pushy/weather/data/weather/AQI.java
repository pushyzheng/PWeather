package site.pushy.weather.data.weather;

public class AQI {

    public AQICity city;

    public class AQICity {

        public String aqi;

        public String pm25;  // 空气pm2.5指数

        public String qlty;  // 空气质量

    }
}
