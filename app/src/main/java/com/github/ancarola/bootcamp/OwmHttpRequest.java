package com.github.ancarola.bootcamp;

import android.os.StrictMode;

import com.github.ancarola.bootcamp.owmstate.sets.OwmSet;
import com.github.ancarola.bootcamp.owmstate.OwmState;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.channels.NoConnectionPendingException;

import javax.net.ssl.HttpsURLConnection;

public class OwmHttpRequest implements OwmConnection {

    private OwmState state;
    private LocationService locService;

    private int maxdays;

    private final String idkey;

    private static final String TEMPLATE = "https://api.openweathermap.org/data/2.5/onecall?lat=%lat&lon=%lon&exclude=current,minutely,hourly&units=metric&appid=%key";

    private static final String F_LATITUDE = "%lat";
    private static final String F_LONGITUDE = "%lon";
    //private static final String F_EXCLUDE = "%part";
    private static final String F_IDKEY = "%key";

    public OwmHttpRequest(LocationService locService, String key) {

        this.locService = locService;
        this.idkey = key;

        // TODO: Remove this line of code once I learn about asynchronous operations!
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());

        this.maxdays = 3; // default value
    }

    public int getMaxdays() {
        return maxdays;
    }

    public void setMaxdays(int maxdays) {
        this.maxdays = maxdays;
    }

    private JSONObject getJSONresponse(HttpsURLConnection connection) throws IOException, JSONException {

        // pass the ownership of the input stream to a BufferedReader
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        return new JSONObject(response.toString());
    }

    public JSONObject owmHttpGETRequest(URL url) throws IOException {
        HttpsURLConnection connection = null;

        connection = (HttpsURLConnection) url.openConnection();
        connection.setReadTimeout(3000);
        connection.setConnectTimeout(3000);
        connection.setRequestMethod("GET");

        // Already true by default but setting just in case; needs to be true since this request
        // is carrying an input (response) body.
        connection.setDoInput(true);

        int responseCode = connection.getResponseCode();

        JSONObject jsonobj = null;

        if (responseCode == HttpsURLConnection.HTTP_OK) { // success
            try {
                jsonobj = getJSONresponse(connection);
            } catch(JSONException e) {
                e.printStackTrace();
            }
        }

        connection.disconnect();

        return jsonobj;
    }

    public void fetch() throws NoConnectionPendingException, IOException {

        // get data
        String queryUrl = TEMPLATE;

        // get position
        // TODO manage permission
        Location location = locService.get();

        if (location == null) {
            System.err.println("No location found");
            throw new NoConnectionPendingException();
        }

        queryUrl = queryUrl.replaceAll(F_LATITUDE, String.valueOf(location.latitude));
        queryUrl = queryUrl.replaceAll(F_LONGITUDE, String.valueOf(location.longitude));

        queryUrl = queryUrl.replaceAll(F_IDKEY, idkey);

        JSONObject jsonobj = owmHttpGETRequest(new URL(queryUrl));

        if (jsonobj == null) {
            System.err.println("Could not parse JSON GET response. Exiting...");
            return;
        }

        OwmSet set = new OwmSet(jsonobj);

        try {
            state = set.getDaily(maxdays);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public OwmState getLocalData() {
        if (state == null) {
            try {
                fetch();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return state;
    }

    @Override
    public OwmState pull() throws NoConnectionPendingException {
        try {
            fetch();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return state;
    }

    @Override
    public boolean hasLocalData() {
        return state != null;
    }
}
