package com.gq.foundation.retrofit.apis

import com.gq.foundation.retrofit.RetrofitCall
import com.lk.retrofit.compiler.annotations.RetrofitApi
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

@RetrofitApi
interface AppApi {

    /**
     * 请求Config
     */
    @FormUrlEncoded
    @POST("/abc")
    fun getConfig(
        @Field("field1") field: String,
        @Field("params") params: Int
    ): RetrofitCall<Result<String>>

}