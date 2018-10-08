package site.pushy.weather.data;

import org.litepal.crud.LitePalSupport;

public class Province extends LitePalSupport {

    private String id;

    private String name;

    private int code;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Province{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code=" + code +
                '}';
    }
}
