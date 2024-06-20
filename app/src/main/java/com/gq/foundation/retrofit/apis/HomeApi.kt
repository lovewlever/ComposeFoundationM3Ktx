package com.gq.foundation.retrofit.apis

import com.gq.foundation.retrofit.RetrofitCall
import com.lk.retrofit.compiler.annotations.BasicRetrofitApi
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import java.util.Date

@BasicRetrofitApi
interface HomeApi {

    /**
     * 请求Home所需要的数据
     */
    @FormUrlEncoded
    @POST("abc/aaa")
    fun queryHomePage(@Field("field1") field: String): RetrofitCall<Result<Date>>

}