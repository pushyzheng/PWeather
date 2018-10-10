package site.pushy.weather.weatherinfo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.pushy.weather.R;
import site.pushy.weather.citymanage.CityManageActivity;
import site.pushy.weather.data.WeatherType;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.weather.Forecast;
import site.pushy.weather.data.weather.Suggestion;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.selectarea.SelectAreaActivity;

public class WeatherInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Fragment> fragmentList;

    @BindView(R.id.iv_main_add_city) ImageView ivAddCity;
    @BindView(R.id.viewpager_main) ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /* 设置沉浸式状态栏 */
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

        List<MyArea> myAreas = LitePal.findAll(MyArea.class);  // 从数据库中获取到添加过的所有城市地区
        if (myAreas.isEmpty()) {  // 未添加过城市
            startActivity(new Intent(this, SelectAreaActivity.class));
            finish();

        } else {
            /* 初始化各个fragment和ViewPager */
            for (MyArea myArea : myAreas) {
                WeatherInfoFragment fragment = new WeatherInfoFragment();
                Bundle bundle = new Bundle();  // 传给fragment参数值介质
                bundle.putString("weatherId", myArea.getWeatherId());
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
        }

        WeatherInfoPagerAdapter adapter = new WeatherInfoPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(adapter);
    }

    private void initWidget() {
        fragmentList = new LinkedList<>();
        ivAddCity.setOnClickListener(this);
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
