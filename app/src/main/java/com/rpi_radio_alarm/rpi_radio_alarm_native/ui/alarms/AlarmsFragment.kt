package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A fragment representing a list of Items.
 */
class AlarmsFragment : Fragment() {
    private var rpiSettings: RpiSettings? = null
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var myAdapter: RecyclerView.Adapter<*>
    private lateinit var recyclerView: RecyclerView
    private lateinit var internalView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        manager = LinearLayoutManager(this.requireContext())
        myAdapter = AlarmsAdapter(arrayOf<Alarm>()) { alarm: Alarm ->
            alarmClicked(
                alarm
            )
        }
        rpiSettings = RpiSettings(this.requireContext())
        val view = inflater.inflate(R.layout.fragment_alarm_list, container, false)
        internalView = view

        recyclerView = internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = manager
        }

        getAlarms()
        return view
    }

    private fun getAlarms() {

        ApiHelper(rpiSettings!!).getAlarms().enqueue(


            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                    Toast.makeText(
                        context,
                        String.format("FAILED"),
                        Toast.LENGTH_SHORT
                    ).show();

                }

                override fun onResponse(
                    call: Call<List<Alarm>>?,
                    response: Response<List<Alarm>>?
                ) {
                    Log.v("retrofit", response!!.toString())
                    Log.v("retrofit", response!!.body()!!.toString())
                    //val toast = Toast.makeText(context, "Alarms retrieved", Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.CENTER, 0, 0)
                    //val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    //v.setTextColor(Color.RED)
                    //v.setBackgroundColor(Color.TRANSPARENT)
                    //toast.show()
                    recyclerView =
                        internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
                            // layoutManager = manager
                            adapter =
                                AlarmsAdapter(response.body()!!.toTypedArray()) { alarm: Alarm ->
                                    alarmClicked(
                                        alarm
                                    )
                                }
                        }

                }
            }
        )

    }

    private fun alarmClicked(alarm: Alarm) {
        val bundle = Bundle()
        bundle.putInt("alarmID", alarm.idx!!)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_navigation_alarm_to_alarmDetailFragment, bundle)
    }
}