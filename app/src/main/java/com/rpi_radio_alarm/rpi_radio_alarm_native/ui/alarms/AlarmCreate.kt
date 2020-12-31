package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.google.android.material.textfield.TextInputEditText
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmCreate : Fragment() {
    private lateinit var rpiSettings: RpiSettings
    private lateinit var root: View
    private lateinit var apiHelper: ApiHelper

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rpiSettings = RpiSettings(this.requireContext())
        apiHelper = ApiHelper(rpiSettings)

        root = inflater.inflate(R.layout.fragment_alarm_create, container, false)
        root.findViewById<Button>(R.id.saveAlarm).setOnClickListener { createAlarm() }
        return root
    }

    private fun createAlarm() {
        val alarm = Alarm()
        alarm.name = (root.findViewById<TextInputEditText>(R.id.alarmNameInput)).text.toString()
        alarm.hour =
            ((root.findViewById<TextInputEditText>(R.id.alarmHourInput)).text.toString()).toInt()
        alarm.min =
            ((root.findViewById<TextInputEditText>(R.id.alarmMinuteInput)).text.toString()).toInt()
        alarm.days =
            (root.findViewById<TextInputEditText>(R.id.alarmDaysInput)).text.toString().split(",")
                .toTypedArray()
        alarm.on = (root.findViewById<Switch>(R.id.alarmOnInput).isChecked)
        apiHelper.createAlarm(alarm).enqueue(
            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    val toast = Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    v.setBackgroundColor(Color.TRANSPARENT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<List<Alarm>>?,
                    response: Response<List<Alarm>>?
                ) {
                    val toast = Toast.makeText(context, "Created", Toast.LENGTH_SHORT)
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    v.setBackgroundColor(Color.TRANSPARENT)
                    toast.show()

                    val bundle = Bundle()
                    Navigation.findNavController(requireView())
                        .navigate(R.id.action_alarmCreate_to_navigation_alarm, bundle)
                }
            }
        )
    }

}