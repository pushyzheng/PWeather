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

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.ViewHolder> {

    private List<Weather> weatherList;

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_city_manage, viewGroup, false);
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
    }

    @Override
    public int getItemCount() {
        if (weatherList.isEmpty()) {
            return 0;
        }
        return weatherList.size();
    }

}
