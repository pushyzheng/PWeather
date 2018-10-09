package site.pushy.weather.weatherinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import site.pushy.weather.R;

public class WeatherInfoActivity extends AppCompatActivity implements WeatherInfoContract.View {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WeatherInfoPresenter presenter = new WeatherInfoPresenter(this, new WeatherInfoModel());
        presenter.start();
    }

    @Override
    public void setPresenter(WeatherInfoContract.Presenter presenter) {

    }
}
