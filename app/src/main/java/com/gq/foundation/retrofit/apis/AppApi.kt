package com.gq.foundation.retrofit.apis

import com.gq.foundation.retrofit.RetrofitCall
import com.lk.retrofit.compiler.annotations.RetrofitApi
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface AppApi {

    @RetrofitApi()
    @FormUrlEncoded
    @POST("/abc")
    fun getConfig(): RetrofitCall<Result<String>>

}