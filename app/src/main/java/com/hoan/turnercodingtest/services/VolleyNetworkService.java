package com.hoan.turnercodingtest.services;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.hoan.turnercodingtest.SingletonFactory;
import com.hoan.turnercodingtest.TurnerApplication;
import com.hoan.turnercodingtest.utils.Logger;

/**
 * Created by Hoan on 10/15/2016.
 */

public class VolleyNetworkService implements NetworkService, Singleton {
    private static final int VOLLEY_TIME_OUT = 3000;
    private static final int NUMBER_OF_RETRY = 0;

    private final RequestQueue mfRequestQueue;

    public VolleyNetworkService(SingletonFactory.SingletonParam singletonParam) {
        mfRequestQueue = Volley.newRequestQueue(TurnerApplication.getApplication());
    }

    @Override
    public void cancel(String tag) {
        Logger.e("VolleyNetworkService", "cancel(" + tag + ")");
        mfRequestQueue.cancelAll(tag);
    }

    @Override
    public void cancelAll() {
        mfRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
        SingletonFactory.INSTANCE.releaseSingleton(NetworkService.class.getName(), this);
    }

    @Override
    public void shutdown(SingletonFactory.SingletonParam singletonParam) {
        Logger.e("VolleyNetworkService", "shutdown");
        if (mfRequestQueue != null) {
            cancelAll();
        }
    }

    @Override
    public void getString(String url, String tag, FutureTaskListener<String> listener) {
        Logger.e("VolleyNetworkService", "getString(" + url + ")");
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new VolleyResponseListener<>(listener), new VolleyErrorListener<>(listener));
        startRequest(stringRequest, tag);
    }

    private void startRequest(Request request, String tag) {
        Logger.e("VolleyNetworkService", "startRequest");
        request.setRetryPolicy(new DefaultRetryPolicy(VOLLEY_TIME_OUT, NUMBER_OF_RETRY, 0));
        request.setTag(tag);
        mfRequestQueue.add(request);
    }

    private class VolleyResponseListener<T> implements Response.Listener<T> {
        private final FutureTaskListener<T> mListener;

        public VolleyResponseListener(FutureTaskListener<T> listener) {
            mListener = listener;
        }

        @Override
        public void onResponse(T response) {
            Logger.e("VolleyResponseListener", "onResponse");
            mListener.onCompletion(response);
        }
    }

    private class VolleyErrorListener<T> implements Response.ErrorListener {
        private final FutureTaskListener<T> mListener;

        public VolleyErrorListener(FutureTaskListener<T> listener) {
            mListener = listener;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Logger.e("VolleyResponseListener", "onError");
            mListener.onError(error.getMessage());
        }
    }
}
