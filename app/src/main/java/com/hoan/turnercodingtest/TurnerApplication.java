package com.hoan.turnercodingtest;

import android.app.Application;

/**
 * Created by Hoan on 10/15/2016.
 */

public class TurnerApplication extends Application {
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;
    }

    public static Application getApplication() {
        return mApplication;
    }
}
