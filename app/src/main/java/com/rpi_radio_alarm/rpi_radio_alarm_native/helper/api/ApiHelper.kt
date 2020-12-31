package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api

import retrofit2.Call
import retrofit2.Retrofit
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm as Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Radio as Radio
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings as RpiSettings
import retrofit2.converter.gson.GsonConverterFactory as Retrofit2ConverterGsonConverterFactory

class ApiHelper(private val rpiSettings: RpiSettings) {
    private var _retrofit: Retrofit
    private var _rpiApiService: RpiApiService

    init {
        _retrofit = Retrofit.Builder()
            .baseUrl("https://" + rpiSettings.host + ":" + rpiSettings.port + "/" + rpiSettings.extraPath + "/")
            .addConverterFactory(
                Retrofit2ConverterGsonConverterFactory.create()
            ).build()
        _rpiApiService = _retrofit.create(RpiApiService::class.java)
    }

    private fun getHeaders(): Map<String, String> {
        val headerName = rpiSettings.customHeader
        val headerValue = rpiSettings.apiKey
        return hashMapOf(headerName to headerValue)
    }

    fun getAlarms(): Call<List<Alarm>> {
        return _rpiApiService.getAlarms(getHeaders())
    }

    fun getAlarm(idx: String): Call<Alarm> {
        return _rpiApiService.getAlarm(getHeaders(), idx)
    }

    fun changeAlarm(alarm: Alarm): Call<Alarm> {
        return _rpiApiService.saveAlarm(getHeaders(), alarm.idx!!.toString(), alarm)
    }

    fun deleteAlarm(idx: String): Call<List<Alarm>> {
        return _rpiApiService.deleteAlarm(getHeaders(), idx)
    }

    fun getRadio(): Call<Radio> {
        return _rpiApiService.getRadio(getHeaders())
    }

    fun changeRadio(radio: Radio): Call<Radio> {
        radio.setSwitchVal()
        return _rpiApiService.changeRadio(getHeaders(), radio)
    }

    fun createAlarm(alarm: Alarm): Call<List<Alarm>> {
        return _rpiApiService.createAlarm(getHeaders(), alarm)
    }

}