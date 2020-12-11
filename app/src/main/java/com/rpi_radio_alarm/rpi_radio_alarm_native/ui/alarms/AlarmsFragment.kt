package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.app.AlertDialog
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
        myAdapter = AlarmsAdapter(
            arrayOf<Alarm>(),
            { alarm: Alarm -> alarmClicked(alarm) },
            { alarm: Alarm -> deleteAlarm(alarm) })
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
                    Toast.makeText(context, String.format("FAILED"), Toast.LENGTH_SHORT).show();
                }

                override fun onResponse(
                    call: Call<List<Alarm>>?,
                    response: Response<List<Alarm>>?
                ) {
                    recyclerView =
                        internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
                            // layoutManager = manager
                            adapter =
                                AlarmsAdapter(response!!.body()!!.toTypedArray(), { alarm: Alarm ->
                                    alarmClicked(
                                        alarm
                                    )
                                }, { alarm: Alarm ->
                                    deleteAlarm(
                                        alarm
                                    )
                                })
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

    private fun deleteAlarm(alarm: Alarm) {
        val dialog: AlertDialog =
            AlertDialog.Builder(context).setMessage("Delete Alarm: " + alarm.name)
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ ->
                    ApiHelper(rpiSettings!!).deleteAlarm(alarm.idx.toString()).enqueue(
                        object : Callback<List<Alarm>> {
                            override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                                Toast.makeText(context, String.format("FAILED"), Toast.LENGTH_SHORT).show();
                            }

                            override fun onResponse(
                                call: Call<List<Alarm>>?,
                                response: Response<List<Alarm>>?
                            ) {
                                recyclerView =
                                    internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
                                        // layoutManager = manager
                                        adapter =
                                            AlarmsAdapter(response!!.body()!!.toTypedArray(), { alarm: Alarm ->
                                                alarmClicked(
                                                    alarm
                                                )
                                            }, { alarm: Alarm ->
                                                deleteAlarm(
                                                    alarm
                                                )
                                            })
                                    }

                            }
                        }
                    )
                    dialog.dismiss()
                    getAlarms()
                    //TODO: delete alarm and reload alarms
                }.setNegativeButton(
                    android.R.string.cancel
                ) { dialog, _ ->
                    dialog.dismiss()
                }.setOnCancelListener { dialog ->
                    dialog.dismiss()
                }.create()
        dialog.show()
    }
}