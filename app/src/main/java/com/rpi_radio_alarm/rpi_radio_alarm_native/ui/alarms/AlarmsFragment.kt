package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.api.ApiHelper
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.ui.UIHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A fragment representing a list of Alarms.
 */
class AlarmsFragment : Fragment() {
    private lateinit var rpiSettings: RpiSettings
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
            arrayOf(),
            { alarm: Alarm -> alarmClicked(alarm) },
            { alarm: Alarm -> deleteAlarm(alarm) },
            { alarm: Alarm -> switchAlarm(alarm) })

        rpiSettings = RpiSettings(this.requireContext())

        val view = inflater.inflate(R.layout.fragment_alarm_list, container, false)

        internalView = view

        recyclerView = internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
            layoutManager = manager
        }
        internalView.findViewById<FloatingActionButton>(R.id.createAlarmFAB)
            .setOnClickListener { goToCreateAlarm() }
        return view
    }

    override fun onStart() {
        super.onStart()

        if(!rpiSettings.isComplete()){
            UIHelper.showToast( requireContext(),"Please complete settings")
            Navigation.findNavController(requireView())
                .navigate(R.id.action_navigation_alarm_to_navigation_settings)
        } else{
            getAlarms()
        }
    }

    private fun goToCreateAlarm() {
        Navigation.findNavController(requireView())
            .navigate(R.id.action_navigation_alarm_to_alarmCreate)
    }

    private fun getAlarms() {
        ApiHelper(rpiSettings).getAlarms().enqueue(

            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    UIHelper.showToast(context!!, "Failed")
                }

                override fun onResponse(
                    call: Call<List<Alarm>>?,
                    response: Response<List<Alarm>>?
                ) {
                    UIHelper.showToast(context!!, "Received Alarms")
                    recyclerView =
                        internalView.findViewById<RecyclerView>(R.id.recycler_view).apply {
                            val alarms = response!!.body()!!.toTypedArray()
                            alarms.forEachIndexed { idx, alarm -> alarm.idx = idx }
                            adapter =
                                AlarmsAdapter(alarms, { alarm: Alarm ->
                                    alarmClicked(
                                        alarm
                                    )
                                }, { alarm: Alarm ->
                                    deleteAlarm(
                                        alarm
                                    )
                                }, { alarm: Alarm ->
                                    switchAlarm(
                                        alarm
                                    )
                                })
                        }
                }
            })

    }

    private fun alarmClicked(alarm: Alarm) {
        val bundle = Bundle()
        bundle.putInt("alarmID", alarm.idx!!)
        Navigation.findNavController(requireView())
            .navigate(R.id.action_navigation_alarm_to_alarmDetailFragment, bundle)
    }

    private fun switchAlarm(alarm: Alarm) {
        alarm.on = !alarm.on!!
        ApiHelper(this.rpiSettings).changeAlarm(alarm).enqueue(
            object : Callback<Alarm> {
                override fun onFailure(call: Call<Alarm>?, t: Throwable?) {
                    UIHelper.showToast(context!!, "FAILED")
                }

                override fun onResponse(
                    call: Call<Alarm>?,
                    response: Response<Alarm>?
                ) {
                    UIHelper.showToast(context!!, "Alarm switched")
                    getAlarms()
                }
            }
        )
    }

    private fun deleteAlarm(alarm: Alarm) {
        val dialog: AlertDialog =
            AlertDialog.Builder(this.context).setMessage("Delete Alarm: " + alarm.name)
                .setPositiveButton(
                    android.R.string.ok
                ) { dialog, _ ->
                    ApiHelper(rpiSettings).deleteAlarm(alarm.idx!!.toString()).enqueue(
                        object : Callback<List<Alarm>> {
                            override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                                UIHelper.showToast(context!!, "Failed")
                            }

                            override fun onResponse(
                                call: Call<List<Alarm>>?,
                                response: Response<List<Alarm>>?
                            ) {
                                UIHelper.showToast(context!!, "Alarm deleted")
                                dialog.dismiss()
                                getAlarms()
                            }
                        }
                    )
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