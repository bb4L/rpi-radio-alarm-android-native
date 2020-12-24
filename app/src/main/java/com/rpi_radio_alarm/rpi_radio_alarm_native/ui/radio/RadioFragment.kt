package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.radio

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.Toast
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Radio
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RadioFragment : Fragment() {
    private lateinit var rpiSettings: RpiSettings
    private lateinit var radio: Radio
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_radio, container, false)
        rpiSettings = RpiSettings(this.requireContext())
        getRadio()
        root.findViewById<Switch>(R.id.radioSwitch).setOnClickListener { setRadio() }

        return root
    }

    private fun getRadio() {
        ApiHelper(rpiSettings).getRadio().enqueue(
            object : Callback<Radio> {
                override fun onFailure(call: Call<Radio>?, t: Throwable?) {
                    Toast.makeText(
                        context,
                        String.format("Failed to get radio"),
                        Toast.LENGTH_SHORT
                    ).show();
                }

                override fun onResponse(
                    call: Call<Radio>?,
                    response: Response<Radio>?
                ) {
                    radio = response!!.body()!!
                    root.findViewById<Switch>(R.id.radioSwitch).isChecked = radio.isPlaying
                }

            })

    }

    private fun setRadio() {
        radio.isPlaying = !radio.isPlaying
        ApiHelper(rpiSettings).changeRadio(radio).enqueue(

            object : Callback<Radio> {
                override fun onFailure(call: Call<Radio>?, t: Throwable?) {
                    radio.isPlaying = !radio.isPlaying
                    root.findViewById<Switch>(R.id.radioSwitch).isChecked = radio.isPlaying
                    Toast.makeText(context, String.format("FAILED"), Toast.LENGTH_SHORT).show();
                }

                override fun onResponse(
                    call: Call<Radio>?,
                    response: Response<Radio>?
                ) {
                }
            })
    }

}