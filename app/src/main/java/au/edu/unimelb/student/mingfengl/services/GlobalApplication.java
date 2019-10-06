package au.edu.unimelb.student.mingfengl.services;

import android.app.Application;

public class GlobalApplication extends Application {
    private static String url = "http://10.130.223.11:8080";

    private static String cookie;

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        GlobalApplication.cookie = cookie;
    }

    public String getUrl() {
        return url;
    }

    public static GlobalApplication getApplication() {
        return application;
    }

    private static GlobalApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }
}
