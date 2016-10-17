package com.hoan.turnercodingtest.activities.main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import com.hoan.turnercodingtest.R;
import com.hoan.turnercodingtest.SingletonFactory;
import com.hoan.turnercodingtest.activities.BaseActivityWithFragment;
import com.hoan.turnercodingtest.services.DataService;
import com.hoan.turnercodingtest.services.FutureTaskListener;
import com.hoan.turnercodingtest.utils.Logger;

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

        Logger.e("MainActivity", "onCreate");
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Logger.e("MainActivity", "onStart");
        mProgressBar.setVisibility(View.VISIBLE);
        //mWeatherTask = new WeatherTask("Atlanta,ga", "json", "imperial");
        //mForecastTask = new ForecastTask("Atlanta,ga", "json", "imperial");
        mWeatherTask = new WeatherTask("Atlanta,ga");
        mForecastTask = new ForecastTask("Atlanta,ga");
        mWeatherTask.execute();
        mForecastTask.execute();
    }

    @Override
    protected void onStop() {
        super.onStop();

        Logger.e("MainActivity", "onStop");
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
        Logger.e("MainActivity", "onBackPressed");
        if (getFragmentManager().findFragmentByTag("WeatherDetailFragment") == null) {
            SingletonFactory.INSTANCE.checkMemoryLeak();
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
        //private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?q=";
        private final String mLocation;
        /*private final String mUnit;
        private final String mMode;*/

        /*public WeatherTask(String location, String mode, String unit) {
            mLocation = location;
            mUnit = unit;
            mMode = mode;
        }*/

        public WeatherTask(String location) {
            mLocation = location;
        }

        /*@Override
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
                        mWeatherModel = JsonParser.parseWeather(response);
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
        }*/

        @Override
        protected Void doInBackground(Void... params) {
            Logger.e("WeatherTask", "doInBackground");
            synchronized (mLockObject) {
                mWeatherDownloadState = WEATHER_DOWNLOAD_STARTED;
            }

            DataService dataService = (DataService) SingletonFactory.INSTANCE.getSingleton(DataService.class.getName(), this);
            dataService.getWeather(mLocation, new FutureTaskListener<WeatherModel>() {
                @Override
                public void onCompletion(WeatherModel result) {
                    Logger.e("WeatherTask", "onCompletion");
                    if (!isCancelled()) {
                        mWeatherModel = result;
                        synchronized (mLockObject) {
                            mWeatherDownloadState = WEATHER_DOWNLOAD_COMPLETED;
                            mLockObject.notifyAll();
                        }
                    }
                    releaseSingleton();
                }

                @Override
                public void onError(String errorMessage) {
                    Logger.e("WeatherTask", "onError");
                    if (!isCancelled()) {
                        synchronized (mLockObject) {
                            mWeatherDownloadState = WEATHER_DOWNLOAD_ERROR;
                            mLockObject.notifyAll();
                        }
                    }
                    releaseSingleton();
                }

                @Override
                public void onProgress(float progress) {

                }
            });

            return null;
        }

        private void releaseSingleton() {
            SingletonFactory.INSTANCE.releaseSingleton(DataService.class.getName(), this);
        }
    }

    private class ForecastTask extends AsyncTask<Void, Void, Void> {
        //private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q=";
        private final String mLocation;
        /*private final String mUnit;
        private final String mMode;*/
        //private List<WeatherModel> mWeatherModelList;

        /*public ForecastTask(String location, String mode, String unit) {
            mLocation = location;
            mUnit = unit;
            mMode = mode;
        }*/

        public ForecastTask(String location) {
            mLocation = location;
        }

        /*@Override
        protected Void doInBackground(Void... params) {
            String url = BASE_URL + mLocation + "&mode=" + mMode
                    + "&units=" + mUnit + "&cnt=7&appid=" + Configuration.getWeatherKey();
            NetworkHelper.queryServer(url, new NetworkHelper.ServerResponseListener() {
                @Override
                public void onCompleted(String response) {
                    if (!isCancelled()) {
                        onDownloadCompleted(JsonParser.parseForecast(response));
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
        }*/

        @Override
        protected Void doInBackground(Void... params) {
            Logger.e("ForecastTask", "doInBackground");
            DataService dataService = (DataService) SingletonFactory.INSTANCE.getSingleton(DataService.class.getName(), this);
            dataService.getForecast(mLocation, new FutureTaskListener<ArrayList<WeatherModel>>() {
                @Override
                public void onCompletion(ArrayList<WeatherModel> result) {
                    Logger.e("ForecastTask", "onCompletion");
                    if (!isCancelled()) {
                        onDownloadCompleted(result);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    Logger.e("ForecastTask", "onError");
                    if (!isCancelled()) {
                        onDownloadCompleted(null);
                    }
                }

                @Override
                public void onProgress(float progress) {

                }
            });

            return null;
        }

        private void onDownloadCompleted(final ArrayList<WeatherModel> weatherModels) {
            Logger.e("ForecastTask", "onDownloadCompleted");
            SingletonFactory.INSTANCE.releaseSingleton(DataService.class.getName(), this);
            while (mWeatherDownloadState == WEATHER_DOWNLOAD_STARTED) {
                synchronized (mLockObject) {
                    try {
                        mLockObject.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        return;
                    }
                    Logger.e("ForecastTask", "onDownloadCompleted after wait");
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