package com.hoan.turnercodingtest.services;

/**
 * Created by Hoan on 10/15/2016.
 */

public interface FutureTaskListener<V> {
    void onCompletion(V result);
    void onError(String errorMessage);
    void onProgress(float progress);
}
