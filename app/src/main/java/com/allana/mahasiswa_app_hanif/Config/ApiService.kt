package com.allana.mahasiswa_app_hanif.Config

import com.allana.mahasiswa_app_hanif.Model.action.ResponseAction
import com.allana.mahasiswa_app_hanif.Model.getData.ResponseGetData
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    // getData
    @GET("getData.php")
    fun getData(): Flowable<ResponseGetData>

    // getDataById
    @GET("getData.php")
    fun getDataById(@Query("id") id: String): Single<ResponseGetData>

    // insertData
    @FormUrlEncoded
    @POST("insertData.php")
    fun insertData(
        @Field("mahasiswa_nama") mahasiswa_nama: String,
        @Field("mahasiswa_nohp") mahasiswa_nohp: String,
        @Field("mahasiswa_alamat") mahasiswa_alamat: String
    ): Single<ResponseAction>

    // updateData
    @FormUrlEncoded
    @POST("updateData.php")
    fun updateData(
        @Field("id_mahasiswa") id_mahasiswa: String,
        @Field("mahasiswa_nama") mahasiswa_nama: String,
        @Field("mahasiswa_nohp") mahasiswa_nohp: String,
        @Field("mahasiswa_alamat") mahasiswa_alamat: String
    ): Single<ResponseAction>

    // deleteData
    @FormUrlEncoded
    @POST("deleteData.php")
    fun deleteData(
        @Field("id_mahasiswa") id_mahasiswa: String): Single<ResponseAction>

}