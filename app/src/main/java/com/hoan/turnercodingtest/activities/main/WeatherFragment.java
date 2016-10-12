package com.hoan.turnercodingtest.activities.main;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.hoan.turnercodingtest.R;
import com.hoan.turnercodingtest.utils.Logger;

import java.util.ArrayList;

/**
 *
 */
public class WeatherFragment extends ListFragment implements View.OnClickListener {

    public interface WeatherFragmentEventListener {
        void onItemClicked(WeatherModel weatherModel);
    }

    private WeatherFragmentEventListener mListener;

    public WeatherFragment() {
        // Required empty public constructor
    }

    public static WeatherFragment newInstance(WeatherModel weatherModel, ArrayList<WeatherModel> fiveDaysForecastList) {
        WeatherFragment weatherFragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putParcelable("weather", weatherModel);
        args.putParcelableArrayList("forecast", fiveDaysForecastList);
        weatherFragment.setArguments(args);
        return weatherFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weather, container, false);
        View headerView = v.findViewById(R.id.header_view);

        headerView.setOnClickListener(this);

        Bundle args = getArguments();
        if (args != null) {
            WeatherModel weatherModel = args.getParcelable("weather");
            setTodayView(headerView, weatherModel);
            setListAdapter(new WeatherAdapter());
        }
        return v;
    }

    private void setTodayView(View headerView, WeatherModel weatherModel) {
        Logger.e("WeatherFragment", "setTodayView\n" + weatherModel.toString());
        TextView dateTextView = (TextView) headerView.findViewById(R.id.text_view_header_date);
        TextView maxTempTextView = (TextView) headerView.findViewById(R.id.text_view_header_max_temp);
        TextView minTempTextView = (TextView) headerView.findViewById(R.id.text_view_header_min_temp);
        TextView mainTextView = (TextView) headerView.findViewById(R.id.text_view_header_main);
        ImageView iconImageView = (ImageView) headerView.findViewById(R.id.image_view_header_icon);

        dateTextView.setText(weatherModel.dayOfTheWeek + ", " + weatherModel.getDate(getActivity(), 0));
        maxTempTextView.setText(weatherModel.maxTemperature + "\u00B0");
        minTempTextView.setText(weatherModel.minTemperature + "\u00B0");
        mainTextView.setText(weatherModel.main);
        iconImageView.setImageResource(weatherModel.getArtResId());
    }

    @Override
    public void onClick(View v) {
        Logger.e("WeatherFragment", "onClick mListener is null " + (mListener == null));
        mListener.onItemClicked((WeatherModel) getArguments().getParcelable("weather"));
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        mListener.onItemClicked((WeatherModel) getArguments().getParcelableArrayList("forecast").get(position));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Logger.e("WeatherFragment", "onAttach");
        mListener = (WeatherFragmentEventListener) context;
    }

    private class WeatherAdapter extends BaseAdapter {

        public WeatherAdapter(){
        }

        @Override
        public int getCount() {
            Logger.e("WeatherAdapter", "getCount");
            return getArguments().getParcelableArrayList("forecast").size();
        }

        @Override
        public WeatherModel getItem(int position) {
            return (WeatherModel) getArguments().getParcelableArrayList("forecast").get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Logger.e("WeatherAdapter", "getView(" + position + ")");
            View row = convertView;
            if (row == null) {
                row = getActivity().getLayoutInflater().inflate(R.layout.weather_item, parent, false);
            }
            TextView dayOfTheWeekTextView = ViewHolder.get(row, R.id.text_view_day_of_the_week);
            TextView minTempTextView = ViewHolder.get(row, R.id.text_view_min_temp);
            TextView maxTempTextView = ViewHolder.get(row, R.id.text_view_max_temp);
            TextView mainTextView = ViewHolder.get(row, R.id.text_view_main);
            ImageView iconImageView = ViewHolder.get(row, R.id.image_view_icon);

            WeatherModel weatherModel = getItem(position);
            dayOfTheWeekTextView.setText(weatherModel.dayOfTheWeek);
            minTempTextView.setText(weatherModel.minTemperature + "\u00B0");
            maxTempTextView.setText(weatherModel.maxTemperature + "\u00B0");
            mainTextView.setText(weatherModel.main);
            iconImageView.setImageResource(weatherModel.getIconResId());

            return row;
        }
    }
}
