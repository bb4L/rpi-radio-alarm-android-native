package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces

import android.content.Context
import android.content.SharedPreferences


class RpiSettings(context:Context) {
    private val _settingsString = "rpi_preferences"
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(_settingsString,0)

    private val _host = "host"
    var host:String
        get() = sharedPreferences.getString(_host, "").toString()
        set(value) = sharedPreferences.edit().putString(_host, value).apply()

    private val _port = "port"
    var port:String
        get() = sharedPreferences.getString(_port, "").toString()
        set(value) = sharedPreferences.edit().putString(_port, value).apply()

    private val _extraPath = "extra_path"
    var extraPath: String
        get() = sharedPreferences.getString(_extraPath, "").toString()
        set(value) = sharedPreferences.edit().putString(_extraPath, value).apply()


    private val _customHeader = "custom_header"
    var customHeader: String
        get() = sharedPreferences.getString(_customHeader, "").toString()
        set(value) = sharedPreferences.edit().putString(_customHeader, value).apply()

    private val _apiKey = "api_key"
    var apiKey: String
        get() = sharedPreferences.getString(_apiKey, "").toString()
        set(value) = sharedPreferences.edit().putString(_apiKey, value).apply()

}