package site.pushy.weather;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityController {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    /**
     * 结束所有的Activity
     */
    public static void finishAll() {
        for (Activity activity : activities) {
            activity.finish();
        }
        activities.clear();
    }

    /**
     * 结束所有的Activity，但是保留传入的activity
     * @param activity
     */
    public static void finishAll(Activity activity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i) == activity) {
                continue;
            }
            activities.get(i).finish();
            activities.remove(activity);
        }
    }

}
