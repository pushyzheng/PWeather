package site.pushy.weather.data.db;

import org.litepal.crud.LitePalSupport;

public class MyArea extends LitePalSupport {

    private String name;

    private String weatherId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    @Override
    public String toString() {
        return "MyArea{" +
                ", name='" + name + '\'' +
                ", weatherId='" + weatherId + '\'' +
                '}';
    }
}
