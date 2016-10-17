package com.hoan.turnercodingtest.utils;

import android.util.Log;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Hoan on 9/19/2016.
 */

public final class Logger {
    private Logger() {}

    private static final List<String> CLASS_SIMPLE_NAMES = Arrays.asList(
            //"DateHelper",
            "ForecastTask",
            //"JsonParser",
            "MainActivity",
            "OpenWeatherDataService",
            "TurnerApplication",
            "VolleyNetworkService",
            //"WeatherAdapter",
            //"WeatherDetailFragment",
            //"WeatherFragment",
            "WeatherTask",
            "Dummy"
    );

    public static void d(String tag, String msg) {
        if (CLASS_SIMPLE_NAMES.contains(tag)) {
            Log.d(tag, msg);
        }
    }

    public static synchronized void e(String tag, String msg) {
        if (CLASS_SIMPLE_NAMES.contains(tag)) {
            Log.e(tag, msg);
        }
    }
}
