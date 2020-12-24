package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces

class Radio {
    var isPlaying:Boolean = false
    var switch:String = ""
    init {

    }

    fun setSwitchVal(){
        if (isPlaying) {
            switch = "on"
        } else{
            switch = "off"
        }
    }
}