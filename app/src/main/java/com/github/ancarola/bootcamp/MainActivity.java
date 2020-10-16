package com.github.ancarola.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String GREETING_EXTRA = "greeting_message";
    public static final String WEATHER_CODE_EXTRA = "OAA";

    public static final int TODAY = 0;
    public static final int TOMORROW = 1;
    public static final int AFTER_TOMORROW = 2;

    // initialize the connection when the first slide wants to be opened
    //weather_connection = new OwmHttpRequest(new AndroidLocationService(this));
    static OwmConnection weather_connection;

    public static OwmConnection getWeatherConnection() {
        return weather_connection;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int PERMISSION_CODE_REQ = 0xfe;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
            System.out.println("Location permission obtained");
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            //showInContextUI(...);
            System.err.println("Warning: location permission denied!!");
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_CODE_REQ);
        }
    }

    /** Called when the user taps the Send button */
    public void sendMessage(View view) {
        // create intent
        Intent intent = new Intent(this, GreetingActivity.class);

        //
        EditText editText = (EditText) findViewById(R.id.mainName);
        String message = editText.getText().toString();
        intent.putExtra(GREETING_EXTRA, message);
        startActivity(intent);
    }

    public void sendWeather(View view) {
        // create intent
        Intent intent = new Intent(this, WeatherActivity.class);

        if (weather_connection == null) {
            weather_connection = new OwmHttpRequest(new AndroidLocationService(this), getString(R.string.openweather_api_key));
        }

        Bundle b = new Bundle();
        b.putInt(WEATHER_CODE_EXTRA, TODAY);
        intent.putExtras(b);
        startActivity(intent);
    }

}