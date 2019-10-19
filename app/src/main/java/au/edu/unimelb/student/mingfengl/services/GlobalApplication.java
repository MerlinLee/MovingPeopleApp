package au.edu.unimelb.student.mingfengl.services;

import android.app.Application;
import android.content.Context;

public class GlobalApplication extends Application {
//    private static String url = "http://45.113.234.163:5000";
    private static String url = "http://10.13.107.208:8080";
    private static Context mContext;

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
        mContext = getApplicationContext();
        application = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
