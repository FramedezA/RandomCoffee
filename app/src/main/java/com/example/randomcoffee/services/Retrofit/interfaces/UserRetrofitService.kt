package com.example.randomcoffee.services.Retrofit.interfaces

import com.example.randomcoffee.data_structures.LoginRequest
import com.example.randomcoffee.data_structures.RegisterRequest
import com.example.randomcoffee.data_structures.UserForm
import com.example.randomcoffee.data_structures.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRetrofitService {
    @POST("register")
    fun regNewUser(
        @Body registerRequest: RegisterRequest
    ): Call<String>


    @POST("login")
    fun loginUser(
        @Body loginRequest: LoginRequest
    ): Call<UserInfo>

    @GET("set_form/{user_id}/{name}/{surname}/{age}/{sex}/{about}/{telegram}")
    fun setForm(
        @Path("user_id") userId:Int,
        @Path("name") name:String,
        @Path("surname") surname:String,
        @Path("age") age:Int,
        @Path("sex") sex:String,
        @Path("about") about:String,
        @Path("telegram") telegram:String,
    ):Call<String>

    @GET("add_meeting_agreement/{user_id}/{during}")
    fun sendMeetingAgreement(
        @Path("user_id")userId:Int,
        @Path("during")during:Int
    ):Call<String>

    @GET("check_my_meetings/{user_id}")
    fun checkMeetings(
        @Path("user_id")userId:Int
    ):Call<String>

    @GET("send_form_if_meeting/{user_id}")
    fun getCompanionFormByUserId(
        @Path("user_id")userId:Int
    ):Call<UserForm>

    @GET("user_meeting_history/{user_id}")
    fun getHistory(
        @Path("user_id")userId:Int
    ):Call<List<UserForm>>


}