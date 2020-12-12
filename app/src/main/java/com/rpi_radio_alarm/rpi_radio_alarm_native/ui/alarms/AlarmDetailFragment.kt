package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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

class AlarmDetailFragment : Fragment() {
    private lateinit var rpiSettings: RpiSettings
    private  var idx: Int? = null;
    private lateinit var root :View
    private lateinit var apiHelper: ApiHelper

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("Alarm detail", "Back Button Pressed")
        when (item.itemId) {
            android.R.id.home -> {
                Log.i("Alarm detail", "home on backpressed")
                activity?.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        idx =  requireArguments().getInt("alarmID")

        rpiSettings = RpiSettings(this.requireContext())
        apiHelper = ApiHelper(rpiSettings)

        root = inflater.inflate(R.layout.fragment_alarm_detail, container, false)
        root.findViewById<Button>(R.id.saveAlarm).setOnClickListener { saveAlarm() }

        getAlarm()
        return root
    }

    private fun setAlarmData(alarm:Alarm){
        (root.findViewById<TextInputEditText>(R.id.alarmNameInput)).setText(alarm.name)
        (root.findViewById<TextInputEditText>(R.id.alarmHourInput)).setText(alarm.hour!!.toString())
        (root.findViewById<TextInputEditText>(R.id.alarmMinuteInput)).setText(alarm.min!!.toString())
        (root.findViewById<TextInputEditText>(R.id.alarmDaysInput)).setText(alarm.days!!.joinToString(","))
        (root.findViewById<Switch>(R.id.alarmOnInput)).isChecked = alarm.on!!
        alarm.idx = idx
    }

    private fun getAlarm() {

        ApiHelper(rpiSettings).getAlarm(idx!!.toString()).enqueue(
            object : Callback<Alarm> {
                override fun onFailure(call: Call<Alarm>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                    Toast.makeText(
                        context,
                        String.format("FAILED"),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<Alarm>?,
                    response: Response<Alarm>?
                ) {
                    setAlarmData(response!!.body()!!)
                }
            }
        )
    }

    private fun saveAlarm(){
        var alarm = Alarm()
        alarm.idx = idx
        alarm.name = (root.findViewById<TextInputEditText>(R.id.alarmNameInput)).text.toString()
        alarm.hour = ((root.findViewById<TextInputEditText>(R.id.alarmHourInput)).text.toString()).toInt()
        alarm.min = ((root.findViewById<TextInputEditText>(R.id.alarmMinuteInput)).text.toString()).toInt()
        alarm.days = (root.findViewById<TextInputEditText>(R.id.alarmDaysInput)).text.toString().split(",").toTypedArray()
        alarm.on = (root.findViewById<Switch>(R.id.alarmOnInput).isChecked)
        apiHelper.changeAlarm(alarm).enqueue(
            object : Callback<Alarm> {
                override fun onFailure(call: Call<Alarm>?, t: Throwable?) {
                    val toast = Toast.makeText(context, "Save failed", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    v.setBackgroundColor(Color.TRANSPARENT)
                    toast.show()
                }

                override fun onResponse(
                    call: Call<Alarm>?,
                    response: Response<Alarm>?
                ) {
                    val toast = Toast.makeText(context, "Saved", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    v.setBackgroundColor(Color.TRANSPARENT)
                    toast.show()

                    setAlarmData(response!!.body()!!)
                }
            }
        )
    }

    private fun deleteAlarm(){
        val bundle = Bundle()

        apiHelper.deleteAlarm(idx.toString())

        Navigation.findNavController(requireView())
            .navigate(R.id.action_alarmDetailFragment_to_navigation_alarm, bundle)
    }

}