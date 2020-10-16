package com.github.ancarola.bootcamp.owmstate.entries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OwmDaily extends OwmEntry {

    OwmTempEntry temp, feels_like;

    public OwmDaily(JSONObject reference) throws JSONException {
        super(reference);

        temp = new OwmTempEntry(reference.getJSONObject("temp"));
        feels_like = new OwmTempEntry(reference.getJSONObject("feels_like"));
    }

    public OwmTempEntry getTemp() {
        return temp;
    }

    public OwmTempEntry getFeelsLike() {
        return feels_like;
    }
}
