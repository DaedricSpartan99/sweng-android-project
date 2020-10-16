package com.github.ancarola.bootcamp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.ancarola.bootcamp.owmstate.entries.OwmDaily;
import com.github.ancarola.bootcamp.owmstate.entries.OwmWeatherEntry;
import com.github.ancarola.bootcamp.owmstate.sets.OwmDailySet;

import org.json.JSONException;

public class WeatherActivity extends AppCompatActivity {

    OwmWeatherEntry averange_weather;
    OwmDaily daily_weather;

    int daycode;

    OwmConnection connection;

    // TODO manage sliding
    //SlidrInterface slidr;

    // TODO All components
    ImageView icon;
    TextView sunrise, sunset, pressure, humidity;
    TextView dayview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        //slidr = Slidr.attach(this);

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        daycode = (b != null) ? b.getInt(MainActivity.WEATHER_CODE_EXTRA) : MainActivity.TODAY;

        // an opened connection should already exist
        connection = MainActivity.getWeatherConnection();

        // components
        icon = findViewById(R.id.weathericon);

        sunrise = findViewById(R.id.sunrise);
        sunset = findViewById(R.id.sunset);
        pressure = findViewById(R.id.pressure);
        humidity = findViewById(R.id.humidity);

        dayview = findViewById(R.id.dayview);

        // page text
        switch (daycode) {
            case MainActivity.TODAY:
                dayview.setText("Today");
                break;

                case MainActivity.TOMORROW:
                dayview.setText("Tomorrow");
                break;

            case MainActivity.AFTER_TOMORROW:
                dayview.setText("After tomorrow");
                break;

            default:
                dayview.setText(String.valueOf(daycode));
                break;
        }

        // refresh
        if (connection != null) {
            refresh(!connection.hasLocalData()); // ensure existence of data
        }
    }

    /*public void lockSlide(View v) {

    }

    public void unlockSlide(View v) {

    }*/

    void refresh(boolean remotely) {

        OwmDailySet dailys = (OwmDailySet) (remotely ?  connection.pull() : connection.getLocalData());

        daily_weather = dailys.getDay(daycode);

        try {
            averange_weather = daily_weather.getWeather()[0];
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // TODO write on components
        try {
            pressure.setText(getString(R.string.pressure, daily_weather.getPressure()));
            humidity.setText(getString(R.string.humidity, daily_weather.getHumidity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // on click Button next day
    public void slide_left(View v) {

        Intent intent = new Intent(this, WeatherActivity.class);
        Bundle b = new Bundle();

        int max = ((OwmHttpRequest)connection).getMaxdays();

        b.putInt(MainActivity.WEATHER_CODE_EXTRA, (daycode + 1) % max);

        intent.putExtras(b);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void slide_right(View v) {
        Intent intent = new Intent(this, WeatherActivity.class);
        Bundle b = new Bundle();

        int max = ((OwmHttpRequest)connection).getMaxdays();
        int next_daycode = (daycode == MainActivity.TODAY) ? max : daycode - 1;

        b.putInt(MainActivity.WEATHER_CODE_EXTRA, next_daycode);

        intent.putExtras(b);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void finish() {
        super.finish();
        // TODO determine where to slide
    }
}