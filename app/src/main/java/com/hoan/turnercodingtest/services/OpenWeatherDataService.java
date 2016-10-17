package com.hoan.turnercodingtest.services;

import com.hoan.turnercodingtest.Configuration;
import com.hoan.turnercodingtest.SingletonFactory;
import com.hoan.turnercodingtest.activities.main.WeatherModel;
import com.hoan.turnercodingtest.utils.JsonParser;
import com.hoan.turnercodingtest.utils.Logger;

import java.util.ArrayList;

/**
 * Created by Hoan on 10/15/2016.
 */

public class OpenWeatherDataService implements DataService, Singleton {
    private final NetworkService mfNetworkService;

    public OpenWeatherDataService(SingletonFactory.SingletonParam singletonParam) {
        mfNetworkService = (NetworkService) SingletonFactory.INSTANCE.getSingleton(NetworkService.class.getName(), this);
    }

    @Override
    public void shutdown(SingletonFactory.SingletonParam singletonParam) {
        SingletonFactory.INSTANCE.releaseSingleton(NetworkService.class.getName(), this);
    }

    @Override
    public void getWeather(String location, final FutureTaskListener<WeatherModel> listener) {
        String url = "http://api.openweathermap.org/data/2.5/weather?q=" + location
                + "&mode=json&units=imperial&appid=" + Configuration.getWeatherKey();

        Logger.e("OpenWeatherDataService", "getWeather url = " + url);

        mfNetworkService.getString(url, "OpenWeatherDataService", new FutureTaskListener<String>() {
            @Override
            public void onCompletion(String result) {
                Logger.e("OpenWeatherDataSercive", "result = " + result);
                listener.onCompletion(JsonParser.parseWeather(result));
            }

            @Override
            public void onError(String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onProgress(float progress) {

            }
        });
    }

    @Override
    public void getForecast(String location, final FutureTaskListener<ArrayList<WeatherModel>> listener) {
        String url = "http://api.openweathermap.org/data/2.5/forecast/daily?q=" + location
                + "&mode=json&units=imperial&cnt=7&appid=" + Configuration.getWeatherKey();
        Logger.e("OpenWeatherDataService", "getForecast url = " + url);
        mfNetworkService.getString(url, "OpenWeatherDataService", new FutureTaskListener<String>() {
            @Override
            public void onCompletion(String result) {
                ArrayList<WeatherModel> weatherModels = JsonParser.parseForecast(result);
                if (result == null) {
                    listener.onError("Json error");
                } else {
                    listener.onCompletion(weatherModels);
                }
            }

            @Override
            public void onError(String errorMessage) {
                listener.onError(errorMessage);
            }

            @Override
            public void onProgress(float progress) {

            }
        });
    }
}
