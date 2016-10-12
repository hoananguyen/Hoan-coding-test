package com.hoan.turnercodingtest.activities.main;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hoan.turnercodingtest.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class WeatherDetailFragment extends Fragment {


    public WeatherDetailFragment() {
        // Required empty public constructor
    }

    public static WeatherDetailFragment newInstance(WeatherModel weatherModel) {
        WeatherDetailFragment weatherFragment = new WeatherDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable("weather", weatherModel);
        weatherFragment.setArguments(args);
        return weatherFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather_detail, container, false);
        TextView dayOfTheWeekTextView = (TextView) v.findViewById(R.id.text_view_detail_day_of_the_week);
        TextView dateTextView = (TextView) v.findViewById(R.id.text_view_detail_date);
        TextView maxTemperatureTextView = (TextView) v.findViewById(R.id.text_view_detail_max_temp);
        TextView minTemperatureTextView = (TextView) v.findViewById(R.id.text_view_detail_min_temp);
        TextView mainTextView = (TextView) v.findViewById(R.id.text_view_detail_main);
        TextView humidityTextView = (TextView) v.findViewById(R.id.text_view_humidity);
        TextView pressureTextView = (TextView) v.findViewById(R.id.text_view_pressure);
        TextView windTextView = (TextView) v.findViewById(R.id.text_view_wind);
        ImageView iconImageView = (ImageView) v.findViewById(R.id.image_view_detail_icon);

        Bundle args = getArguments();
        if (args != null) {
            WeatherModel weatherModel = args.getParcelable("weather");
            dateTextView.setText(weatherModel.date);
            dayOfTheWeekTextView.setText(weatherModel.dayOfTheWeek);
            maxTemperatureTextView.setText(weatherModel.maxTemperature + "\u00B0");
            minTemperatureTextView.setText(weatherModel.minTemperature + "\u00B0");
            mainTextView.setText(weatherModel.main);
            humidityTextView.append(String.valueOf(weatherModel.humidity) + "%");
            pressureTextView.append(String.valueOf(weatherModel.pressure) + " hPa");
            windTextView.append(String.valueOf(weatherModel.windSpeed) + " km/h ");
            iconImageView.setImageResource(weatherModel.getArtResId());
        }

        return v;
    }

}
