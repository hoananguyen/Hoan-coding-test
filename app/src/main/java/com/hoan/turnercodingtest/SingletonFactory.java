package com.hoan.turnercodingtest;

import android.util.Log;

import com.hoan.turnercodingtest.services.DataService;
import com.hoan.turnercodingtest.services.OpenWeatherDataService;
import com.hoan.turnercodingtest.services.NetworkService;
import com.hoan.turnercodingtest.services.Singleton;
import com.hoan.turnercodingtest.services.VolleyNetworkService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Hoan on 10/15/2016.
 */

public enum SingletonFactory {
    INSTANCE;

    private final SingletonParam mfSingletonParam = new SingletonParam();

    private final HashMap<String, SingletonInfo> mfSingletonInfoHashMap = new HashMap<>();

    /*public DataService getDataService(Object object) {
        SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(DataService.class.getName());
        if (singletonInfo == null) {
            singletonInfo = new SingletonInfo(new OpenWeatherDataService(mfSingletonParam), object);
            mfSingletonInfoHashMap.put(DataService.class.getName(), singletonInfo);
        } else {
            synchronized (singletonInfo.callingObjects) {
                if (singletonInfo.callingObjects.contains(object)) {
                    throw new RuntimeException("getDataService has already been called and not released");
                } else {
                    singletonInfo.callingObjects.add(object);
                    if (singletonInfo.singleton == null) {
                        singletonInfo = new SingletonInfo(new OpenWeatherDataService(mfSingletonParam), object);
                    }
                }
            }
        }
        return (DataService) singletonInfo.singleton;
    }

    public NetworkService getNetworkService(Object object) {
        SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(NetworkService.class.getName());
        if (singletonInfo == null) {
            singletonInfo = new SingletonInfo(new VolleyNetworkService(mfSingletonParam), object);
            mfSingletonInfoHashMap.put(NetworkService.class.getName(), singletonInfo);
        } else {
            synchronized (singletonInfo.callingObjects) {
                if (singletonInfo.callingObjects.contains(object)) {
                    throw new RuntimeException("getNetworkService has already been called and not released");
                } else {
                    singletonInfo.callingObjects.add(object);
                    if (singletonInfo.singleton == null) {
                        singletonInfo = new SingletonInfo(new VolleyNetworkService(mfSingletonParam), object);
                    }
                }
            }
        }
        return (NetworkService) singletonInfo.singleton;
    }

    public void releaseNetWorkService(Object object) {
        SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(NetworkService.class.getName());
        if (singletonInfo == null) return;

        synchronized (singletonInfo.callingObjects) {
            singletonInfo.callingObjects.remove(object);
            if (singletonInfo.callingObjects.isEmpty()) {
                singletonInfo.singleton = null;
            }
        }
    }*/

    public Object getSingleton(String singletonClassName, Object object) {
        SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(singletonClassName);
        if (singletonInfo == null) {
            singletonInfo = new SingletonInfo(createSingleton(singletonClassName), object);
            mfSingletonInfoHashMap.put(singletonClassName, singletonInfo);
        } else {
            synchronized (singletonInfo.callingObjects) {
                if (singletonInfo.callingObjects.contains(object)) {
                    throw new RuntimeException("getSingleton(" + singletonClassName + ") has already been called and not released");
                } else {
                    singletonInfo.callingObjects.add(object);
                    if (singletonInfo.singleton == null) {
                        singletonInfo.singleton.shutdown(mfSingletonParam);
                        singletonInfo.singleton = createSingleton(singletonClassName);
                    }
                }
            }
        }
        return singletonInfo.singleton;
    }

    public void releaseSingleton(String singletonClassName, Object object) {
        SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(singletonClassName);
        if (singletonInfo == null) return;

        synchronized (singletonInfo.callingObjects) {
            singletonInfo.callingObjects.remove(object);
            if (singletonInfo.callingObjects.isEmpty()) {
                singletonInfo.singleton = null;
            }
        }
    }

    public void checkMemoryLeak() {
        Iterator<String> iterator = mfSingletonInfoHashMap.keySet().iterator();
        while (iterator.hasNext()) {
            SingletonInfo singletonInfo = mfSingletonInfoHashMap.get(iterator.next());
            if (singletonInfo.singleton != null) {
                for (Object object : singletonInfo.callingObjects) {
                    Log.e("SingletonFactory", "SingletonFactory has leak memory: " + object.getClass().getSimpleName());
                }
            }
        }
    }

    private Singleton createSingleton(String singletonClassName) {
        if (DataService.class.getName().equals(singletonClassName)) {
            return new OpenWeatherDataService(mfSingletonParam);
        }

        if (NetworkService.class.getName().equals(singletonClassName)) {
            return new VolleyNetworkService(mfSingletonParam);
        }

        return null;
    }

    private class SingletonInfo {
        final Set callingObjects = new HashSet();
        Singleton singleton;

        SingletonInfo(Singleton singleton, Object callingObject) {
            this.singleton = singleton;
            callingObjects.add(callingObject);
        }
    }

    public class SingletonParam {
        private SingletonParam() {

        }
    }
}
