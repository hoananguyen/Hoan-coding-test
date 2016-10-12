package com.hoan.turnercodingtest.utils;

import android.content.Context;

import com.hoan.turnercodingtest.activities.main.WeatherModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Hoan on 10/10/2016.
 */

public class JsonParser {

    public static WeatherModel parseWeather(Context context, String jsonObjectString) {
        JSONObject jsonObject = JsonHelper.createJSONObject(jsonObjectString);
        return jsonObject == null ? null : parseWeather(context, jsonObject);
    }

    public static WeatherModel parseWeather(Context context, JSONObject jsonObject) {
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.dt = JsonHelper.getLong(jsonObject, "dt");
        JSONArray weatherArray = JsonHelper.getJSONArray(jsonObject, "weather");
        weatherModel.main = JsonHelper.getString(weatherArray, 0, "main");
        weatherModel.icon = JsonHelper.getString(weatherArray, 0, "icon");
        if (weatherModel.icon != null) {
            weatherModel.icon = weatherModel.icon.substring(0, weatherModel.icon.length() - 1);
        }
        JSONObject mainObject = JsonHelper.getJSONObject(jsonObject, "main");
        weatherModel.maxTemperature = (int) Math.round(JsonHelper.getDouble(mainObject, "temp_max"));
        weatherModel.minTemperature = (int) Math.round(JsonHelper.getDouble(mainObject, "temp_min"));
        weatherModel.pressure = (int) Math.round(JsonHelper.getDouble(mainObject, "pressure"));
        weatherModel.humidity = (int) Math.round(JsonHelper.getDouble(mainObject, "humidity"));
        JSONObject windObject = JsonHelper.getJSONObject(jsonObject, "wind");
        weatherModel.windSpeed = (float) JsonHelper.getDouble(windObject, "speed");
        weatherModel.degree = (float) JsonHelper.getDouble(windObject, "deg");
        weatherModel.dayOfTheWeek = weatherModel.getDayOfTheWeek(context, 0);
        weatherModel.date = weatherModel.getDate(context, 0);
        return weatherModel;
    }

    public static ArrayList<WeatherModel> parseForecast(Context context, String jsonObjectString) {
        ArrayList<WeatherModel> weatherModels = new ArrayList<>();
        JSONArray weatherJsonArray = JsonHelper.getJSONArray(JsonHelper.createJSONObject(jsonObjectString), "list");
        int length = weatherJsonArray == null ? 0 : weatherJsonArray.length();
        Logger.e("JsonParser", JsonHelper.toString(weatherJsonArray, true));
        for (int i = 0; i < length; i++) {
            WeatherModel weatherModel = new WeatherModel();
            JSONObject jsonObject = weatherJsonArray.optJSONObject(i);
            weatherModel.dt = JsonHelper.getLong(jsonObject, "dt");
            weatherModel.pressure = (int) Math.round(JsonHelper.getDouble(jsonObject, "pressure"));
            weatherModel.humidity = (int) Math.round(JsonHelper.getDouble(jsonObject, "humidity"));
            weatherModel.windSpeed = (float) JsonHelper.getDouble(jsonObject, "speed");
            weatherModel.degree = (float) JsonHelper.getDouble(jsonObject, "deg");
            JSONObject tempObject = JsonHelper.getJSONObject(jsonObject, "temp");
            weatherModel.maxTemperature = (int) Math.round(JsonHelper.getDouble(tempObject, "max"));
            weatherModel.minTemperature = (int) Math.round(JsonHelper.getDouble(tempObject, "min"));
            JSONArray weatherArray = JsonHelper.getJSONArray(jsonObject, "weather");
            weatherModel.main = JsonHelper.getString(weatherArray, 0, "main");
            weatherModel.icon = JsonHelper.getString(weatherArray, 0, "icon");
            if (weatherModel.icon != null) {
                weatherModel.icon = weatherModel.icon.substring(0, weatherModel.icon.length() - 1);
            }
            weatherModels.add(weatherModel);
            weatherModel.dayOfTheWeek = weatherModel.getDayOfTheWeek(context, i + 1);
            weatherModel.date = weatherModel.getDate(context, i + 1);
        }
        return weatherModels;
    }
}
