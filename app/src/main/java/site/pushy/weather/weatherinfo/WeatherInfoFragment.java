package site.pushy.weather.weatherinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.litepal.LitePal;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.pushy.weather.R;
import site.pushy.weather.citymanage.CityManageActivity;
import site.pushy.weather.data.WeatherType;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.weather.Forecast;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.uitls.ToastUtil;

public class WeatherInfoFragment extends Fragment implements WeatherInfoContract.View,
        SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = "WeatherInfoFragment";
    private String weatherId;  // 当前显示天气数据的地区的code
    private WeatherInfoPresenter presenter;

    @BindView(R.id.iv_background) ImageView ivBackground;
    @BindView(R.id.tv_temp) TextView tvTemp;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.tv_info) TextView tvInfo;
    @BindView(R.id.tv_air_aqi) TextView tvAirAqi;
    @BindView(R.id.tv_wind_dir) TextView tvWindDir;
    @BindView(R.id.layout_forecast) LinearLayout forecastLayout;
    @BindView(R.id.layout_suggestion) LinearLayout suggestionLayout;
    @BindView(R.id.swipe_refresh_main) SwipeRefreshLayout swipeRefresh;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather_info, container, false);
        ButterKnife.bind(this, view);
        initWidget();

        weatherId = getArguments().getString("weatherId");

        presenter = new WeatherInfoPresenter(this, new WeatherInfoModel());
        presenter.getWeatherInfo(weatherId);
        return view;
    }

    private void initWidget() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void setPresenter(WeatherInfoContract.Presenter presenter) {

    }

    @Override
    public void setWeather(Weather weather) {
        tvTemp.setText(weather.now.temperature);
        tvCity.setText(weather.basic.location);
        tvInfo.setText(weather.now.more.info);

        String apiText = String.format("空气%s %s", weather.aqi.city.qlty, weather.aqi.city.aqi);
        tvAirAqi.setText(apiText);
        tvWindDir.setText(weather.now.windDir);

        /* 设置未来天气播报部分数据 */
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(getActivity())
                    .inflate(R.layout.main_forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.tv_forecast_date);
            TextView infoText = view.findViewById(R.id.tv_forecast_info);
            ImageView weatherImg = view.findViewById(R.id.tv_forecast_ic);
            TextView maxText = view.findViewById(R.id.tv_forecast_max);
            TextView minText = view.findViewById(R.id.tv_forecast_min);
            dateText.setText(forecast.date);
            infoText.setText(" · " + forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(String.format("/%s℃", forecast.temperature.min));
            weatherImg.setImageResource(WeatherType.getWeatherICResource(forecast.more.info));
            forecastLayout.addView(view);
        }

        /* 设置洗车、舒适度、运动建议数据 */
        suggestionLayout.removeAllViews();
        setSuggestionItem(R.drawable.ic_comf, weather.suggestion.comfort.gist, weather.suggestion.comfort.info);
        setSuggestionItem(R.drawable.ic_wash_car, weather.suggestion.carWash.gist, weather.suggestion.carWash.info);
        setSuggestionItem(R.drawable.ic_sports, weather.suggestion.sport.gist, weather.suggestion.sport.info);
    }

    private void setSuggestionItem(int resId, String brf, String txt) {
        View view = LayoutInflater.from(getActivity())
                .inflate(R.layout.main_suggestion_item, suggestionLayout, false);
        ImageView icImg = view.findViewById(R.id.iv_suggestion_ic);
        TextView brfText = view.findViewById(R.id.tv_suggestion_brf);
        TextView txtText = view.findViewById(R.id.tv_suggestion_txt);
        icImg.setImageResource(resId);
        brfText.setText(brf);
        txtText.setText(txt);
        suggestionLayout.addView(view);
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        presenter.updateWeatherInfo(weatherId);
        ToastUtil.showToast("更新天气数据成功");
    }
}
