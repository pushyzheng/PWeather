package site.pushy.weather.uitls;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import site.pushy.weather.PWeatherApplication;
import site.pushy.weather.data.weather.Weather;

public class StorageUtil {

    public static String get(String id) {
        SharedPreferences prefs = PreferenceManager
                .getDefaultSharedPreferences(PWeatherApplication.getContext());
        return prefs.getString(id, null);
    }

    public static void save(String id, String value) {
        SharedPreferences.Editor editor =
                PreferenceManager.getDefaultSharedPreferences(PWeatherApplication.getContext()).edit();
        editor.putString(id, value);
        editor.apply();
    }

    public static Weather getWeather(String id) {
        String jsonString = get(id);
        if (jsonString == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Weather.class);
    }

    public static void saveWeather(String id, Weather weather) {
        save(id, new Gson().toJson(weather));
    }


}
