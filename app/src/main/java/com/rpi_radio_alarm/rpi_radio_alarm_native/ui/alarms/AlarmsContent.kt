package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.content.ClipData
import android.util.Log
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.util.ArrayList

class AlarmsContent (rpiSettings:RpiSettings) {
    val ITEMS: MutableList<Alarm> by lazy { ArrayList() }

    init {

        //ITEMS.addAll(ApiHelper(rpiSettings).getAlarms().await())
        /*ApiHelper(rpiSettings).getAlarms().enqueue(
            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                }

                override fun onResponse(call: Call<List<Alarm>>?, response: Response<List<Alarm>>?) {
                    Log.v("retrofit", response!!.toString())
                    Log.v("retrofit", response!!.body()!!.toString())
                    ITEMS.addAll(response!!.body()!!)
                }
            }
        )*/
        //ITEMS.addAll(ApiHelper(rpiSettings).getAlarms())
    }

    companion object Factory {
        fun create(rpiSettings: RpiSettings): AlarmsContent = AlarmsContent(rpiSettings)
    }
}