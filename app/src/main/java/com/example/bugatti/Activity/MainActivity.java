package com.example.bugatti.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bugatti.Helper.ServerHelper;
import com.example.bugatti.R;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    ImageView image;
    TextView title, pro1, pro2, pro3, intro;
    RelativeLayout prograssbar, error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        image = findViewById(R.id.img);
        title = findViewById(R.id.title);
        pro1 = findViewById(R.id.pro1);
        pro2 = findViewById(R.id.pro2);
        pro3 = findViewById(R.id.pro3);
        intro = findViewById(R.id.intro);
        prograssbar = findViewById(R.id.prograssBar);
        error = findViewById(R.id.error);

        request();
    }

    private void request() {

        prograssbar.setVisibility(View.VISIBLE);
        error.setVisibility(View.GONE);
        ServerHelper.connect().bugatti().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {


                if (response.isSuccessful() && response.body().get("ok").getAsBoolean()) {

                    Log.e("myApp", "پاسخ دریافت شد");
                    Toast.makeText(MainActivity.this, "پاسخ دریافت شد", Toast.LENGTH_SHORT).show();
                    setView(response.body());
                    prograssbar.setVisibility(View.GONE);
                    error.setVisibility(View.GONE);
                } else {

                    Toast.makeText(MainActivity.this, "خظایی در سرور رخ داد", Toast.LENGTH_SHORT).show();
                    Toast.makeText(MainActivity.this, response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                    error.setVisibility(View.VISIBLE);
                    prograssbar.setVisibility(View.GONE);

                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

                Log.e("myApp", "اینترنت خود را بررسی کنید");
               // Toast.makeText(MainActivity.this, "اینترنت خود را بررسی کنید", Toast.LENGTH_SHORT).show();
                error.setVisibility(View.VISIBLE);
                prograssbar.setVisibility(View.GONE);

            }
        });

    }

    private void setView(JsonObject body) {

        Picasso.get()
                .load(body.get("image").getAsString())
                .into(image);
        //Log.e("myApp", body.get("nameEn").getAsString());
        title.setText(body.get("nameEn").getAsString() + " " + body.get("nameFa").getAsString());
        pro1.setText(body.get("property").getAsJsonArray().get(0).getAsString());
        pro2.setText(body.get("property").getAsJsonArray().get(1).getAsString());
        pro3.setText(body.get("property").getAsJsonArray().get(2).getAsString());
        intro.setText(body.get("intro").getAsString());
    }
}