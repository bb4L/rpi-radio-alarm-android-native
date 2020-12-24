package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.ui

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast


class UIHelper {
    companion object {
        fun showToast(context: Context, text: String) {
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, 0, 0)
            val v = toast.view.findViewById<View>(android.R.id.message) as TextView
            v.setTextColor(Color.RED)
            v.setBackgroundColor(Color.TRANSPARENT)
            toast.show()
        }

    }
}