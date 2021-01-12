package com.example.retrone;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.retrone.SearchPlaceNameActivity.EXTRA_TEXT;

public class MainActivity extends AppCompatActivity{
    private TextView textViewResult, condition_text, place_name, date_day_month;

    private TextView wind_speed, pressure_in, chance_of_rain, humidity_value, uv_index, real_feel;
    private TextView SunRise_phase, sunset_phase, moonrise_phase, moonSet_Phase, moon_phase_side, moon_illumination_phase;
    private JsonPlaceHolder jsonPlaceHolder;

    public static final String KEY_ = "";/**Enter Your Key Here*/
    FloatingActionButton btn;
    String text;
    private long PressTime_is;
    private String date = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH).format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initViews();
/***---------------------------------------------------------*/
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.weatherapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        jsonPlaceHolder = retrofit.create(JsonPlaceHolder.class);
/**-----------------------------------------------------------*/

        /* Receiving  the place name from SearchPlaceNameActivity*/
        Intent intent = getIntent();
        text = intent.getStringExtra(EXTRA_TEXT);

/**-----------------------------------------------------------*/

        /* Go to the next Activity*/
        btn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, SearchPlaceNameActivity.class));
        });
/**-----------------------------------------------------------*/

        /* to check for place name */
        check();

/**-----------------------------------------------------------*/
        /* to Display day month and year the app*/
        DayMonthYear();


    }

    private void initViews() {
        date_day_month = findViewById(R.id.date_day_month);
        place_name = findViewById(R.id.place_name);
        textViewResult = findViewById(R.id.txt_view_result);
        condition_text = findViewById(R.id.condition_text);
        btn = findViewById(R.id.btn);

        /*------------------------------*/
        wind_speed = findViewById(R.id.wind_speed);
        humidity_value = findViewById(R.id.humidity_value);
        uv_index = findViewById(R.id.uv_index);
        chance_of_rain = findViewById(R.id.chance_of_rain);
        pressure_in = findViewById(R.id.pressure_in);
        real_feel = findViewById(R.id.real_feel);

        SunRise_phase = findViewById(R.id.SunRise_phase_text);
        sunset_phase = findViewById(R.id.sunset_phase);
        moonrise_phase = findViewById(R.id.moonrise_phase);
        moonSet_Phase = findViewById(R.id.moonSet_Phase);
        moon_phase_side = findViewById(R.id.moon_phase_side);
        moon_illumination_phase = findViewById(R.id.moon_illumination_phase);


    }

    private void check() {
        if (text == null) {

        } else {
            place_name.setText(text);
            getWeather();
            getAstronomyInfo();
        }

    }

    private void getAstronomyInfo() {


        jsonPlaceHolder.getAstronomyDetails(KEY_, text, date).enqueue(new Callback<WeatherAstronomy>() {
            @Override
            public void onResponse(Call<WeatherAstronomy> call, Response<WeatherAstronomy> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code:" + response.code());
                }

                SunRise_phase.setText(response.body().getAstronomy().getAstro().getSunrise());
                sunset_phase.setText(response.body().getAstronomy().getAstro().getSunset());
                moonrise_phase.setText(response.body().getAstronomy().getAstro().getMoonrise());
                moonSet_Phase.setText(response.body().getAstronomy().getAstro().getMoonset());
                moon_phase_side.setText(response.body().getAstronomy().getAstro().getMoonPhase());
                moon_illumination_phase.setText(response.body().getAstronomy().getAstro().getMoonIllumination());


            }

            @Override
            public void onFailure(Call<WeatherAstronomy> call, Throwable t) {
                t.getMessage();

            }
        });
    }

    private void getWeather() {
        jsonPlaceHolder.getdata(KEY_, text).enqueue(new Callback<WeatherOne>() {
            @Override
            public void onResponse(Call<WeatherOne> call, Response<WeatherOne> response) {
                if (!response.isSuccessful()) {
                    textViewResult.setText("code: " + response.code());
                }

                DecimalFormat df = new DecimalFormat("#");
                assert response.body() != null;
                double dbl = response.body().getCurrent().getTempC();
                String contentNext = response.body().getCurrent().getCondition().getText();
                double wind_speed_in = response.body().getCurrent().getWindKph();
                double pressure = response.body().getCurrent().getPressureMb();
                Integer cloud_chance = response.body().getCurrent().getCloud();
                Integer humidity_in = response.body().getCurrent().getHumidity();
                double uv_index_in = response.body().getCurrent().getUv();
                double feels_like = response.body().getCurrent().getFeelslikeC();

                textViewResult.setText(String.valueOf(df.format(dbl)));

                condition_text.setText(contentNext);

                DecimalFormat dft = new DecimalFormat("#.#");

                String stringOne = " km/h";
                wind_speed.setText(dft.format(wind_speed_in).concat(stringOne));

                String stringFive = " hPa";
                pressure_in.setText(String.valueOf(dft.format(pressure)).concat(stringFive));

                String stringThree = " %";
                chance_of_rain.setText(String.valueOf(cloud_chance).concat(stringThree));

                String stringTwo = " %";
                humidity_value.setText(String.valueOf(humidity_in).concat(stringTwo));

                uv_index.setText(dft.format((uv_index_in)));

                String stringFour = " c";
                real_feel.setText(dft.format((feels_like)).concat(stringFour));


            }


            @Override
            public void onFailure(Call<WeatherOne> call, Throwable t) {
                textViewResult.setText(t.getLocalizedMessage());

            }
        });
    }


    @Override
    public void onBackPressed() {
        if (PressTime_is + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
        } else {
            Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        }
        PressTime_is = System.currentTimeMillis();

    }

    /**
     * To display  local Day Month And Year
     */
    private void DayMonthYear() {
        date_day_month.setText(new SimpleDateFormat("dd, MMMM, yyyy", Locale.ENGLISH).format(new Date()));
    }

}
