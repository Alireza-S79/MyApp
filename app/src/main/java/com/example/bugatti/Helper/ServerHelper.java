package com.example.bugatti.Helper;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class ServerHelper {

    private static Retrofit retrofit;

    public static void start() {

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                Request newRequest;
                newRequest = request.newBuilder().build();
                return chain.proceed(newRequest);
            }
        });
        OkHttpClient okHttpClient = okHttpClientBuilder.build();
        Gson gson = new GsonBuilder().setLenient().create();
        retrofit = new Retrofit.Builder()
                .baseUrl("https://hamyarandroid.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public interface connect {

        @GET("api?t=lab&p=bugatti")
        Call<JsonObject> bugatti();

    }

    public static connect connect() {

        return retrofit.create(connect.class);
    }

}
