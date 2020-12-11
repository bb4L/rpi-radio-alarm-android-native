package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api

import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Radio
import retrofit2.Call
import retrofit2.http.*

interface RpiApiService {

    @POST("alarm")
    fun createAlarm(
        @HeaderMap headers: Map<String, String>, @Body alarm: Alarm
    ): Call<Alarm>

    @PUT("alarm/{idx}")
    fun saveAlarm(
        @HeaderMap headers: Map<String, String>, @Path("idx") idx: String, @Body alarm: Alarm
    ): Call<Alarm>


    @DELETE("alarm/{idx}")
    fun deleteAlarm(
        @HeaderMap headers: Map<String, String>, @Path("idx") idx: String
    ): Call<List<Alarm>>

    @GET("alarm/{idx}")
    fun getAlarm(
        @HeaderMap headers: Map<String, String>,
        @Path("idx") idx: String
    ): Call<Alarm>

    @GET("alarm")
    fun getAlarms(@HeaderMap headers: Map<String, String>): Call<List<Alarm>>

    @GET("radio")
    fun getRadio(@HeaderMap headers: Map<String, String>): Call<Radio>

    @GET("radio")
    fun changeRadio(@HeaderMap headers: Map<String, String>, @Body radio: Radio): Call<Radio>
}