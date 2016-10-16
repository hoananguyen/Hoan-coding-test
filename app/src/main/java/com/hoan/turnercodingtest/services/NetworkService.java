package com.hoan.turnercodingtest.services;

/**
 * Created by Hoan on 10/15/2016.
 */

public interface NetworkService {
    void cancel(String tag);
    void cancelAll();
    void getString(String url, String tag, FutureTaskListener<String> listener);
}
