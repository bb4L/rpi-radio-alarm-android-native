package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api

import android.util.Log
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//perr-nas.synology.me,4106,apikey,YfXgUhK9Qp5FTZUT,alsp
class ApiHelper(rpiSettings: RpiSettings) {
    private var _rpiSettings: RpiSettings? = null
    private var _retrofit: Retrofit? = null
    private var _rpiApiService: RpiApiService? = null

    init {
        _rpiSettings = rpiSettings
        _retrofit = Retrofit.Builder()
            .baseUrl("https://" + _rpiSettings!!.host + ":" + rpiSettings!!.port + "/" + rpiSettings!!.extraPath + "/")
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
        _rpiApiService = _retrofit!!.create(RpiApiService::class.java)
    }

    private fun getHeaders(): Map<String, String> {
        var headerName = _rpiSettings?.customHeader
        var headerValue = _rpiSettings?.apiKey
        Log.v("retrofit", headerName)
        Log.v("retrofit", headerValue)
        if (headerName != null && headerValue != null) {
            return hashMapOf(headerName to headerValue)
        }
        return hashMapOf()

    }

    fun getAlarms(): Call<List<Alarm>> {
        return _rpiApiService!!.getAlarms(getHeaders())
    }

    fun getAlarm(idx: String): Call<Alarm> {
        return _rpiApiService!!.getAlarm(getHeaders(), idx)
    }


    fun switchAlarm(i: Int, turnOff: Boolean) {
        // TODO: implement
    }

    fun changeAlarm(alarm: Alarm): Call<Alarm> {
        return _rpiApiService!!.saveAlarm(getHeaders(), alarm.idx!!.toString(), alarm)
    }

    fun deleteAlarm(idx: String): Call<List<Alarm>> {
        return _rpiApiService!!.deleteAlarm(getHeaders(), idx)
    }

    fun getRadio() {
        // TODO: implement
    }

    fun startRadio() {
        // TODO: implement
    }

    fun stopRadio() {
        // TODO: implement
    }

    fun createAlarm(alarm: Alarm) {
        // TODO: implement
    }

}