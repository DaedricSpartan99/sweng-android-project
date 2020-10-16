package com.github.ancarola.bootcamp.owmstate.sets;

import com.github.ancarola.bootcamp.owmstate.OwmState;
import com.github.ancarola.bootcamp.owmstate.entries.OwmDaily;
import com.github.ancarola.bootcamp.owmstate.entries.OwmEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OwmSet implements OwmState {

    JSONObject reference;

    // TODO, much more general structure
    public OwmSet(JSONObject reference) {
        this.reference = reference;
    }

    public OwmState getCurrent() throws JSONException {
        return new OwmEntry(reference.getJSONObject("current"));
    }

    public OwmState getMinutely() throws JSONException {
        return new OwmEntry(reference.getJSONObject("minutely"));
    }

    public OwmState getHourly() throws JSONException {
        return new OwmEntry(reference.getJSONObject("hourly"));
    }

    public OwmDailySet getDaily(int maximum) throws JSONException {
        return new OwmDailySet(reference.getJSONArray("daily"), maximum);
    }

    // TODO getDaily, ecc...
}
