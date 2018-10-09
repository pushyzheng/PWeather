package site.pushy.weather.weatherinfo;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.pushy.weather.R;
import site.pushy.weather.data.weather.Forecast;
import site.pushy.weather.data.weather.Weather;

public class WeatherInfoActivity extends AppCompatActivity implements WeatherInfoContract.View, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.iv_background) ImageView ivBackground;
    @BindView(R.id.tv_temp) TextView tvTemp;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.tv_info) TextView tvInfo;
    @BindView(R.id.tv_air_aqi) TextView tvAirAqi;
    @BindView(R.id.tv_wind_dir) TextView tvWindDir;
    @BindView(R.id.layout_forecast) LinearLayout forecastLayout;
    @BindView(R.id.swipe_refresh_main) SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            );
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initWidget();

        WeatherInfoPresenter presenter = new WeatherInfoPresenter(this, new WeatherInfoModel());
        presenter.getWeatherInfo("CN101230909");
    }

    @Override
    public void setPresenter(WeatherInfoContract.Presenter presenter) {

    }

    private void initWidget() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void setWeather(Weather weather) {
        Glide.with(this)
                .load("http://cn.bing.com/az/hprichbg/rb/MarshallPoint_ZH-CN9062933060_1920x1080.jpg")
                .into(ivBackground);

        tvTemp.setText(weather.now.temperature);
        tvCity.setText(weather.basic.cityName);
        tvInfo.setText(weather.now.more.info);

        String apiText = String.format("空气%s %s", weather.aqi.city.qlty, weather.aqi.city.aqi);
        tvAirAqi.setText(apiText);
        tvWindDir.setText(weather.now.windDir);

        /* 设置未来天气播报部分数据 */
        forecastLayout.removeAllViews();
        for (Forecast forecast : weather.forecastList) {
            View view = LayoutInflater.from(this)
                    .inflate(R.layout.main_forecast_item, forecastLayout, false);
            TextView dateText = view.findViewById(R.id.tv_forecast_date);
            TextView infoText = view.findViewById(R.id.tv_forecast_info);
            TextView maxText = view.findViewById(R.id.tv_forecast_max);
            TextView minText = view.findViewById(R.id.tv_forecast_min);
            dateText.setText(forecast.date);
            infoText.setText(" · " + forecast.more.info);
            maxText.setText(forecast.temperature.max);
            minText.setText(String.format("/%s℃", forecast.temperature.min));
            forecastLayout.addView(view);
        }
    }

    @Override
    public void onRefresh() {
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this, "更新天气数据成功", Toast.LENGTH_SHORT).show();
    }
}
