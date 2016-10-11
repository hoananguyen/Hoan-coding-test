package com.hoan.turnercodingtest.activities.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.hoan.turnercodingtest.Configuration;
import com.hoan.turnercodingtest.R;
import com.hoan.turnercodingtest.activities.BaseActivityWithFragment;
import com.hoan.turnercodingtest.utils.JsonParser;
import com.hoan.turnercodingtest.utils.Logger;
import com.hoan.turnercodingtest.utils.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * When the application start the app will show the WeatherFragment.
 *
 * The design follows MVC with fragments as View and activities as Controller.
 * Thus fragments will get all the data from controllers to load the data to the fragment views
 * and retrieve data or click events to send back to controllers to handle.
 *
 * Would have use Volley or Retrofit for networking and Gson if allowed to use library.
 */
public class MainActivity extends BaseActivityWithFragment implements WeatherFragment.WeatherFragmentEventListener{
    private static final int WEATHER_DOWNLOAD_STARTED = 0;
    private static final int WEATHER_DOWNLOAD_COMPLETED = 1;
    private static final int WEATHER_DOWNLOAD_ERROR = 2;

    private WeatherModel mWeatherModel;
    private WeatherTask mWeatherTask;
    private ForecastTask mForecastTask;
    private final Object mLockObject = new Object();
    private int mWeatherDownloadState;

    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        mProgressBar.setVisibility(View.VISIBLE);
        mWeatherTask = new WeatherTask("Atlanta,ga", "json", "imperial");
        mForecastTask = new ForecastTask("Atlanta,ga", "json", "imperial");
        mWeatherTask.execute();
        mForecastTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();

        mProgressBar.setVisibility(View.GONE);
        if (mForecastTask != null && mForecastTask.getStatus() != AsyncTask.Status.FINISHED) {
            mForecastTask.cancel(true);
        }

        if (mWeatherTask != null && mWeatherTask.getStatus() != AsyncTask.Status.FINISHED) {
            mWeatherTask.cancel(true);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().findFragmentByTag("WeatherDetailFragment") == null) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    @Override
    public void onItemClicked(WeatherModel weatherModel) {
        Logger.e("MainActivity", "onItemClicked");
        showFragment(R.id.content_main, WeatherDetailFragment.newInstance(weatherModel), true);
    }

    private class WeatherTask extends AsyncTask<Void, Void, Void> {
        private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private final String mLocation;
        private final String mUnit;
        private final String mMode;

        public WeatherTask(String location, String mode, String unit) {
            mLocation = location;
            mUnit = unit;
            mMode = mode;
        }

        @Override
        protected Void doInBackground(Void... params) {
            synchronized (mLockObject) {
                mWeatherDownloadState = WEATHER_DOWNLOAD_STARTED;
            }

            String url = BASE_URL + mLocation + "&mode=" + mMode
                    + "&units=" + mUnit + "&appid=" + Configuration.getWeatherKey();
            NetworkHelper.queryServer(url, new NetworkHelper.ServerResponseListener() {
                @Override
                public void onCompleted(String response) {
                    if (!isCancelled()) {
                        mWeatherModel = JsonParser.parseWeather(MainActivity.this, response);
                        if (mWeatherModel == null) {
                            onError(null);
                        } else {
                            synchronized (mLockObject) {
                                mWeatherDownloadState = WEATHER_DOWNLOAD_COMPLETED;
                                mLockObject.notifyAll();
                            }
                        }
                    }
                }

                @Override
                public void onError(String error) {
                    if (!isCancelled()) {
                        synchronized (mLockObject) {
                            mWeatherDownloadState = WEATHER_DOWNLOAD_ERROR;
                            mLockObject.notifyAll();
                        }
                    }
                }
            });

            return null;
        }
    }

    private class ForecastTask extends AsyncTask<Void, Void, Void> {
        private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
        private final String mLocation;
        private final String mUnit;
        private final String mMode;
        private List<WeatherModel> mWeatherModelList;

        public ForecastTask(String location, String mode, String unit) {
            mLocation = location;
            mUnit = unit;
            mMode = mode;
        }

        @Override
        protected Void doInBackground(Void... params) {
            String url = BASE_URL + mLocation + "&mode=" + mMode
                    + "&units=" + mUnit + "&cnt=5&appid=" + Configuration.getWeatherKey();
            NetworkHelper.queryServer(url, new NetworkHelper.ServerResponseListener() {
                @Override
                public void onCompleted(String response) {
                    if (!isCancelled()) {
                        onDownloadCompleted(JsonParser.parseForecast(MainActivity.this, response));
                    }
                }

                @Override
                public void onError(String error) {
                    if (!isCancelled()) {
                        onDownloadCompleted(null);
                    }
                }
            });

            return null;
        }

        private void onDownloadCompleted(final ArrayList<WeatherModel> weatherModels) {

            while (mWeatherDownloadState == WEATHER_DOWNLOAD_STARTED) {
                synchronized (mLockObject) {
                    try {
                        mLockObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                }
            }
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mProgressBar.setVisibility(View.GONE);
                    if (mWeatherDownloadState == WEATHER_DOWNLOAD_ERROR) {
                        // What should I do here if there is error getting today weather
                    } else {
                        showFragment(R.id.content_main, WeatherFragment.newInstance(mWeatherModel, weatherModels));
                    }
                }
            });
        }
    }

}