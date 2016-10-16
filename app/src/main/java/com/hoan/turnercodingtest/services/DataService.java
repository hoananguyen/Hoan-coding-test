package com.hoan.turnercodingtest.services;

import com.hoan.turnercodingtest.activities.main.WeatherModel;

import java.util.ArrayList;

/**
 * Created by Hoan on 10/15/2016.
 */

public interface DataService {
    void getWeather(String location, FutureTaskListener<WeatherModel> listener);
    void getForecast(String location, FutureTaskListener<ArrayList<WeatherModel>> listener);
}
