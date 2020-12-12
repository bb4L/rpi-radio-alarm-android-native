package com.rpi_radio_alarm.rpi_radio_alarm_native.helper.ui

import android.animation.Animator

import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast


class UIHelper {
    companion object {
        fun animateView(view: View, toVisibility: Int, toAlpha: Float, duration: Int) {
            val show = toVisibility == View.VISIBLE
            if (show) {
                view.alpha = 0F
            }
            view.visibility = View.VISIBLE
            view.animate()
                .setDuration(duration.toLong())
                .alpha((if (show) toAlpha else 0) as Float)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.visibility = toVisibility
                    }
                })
        }

        fun showToast(context:Context, text:String){
            val toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0)
            val v = toast.view.findViewById<View>(android.R.id.message) as TextView
            v.setTextColor(Color.RED)
            v.setBackgroundColor(Color.TRANSPARENT)
            toast.show()
        }

    }
}