package site.pushy.weather.data;

import site.pushy.weather.R;

public class WeatherType {

    public static final String MIDDLE_RAIL = "中雨";
    public static final String SUNNY = "晴";
    public static final String CLOUDY = "阴";
    public static final String PARTLY_CLOUDY = "多云";

    public static int getWeatherICResource(String type) {
        int resource;
        switch (type) {
            case MIDDLE_RAIL:
                resource = R.drawable.ic_rain;
                break;
            case SUNNY:
                resource = R.drawable.ic_sunny;
                break;
            case CLOUDY:
                resource = R.drawable.ic_cloudy;
                break;
            case PARTLY_CLOUDY:
                resource = R.drawable.ic_partly_cloudy;
                break;
            default:
                resource = R.drawable.ic_sunny;
                break;
        }
        return resource;
    }

}
