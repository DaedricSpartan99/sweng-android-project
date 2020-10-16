package com.github.ancarola.bootcamp.owmstate.entries;

import com.github.ancarola.bootcamp.owmstate.OwmState;

import org.json.JSONException;
import org.json.JSONObject;

public class OwmTempEntry implements OwmState {

    public final double day, night, evening, morning;
    JSONObject reference;

    public OwmTempEntry(JSONObject reference) throws JSONException {
        this.reference = reference;

        day = reference.getDouble("day");
        night= reference.getDouble("night");
        evening = reference.getDouble("eve");
        morning = reference.getDouble("morn");
    }

    public Double min() throws JSONException {
        return reference.isNull("min") ? null : reference.getDouble("min");
    }

    public Double max() throws JSONException {
        return reference.isNull("max") ? null : reference.getDouble("max");
    }
}
