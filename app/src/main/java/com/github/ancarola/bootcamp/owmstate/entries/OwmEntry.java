package com.github.ancarola.bootcamp.owmstate.entries;

import com.github.ancarola.bootcamp.owmstate.OwmState;
import com.github.ancarola.bootcamp.owmstate.entries.OwmWeatherEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OwmEntry implements OwmState {

    JSONObject reference;

    public OwmEntry(JSONObject reference) {
        this.reference = reference;
    }

    public int getDt() throws JSONException {
        return reference.getInt("dt");
    }

    public double getPressure() throws JSONException {
        return reference.getDouble("pressure");
    }

    public double getHumidity() throws JSONException {
        return reference.getDouble("humidity");
    }

    public double getCloudness() throws JSONException {
        return reference.getDouble("clouds");
    }

    public double getWindSpeed() throws JSONException {
        return reference.getDouble("wind_speed");
    }

    public double getWindTemperature() throws JSONException {
        return reference.getDouble("wind_deg");
    }

    public OwmWeatherEntry[] getWeather() throws JSONException {
        JSONArray arr = reference.getJSONArray("weather");
        OwmWeatherEntry[] ws = new OwmWeatherEntry[arr.length()];

        for (int i = 0; i < arr.length(); ++i)
            ws[i] = new OwmWeatherEntry(arr.getJSONObject(i));

        return ws;
    }


}
