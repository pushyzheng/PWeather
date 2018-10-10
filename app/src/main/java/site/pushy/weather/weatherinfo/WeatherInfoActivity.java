package site.pushy.weather.weatherinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.litepal.LitePal;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.pushy.weather.R;
import site.pushy.weather.citymanage.CityManageActivity;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.weather.Forecast;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.selectarea.SelectAreaActivity;

public class WeatherInfoActivity extends AppCompatActivity implements WeatherInfoContract.View,
        SwipeRefreshLayout.OnRefreshListener, View.OnClickListener {

    private static final String TAG = "WeatherInfoActivity";
    private String weatherId;  // 当前显示天气数据的地区的code
    private WeatherInfoPresenter presenter;

    @BindView(R.id.iv_background) ImageView ivBackground;
    @BindView(R.id.tv_temp) TextView tvTemp;
    @BindView(R.id.tv_city) TextView tvCity;
    @BindView(R.id.tv_info) TextView tvInfo;
    @BindView(R.id.tv_air_aqi) TextView tvAirAqi;
    @BindView(R.id.tv_wind_dir) TextView tvWindDir;
    @BindView(R.id.layout_forecast) LinearLayout forecastLayout;
    @BindView(R.id.swipe_refresh_main) SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.iv_main_add_city) ImageView ivAddCity;

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

        presenter = new WeatherInfoPresenter(this, new WeatherInfoModel());
        weatherId = getIntent().getStringExtra("weatherId");

        if (weatherId == null) {
            /* main启动，从缓存中获取添加过的城市地区 */
            List<MyArea> myAreas = LitePal.findAll(MyArea.class);
            if (myAreas.isEmpty()) {  // 未添加过城市
                startActivity(new Intent(this, SelectAreaActivity.class));
                finish();
            } else {
                /* 获取并显示缓存中添加的第一个Area的天气数据 */
                MyArea myArea = myAreas.get(0);
                presenter.getWeatherInfo(myArea.getWeatherId());
            }
            return;
        }
        /* 从其他Activity跳转，直接获取传入的weatherId的天气数据 */
        presenter.getWeatherInfo(weatherId);
    }

    @Override
    public void setPresenter(WeatherInfoContract.Presenter presenter) {

    }

    private void initWidget() {
        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(this);
        ivAddCity.setOnClickListener(this);
    }

    @Override
    public void setWeather(Weather weather) {
//        Glide.with(this)
//                .load("http://cn.bing.com/az/hprichbg/rb/MarshallPoint_ZH-CN9062933060_1920x1080.jpg")
//                .into(ivBackground);

        tvTemp.setText(weather.now.temperature);
        tvCity.setText(weather.basic.location);
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
        presenter.updateWeatherInfo(weatherId);
        Toast.makeText(this, "更新天气数据成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_main_add_city:
                Intent intent = new Intent(this, CityManageActivity.class);
                startActivity(intent);
                break;
        }
    }
}
