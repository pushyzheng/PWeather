package site.pushy.weather.citymanage;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import site.pushy.weather.R;
import site.pushy.weather.data.weather.Weather;
import site.pushy.weather.uitls.ToastUtil;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder>
        implements View.OnClickListener {

    private List<Weather> weatherList;
    private OnItemClickListener mOnItemClickListener;

    public CityListAdapter(List<Weather> weatherList) {
        this.weatherList = weatherList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView county;
        TextView city;
        TextView province;
        TextView temp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            county = itemView.findViewById(R.id.tv_city_manage_county);
            city = itemView.findViewById(R.id.tv_city_manage_city);
            province = itemView.findViewById(R.id.tv_city_manage_province);
            temp = itemView.findViewById(R.id.tv_city_manage_temp);
        }
    }

    /* 定义对外暴露的点击事件的接口 */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city_manage, viewGroup, false);
        view.setOnClickListener(this);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Weather weather = weatherList.get(i);
        viewHolder.county.setText(weather.basic.location);
        viewHolder.city.setText(weather.basic.city + "，");
        viewHolder.province.setText(weather.basic.province);
        viewHolder.temp.setText(weather.now.temperature);

        viewHolder.itemView.setTag(i);  // 为itemView设置tag
    }

    @Override
    public int getItemCount() {
        if (weatherList.isEmpty()) {
            return 0;
        }
        return weatherList.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }


}
