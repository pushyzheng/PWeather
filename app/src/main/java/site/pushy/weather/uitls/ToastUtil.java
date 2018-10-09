//package site.pushy.weather.uitls;
//
//import android.content.Context;
//import android.widget.Toast;
//
//import site.pushy.weather.PWeatherApplication;
//
//public class ToastUtil {
//
//    private static Toast toast;
//
//    public static void showToast(Context context, String content) {
//        if (toast == null) {
//            toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(content);
//        }
//        toast.show();
//    }
//
//    public static void showToast(String content) {
//        if (toast == null) {
//            toast = Toast.makeText(PWeatherApplication.getContext(), content, Toast.LENGTH_SHORT);
//        } else {
//            toast.setText(content);
//        }
//        toast.show();
//    }
//
//}
