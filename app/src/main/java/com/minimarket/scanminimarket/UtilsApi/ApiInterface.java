package com.minimarket.scanminimarket.UtilsApi;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("user/login.php")
    Call<ResponseBody>login(@Field("username") String username,
                            @Field("password") String password);

    @FormUrlEncoded
    @POST("barang/get_barang.php")
    Call<ResponseBody>getBarang(@Field("id_barang") String idbarang);
}
