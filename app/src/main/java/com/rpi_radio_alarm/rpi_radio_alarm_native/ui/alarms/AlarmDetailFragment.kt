package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
// private const val ARG_PARAM1 = "param1"
// private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AlarmDetailFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AlarmDetailFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var rpiSettings: RpiSettings? = null
    private lateinit var idx:String
    private lateinit var root :View


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
    //        param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        idx =  requireArguments().getInt("alarmID").toString()
        Log.d("alarmDetail", idx)
        rpiSettings = RpiSettings(this.requireContext())
        root = inflater.inflate(R.layout.fragment_alarm_detail, container, false)
        getAlarm()
        return root
    }

    private fun setAlarmData(alarm:Alarm){
        (root!!.findViewById<TextInputEditText>(R.id.alarmNameInput)).setText(alarm.name)
        (root!!.findViewById<TextInputEditText>(R.id.alarmHourInput)).setText(alarm.hour!!.toString())
        (root!!.findViewById<TextInputEditText>(R.id.alarmMinuteInput)).setText(alarm.min!!.toString())
        (root!!.findViewById<TextInputEditText>(R.id.alarmDaysInput)).setText(alarm.days!!.joinToString(","))
        (root!!.findViewById<Switch>(R.id.alarmOnInput)).isChecked = alarm.on!!
    }

    private fun getAlarm() {

        ApiHelper(rpiSettings!!).getAlarm(idx).enqueue(
            object : Callback<Alarm> {
                override fun onFailure(call: Call<Alarm>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                    Toast.makeText(
                        context,
                        String.format("FAILED"),
                        Toast.LENGTH_SHORT
                    ).show();
                }

                override fun onResponse(
                    call: Call<Alarm>?,
                    response: Response<Alarm>?
                ) {
                    setAlarmData(response!!.body()!!)
                    //val toast = Toast.makeText(context, "Alarms retrieved", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.CENTER, 0, 0)
                    //val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    //v.setTextColor(Color.RED)
                    //v.setBackgroundColor(Color.TRANSPARENT)
                    //toast.show()
                    /*recyclerView =
                        internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
                            // layoutManager = manager
                            adapter =
                                AlarmsAdapter(response.body()!!.toTypedArray()) { alarm: Alarm ->
                                    alarmClicked(
                                        alarm
                                    )
                                }
                        }*/

                }
            }
        )
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AlarmDetailFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AlarmDetailFragment().apply {
                arguments = Bundle().apply {
      //              putString(ARG_PARAM1, param1)
       //             putString(ARG_PARAM2, param2)
                }
            }
    }

}