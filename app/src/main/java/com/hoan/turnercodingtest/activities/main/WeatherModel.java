package com.hoan.turnercodingtest.activities.main;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.hoan.turnercodingtest.R;
import com.hoan.turnercodingtest.utils.DateHelper;

/**
 * Created by Hoan on 10/10/2016.
 */

public class WeatherModel implements Parcelable {
    public static final int NO_RESOURCE = -1;
    public long dt;
    public int maxTemperature;
    public int minTemperature;
    public int humidity;
    public int pressure;
    public float windSpeed;
    public float degree;
    public String main;
    public String icon;

    public String getDate() {
        return DateHelper.getDate(dt, "GMT-4", "MMM d");
    }

    public String getDayOfTheWeek() {
        return DateHelper.getDate(dt, "GMT-4", "EEE");
    }

    public String getDayOfTheWeek(Context context) {
        if (DateHelper.isTomorrow(dt, "GMT-4")) {
            return context.getString(R.string.tomorrow);
        }
        return getDayOfTheWeek();
    }

    // Could create a hashmap instead but doing this saves some memory.
    // Could use switch statement if JDK is 7 or later
    public int getIconResId() {
        if ("01".equals(icon)) return R.drawable.ic_clear;

        if ("02".equals(icon)) return R.drawable.ic_light_clouds;

        if ("03".equals(icon) || "04".equals(icon)) return R.drawable.ic_cloudy;

        if ("09".equals(icon)) return R.drawable.ic_light_rain;

        if ("10".equals(icon)) return R.drawable.ic_rain;

        if ("11".equals(icon)) return R.drawable.ic_storm;

        if ("13".equals(icon)) return R.drawable.ic_snow;

        if ("50".equals(icon)) return R.drawable.ic_fog;

        return NO_RESOURCE;
    }

    // Could create a hashmap instead but doing this save some memory.
    // Could use switch statement if JDK is 7 or later
    public int getArtResId() {
        if ("01".equals(icon)) return R.drawable.art_clear;

        if ("02".equals(icon)) return R.drawable.art_light_clouds;

        if ("03".equals(icon) || "04".equals(icon)) return R.drawable.art_clouds;

        if ("09".equals(icon)) return R.drawable.art_light_rain;

        if ("10".equals(icon)) return R.drawable.art_rain;

        if ("11".equals(icon)) return R.drawable.art_storm;

        if ("13".equals(icon)) return R.drawable.art_snow;

        if ("50".equals(icon)) return R.drawable.art_fog;

        return NO_RESOURCE;
    }

    public String getWindDir() {
        if (degree <= 11.25 || degree > 348.75) return "N";
        if (degree > 11.25 && degree <= 33.75) return "NNE";
        if (degree > 33.75 && degree <= 56.25) return "NE";
        if (degree > 56.25 && degree <= 78.75) return "ENE";
        if (degree > 78.75 && degree <= 101.25) return "E";
        if (degree > 101.25 && degree <= 123.75) return "ESE";
        if (degree > 123.75 && degree <= 146.25) return "SE";
        if (degree > 146.25 && degree <= 168.75) return "SSE";
        if (degree > 168.75 && degree <= 191.25) return "S";
        if (degree > 191.25 && degree <= 213.75) return "SSW";
        if (degree > 213.75 && degree <= 236.25) return "SW";
        if (degree > 236.25 && degree <= 258.75) return "WSW";
        if (degree > 258.75 && degree <= 281.25) return "W";
        if (degree > 281.25 && degree <= 303.75) return "WNW";
        if (degree > 303.75 && degree <= 326.25) return "NW";
        if (degree > 326.25 && degree <= 348.75) return "NNW";
        return "";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.dt);
        dest.writeInt(this.maxTemperature);
        dest.writeInt(this.minTemperature);
        dest.writeInt(this.humidity);
        dest.writeInt(this.pressure);
        dest.writeFloat(this.windSpeed);
        dest.writeFloat(this.degree);
        dest.writeString(this.main);
        dest.writeString(this.icon);
    }

    public WeatherModel() {
    }

    protected WeatherModel(Parcel in) {
        this.dt = in.readLong();
        this.maxTemperature = in.readInt();
        this.minTemperature = in.readInt();
        this.humidity = in.readInt();
        this.pressure = in.readInt();
        this.windSpeed = in.readFloat();
        this.degree = in.readFloat();
        this.main = in.readString();
        this.icon = in.readString();
    }

    public static final Creator<WeatherModel> CREATOR = new Creator<WeatherModel>() {
        @Override
        public WeatherModel createFromParcel(Parcel source) {
            return new WeatherModel(source);
        }

        @Override
        public WeatherModel[] newArray(int size) {
            return new WeatherModel[size];
        }
    };

    @Override
    public String toString() {
        return "dt = " + dt + "\nmaxTemperature = " + maxTemperature + "\nminTemperature = " + minTemperature
                + "\nicon = " + icon + "\nhumidity = " + humidity + "\nPressure = " + pressure + "\nwindSpeed = "
                + windSpeed + "\ndegree = " + degree;
    }
}
