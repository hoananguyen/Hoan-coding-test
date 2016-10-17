package com.hoan.turnercodingtest;

import android.app.Application;

import com.hoan.turnercodingtest.utils.Logger;

/**
 * Created by Hoan on 10/15/2016.
 */

public class TurnerApplication extends Application {
    private static Application mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        Logger.e("TurnerApplication", "onCreate");
        mApplication = this;
    }

    public static Application getApplication() {
        Logger.e("TurnerApplication", "getApplication");
        return mApplication;
    }
}
