package com.github.ancarola.bootcamp.owmstate.entries;

import com.github.ancarola.bootcamp.owmstate.OwmState;

import org.json.JSONException;
import org.json.JSONObject;

public class OwmWeatherEntry implements OwmState {

    public final int id;
    public final String main;
    public final String description;
    String icon;

    //static final String iconLink = "http://openweathermap.org/img/wn/%icon@2x.png";

    public OwmWeatherEntry(JSONObject reference) throws JSONException {

        id = reference.getInt("id");
        main = reference.getString("main");
        description = reference.getString("description");
        icon = reference.getString("icon");
    }

    // TODO support icons
    //public
}
