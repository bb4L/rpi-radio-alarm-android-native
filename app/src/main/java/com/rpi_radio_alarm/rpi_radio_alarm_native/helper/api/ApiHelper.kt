package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api

import android.util.Log
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

//perr-nas.synology.me,4106,apikey,YfXgUhK9Qp5FTZUT,alsp
class ApiHelper(rpiSettings: RpiSettings) {
    private var _rpiSettings: RpiSettings? = null
    private var _retrofit: Retrofit? = null
    private var _rpiApiService: RpiApiService? = null

    init {
        _rpiSettings = rpiSettings
        _retrofit = Retrofit.Builder().baseUrl("https://"+_rpiSettings!!.host + ":" + rpiSettings!!.port + "/" + rpiSettings!!.extraPath+"/").addConverterFactory(
            GsonConverterFactory.create()).build()
        _rpiApiService = _retrofit!!.create(RpiApiService::class.java)
    }

    private fun getHeaders(): Map<String,String>{
        var headerName = _rpiSettings?.customHeader
        var headerValue = _rpiSettings?.apiKey
        Log.v("retrofit", headerName)
        Log.v("retrofit", headerValue)
        if (headerName != null && headerValue != null){
            return hashMapOf(headerName to headerValue)
        }
        return  hashMapOf()

    }

    fun getAlarms(): Call<List<Alarm>> {
        return _rpiApiService!!.getAlarms(getHeaders())
        /*    .enqueue(
            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                }

                override fun onResponse(call: Call<List<Alarm>>?, response: Response<List<Alarm>>?) {
                    Log.v("retrofit", response!!.toString())
                }
                }

        )
        return ArrayList()
        //return k.body()!!

         */
    }


    fun switchAlarm(i: Int, turnOff: Boolean){
        // TODO: implement
    }

    fun changeAlarm(alarm: Alarm){
        // TODO: http put alarm as json on the correct endpoint
    }

    fun deleteAlarm(alarm: Alarm){
        // TODO: implement
    }

    fun getRadio(){
        // TODO: implement
    }

    fun startRadio(){
        // TODO: implement
    }

    fun stopRadio(){
        // TODO: implement
    }

    fun createAlarm(alarm: Alarm){
        // TODO: implement
    }

}