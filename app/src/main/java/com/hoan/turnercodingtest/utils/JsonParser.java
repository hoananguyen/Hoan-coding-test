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
        JSONObject jsonObject = JsonUtils.createJSONObject(jsonObjectString);
        return jsonObject == null ? null : parseWeather(context, jsonObject);
    }

    public static WeatherModel parseWeather(Context context, JSONObject jsonObject) {
        WeatherModel weatherModel = new WeatherModel();
        weatherModel.dt = JsonUtils.getLong(jsonObject, "dt");
        JSONArray weatherArray = JsonUtils.getJSONArray(jsonObject, "weather");
        weatherModel.main = JsonUtils.getString(weatherArray, 0, "main");
        weatherModel.icon = JsonUtils.getString(weatherArray, 0, "icon");
        if (weatherModel.icon != null) {
            weatherModel.icon = weatherModel.icon.substring(0, weatherModel.icon.length() - 1);
        }
        JSONObject mainObject = JsonUtils.getJSONObject(jsonObject, "main");
        weatherModel.maxTemperature = (int) Math.round(JsonUtils.getDouble(mainObject, "temp_max"));
        weatherModel.minTemperature = (int) Math.round(JsonUtils.getDouble(mainObject, "temp_min"));
        weatherModel.pressure = (int) Math.round(JsonUtils.getDouble(mainObject, "pressure"));
        weatherModel.humidity = (int) Math.round(JsonUtils.getDouble(mainObject, "humidity"));
        JSONObject windObject = JsonUtils.getJSONObject(jsonObject, "wind");
        weatherModel.windSpeed = (float) JsonUtils.getDouble(windObject, "speed");
        weatherModel.degree = (float) JsonUtils.getDouble(windObject, "deg");
        weatherModel.dayOfTheWeek = weatherModel.getDayOfTheWeek(context, 0);
        weatherModel.date = weatherModel.getDate(context, 0);
        return weatherModel;
    }

    public static ArrayList<WeatherModel> parseForecast(Context context, String jsonObjectString) {
        ArrayList<WeatherModel> weatherModels = new ArrayList<>();
        JSONArray weatherJsonArray = JsonUtils.getJSONArray(JsonUtils.createJSONObject(jsonObjectString), "list");
        int length = weatherJsonArray == null ? 0 : weatherJsonArray.length();
        Logger.e("JsonParser", JsonUtils.toString(weatherJsonArray, true));
        for (int i = 0; i < length; i++) {
            WeatherModel weatherModel = new WeatherModel();
            JSONObject jsonObject = weatherJsonArray.optJSONObject(i);
            weatherModel.dt = JsonUtils.getLong(jsonObject, "dt");
            weatherModel.pressure = (int) Math.round(JsonUtils.getDouble(jsonObject, "pressure"));
            weatherModel.humidity = (int) Math.round(JsonUtils.getDouble(jsonObject, "humidity"));
            weatherModel.windSpeed = (float) JsonUtils.getDouble(jsonObject, "speed");
            weatherModel.degree = (float) JsonUtils.getDouble(jsonObject, "deg");
            JSONObject tempObject = JsonUtils.getJSONObject(jsonObject, "temp");
            weatherModel.maxTemperature = (int) Math.round(JsonUtils.getDouble(tempObject, "max"));
            weatherModel.minTemperature = (int) Math.round(JsonUtils.getDouble(tempObject, "min"));
            JSONArray weatherArray = JsonUtils.getJSONArray(jsonObject, "weather");
            weatherModel.main = JsonUtils.getString(weatherArray, 0, "main");
            weatherModel.icon = JsonUtils.getString(weatherArray, 0, "icon");
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
