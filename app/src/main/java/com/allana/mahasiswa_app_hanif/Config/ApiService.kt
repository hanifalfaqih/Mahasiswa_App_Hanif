package com.allana.mahasiswa_app_hanif.Config

import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // getData
    @GET("getData.php")
    fun getData(): Call<ResponseGetData>

    // getDataById
    @GET("getData.php")
    fun getDataById(@Query("id") id: String): Call<ResponseGetData>

    // insertData
    @FormUrlEncoded
    @POST("insertData.php")
    fun insertData(
        @Field("mahasiswa_nama") mahasiswa_nama: String,
        @Field("mahasiswa_nohp") mahasiswa_nohp: String,
        @Field("mahasiswa_alamat") mahasiswa_alamat: String
    ): Call<ResponseAction>

    // updateData
    @FormUrlEncoded
    @POST("updateData.php")
    fun updateData(
        @Field("id_mahasiswa") id_mahasiswa: String,
        @Field("mahasiswa_nama") mahasiswa_nama: String,
        @Field("mahasiswa_nohp") mahasiswa_nohp: String,
        @Field("mahasiswa_alamat") mahasiswa_alamat: String
    ): Call<ResponseAction>

    // deleteData
    @FormUrlEncoded
    @POST("deleteData.php")
    fun deleteData(
        @Field("id_mahasiswa") id_mahasiswa: String): Call<ResponseAction>

}