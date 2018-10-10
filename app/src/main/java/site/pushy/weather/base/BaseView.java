package site.pushy.weather.base;

public interface BaseView<T> {

    void setPresenter(T presenter);  // 得到presenter引用，通过setter进行注入presenter实例

}
