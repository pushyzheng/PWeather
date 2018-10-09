package site.pushy.weather.selectarea;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import site.pushy.weather.R;
import site.pushy.weather.data.db.City;
import site.pushy.weather.data.db.MyArea;
import site.pushy.weather.data.db.Province;
import site.pushy.weather.weatherinfo.WeatherInfoActivity;

public class SelectAreaActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, SelectAreaContract.View  {

    private static final int LEVEL_PROVINCE = 0;
    private static final int LEVEL_CITY = 1;
    private static final int LEVEL_COUNTY = 2;

    private Province selectedProvince;  // 当前选中的省份
    private City selectedCity;  // 当前选中的城市
    private int currentLevel;  // 当前选中的级别
    private List<String> dataList = new ArrayList<>();

    private SelectAreaPresenter presenter;

    @BindView(R.id.lv_area) ListView lvArea;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_area);
        ButterKnife.bind(this);
        initWidget();

        LitePal.getDatabase();

        presenter = new SelectAreaPresenter(this, new SelectAreaModel());
        //presenter.start();
        presenter.getProvinces();
    }

    @Override
    public void setPresenter(SelectAreaContract.Presenter presenter) {

    }

    private void initWidget() {
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dataList);
        lvArea.setAdapter(adapter);
        lvArea.setOnItemClickListener(this);
        currentLevel = LEVEL_PROVINCE;
    }

    @Override
    public void onBackPressed() {
        if (currentLevel == LEVEL_COUNTY) {
            presenter.getCities(18);
            currentLevel = LEVEL_CITY;
        } else if (currentLevel == LEVEL_CITY) {
            presenter.getProvinces();
            currentLevel = LEVEL_PROVINCE;
        } else {
            finish();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (currentLevel == LEVEL_PROVINCE) {
            //getSupportActionBar().setTitle(dataList.get(position));
            presenter.getCities(18);
            currentLevel = LEVEL_CITY;
        } else if (currentLevel == LEVEL_CITY) {
            //getSupportActionBar().setTitle(dataList.get(position));
            presenter.getCounties(18, 146);
            currentLevel = LEVEL_COUNTY;
        } else if (currentLevel == LEVEL_COUNTY) {
            Toast.makeText(this, "选择地区成功", Toast.LENGTH_SHORT).show();

            /* 保存选择的地区到数据库 */
            MyArea myArea = new MyArea();
            myArea.setName("政和");
            myArea.setWeatherId("CN101230909");
            myArea.save();

            Intent intent = new Intent(this, WeatherInfoActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void setDataList(List<String> data) {
        dataList.clear();
        dataList.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setProgressDialog(boolean v) {
        if (progressDialog == null) {  // 初始化progressDialog
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("正在加载 ...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        if (v) {
            progressDialog.show();
        } else {
            progressDialog.hide();
        }
    }

}
