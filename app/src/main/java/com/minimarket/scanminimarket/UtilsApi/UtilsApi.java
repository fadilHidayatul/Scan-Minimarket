package com.minimarket.scanminimarket.UtilsApi;

import retrofit2.Retrofit;

public class UtilsApi {
    public static final String baseURL = "http://192.168.100.183/minimarket/api/";
    public static final String imgURL = "http://192.168.100.183/minimarket/assets/img/barang/";

    public static ApiInterface getApiService(){
        return RetrofitClient.getRetrofit(baseURL).create(ApiInterface.class);
    }
}
