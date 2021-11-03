package com.example.board;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitObj { // retrofit 객체를 한번만 생성해서 계속 돌려쓰기(이전에는 화면마다 만들었음)
    public static Retrofit rfo;
    private RetrofitObj(){}
    public static Retrofit getInstance(){
        if(rfo==null){
            rfo = new Retrofit.Builder()
                    .baseUrl("http://172.30.1.17:8090/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return rfo;
    }
}
