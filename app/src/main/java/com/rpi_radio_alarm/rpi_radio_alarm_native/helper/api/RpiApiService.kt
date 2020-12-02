package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api

import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import java.util.*

public interface RpiApiService {
    @GET("alarm")
    public fun getAlarms(@HeaderMap headers:Map<String, String>): Call<List<Alarm>>
}