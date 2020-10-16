package com.github.ancarola.bootcamp.owmstate.sets;

import com.github.ancarola.bootcamp.owmstate.OwmState;
import com.github.ancarola.bootcamp.owmstate.entries.OwmDaily;

import org.json.JSONArray;
import org.json.JSONException;

public class OwmDailySet implements OwmState {

    OwmDaily[] entries;

    public OwmDailySet(JSONArray reference) throws JSONException {
        this(reference, Integer.MAX_VALUE);
    }

    public OwmDailySet(JSONArray reference, int maximum) throws JSONException {
        int size = Math.min(maximum, reference.length());
        entries = new OwmDaily[size];

        for (int i = 0; i < size; ++i) {
            entries[i] = new OwmDaily(reference.getJSONObject(i));
        }
    }

    public OwmDaily getDay(int index) {
        return entries[index];
    }
}
