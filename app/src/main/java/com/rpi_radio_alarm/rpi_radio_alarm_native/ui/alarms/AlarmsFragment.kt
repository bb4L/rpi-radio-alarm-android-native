package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.R.layout.fragment_alarm
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        manager = LinearLayoutManager(this.requireContext())
        myAdapter = AlarmsAdapter(arrayOf<Alarm>())
        rpiSettings = RpiSettings(this.requireContext())

    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_alarm_list, container, false)
        val ctx = this.context

        ApiHelper(rpiSettings!!).getAlarms().enqueue(


            object : Callback<List<Alarm>> {
                override fun onFailure(call: Call<List<Alarm>>?, t: Throwable?) {
                    Log.v("retrofit", "call failed")
                    Log.v("retrofit", t!!.stackTraceToString())
                    Toast.makeText(
                        container!!.context!!,
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
                    val toast = Toast.makeText(ctx, "Alarms retrieved", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0)
                    val v = toast.view.findViewById<View>(android.R.id.message) as TextView
                    v.setTextColor(Color.RED)
                    v.setBackgroundColor(Color.TRANSPARENT)
                    toast.show()

                    recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view).apply {
                        layoutManager = manager
                        adapter =  AlarmsAdapter(response.body()!!.toTypedArray())
                    }
                    //ITEMS.addAll(response!!.body()!!)
                }
            }
        )
//        //val alarms = ApiHelper(rpiSettings!!).getAlarms()
//
//        // Set the adapter
//        if (view is RecyclerView) {
//            with(view) {
//                layoutManager = when {
//                    columnCount <= 1 -> LinearLayoutManager(context)
//                    else -> GridLayoutManager(context, columnCount)
//                }
//                var items = AlarmsContent.create(rpiSettings!!).ITEMS
//                Log.v("ITEMS ", items.toString())
//                adapter = MyAlarmsRecyclerViewAdapter(items)
//                //items.a
//            }
//        }
        return view
    }

    private fun getAlarms(){
//        val call =  ApiHelper(rpiSettings!!).getAlarms()
//        call.enqueue(object : Callback<List<Alarm>> {
//            override fun onResponse(call: Call<List<Alarm>?>, response: Response<List<Alarm>?>) {
//                Log.v("get response ", response.message())
//                val alarms: List<Alarm>? = response.body()
//
//                if (rootView!! is RecyclerView) {
//                    with(rootView) {
//                        layoutManager = when {
//                            columnCount <= 1 -> LinearLayoutManager(context)
//                            else -> GridLayoutManager(context, columnCount)
//                        }
//                        //var items = AlarmsContent.create(rpiSettings!!).ITEMS
//                        Log.v("ITEMS ", alarms.toString())
//                        val adapter = MyAlarmsRecyclerViewAdapter(alarms!!)
//                        rootView.layoutManager = layoutManager
//                        //items.a
//                    }
//                }
//
//
//                layoutManager = LinearLayoutManager(activity)
//                //val recycler = rootView.findViewById(R.id.rvRendicion)
//                //recycler.layoutManager = layoutManager
//                //val rvAdapter = RvRendicionAdapter(atenciones)
//                //recycler.adapter = rvAdapter
//            }
//
//            override fun onFailure(call: Call<List<Alarm>?>, t: Throwable) {
//                println("FALLOOOOO:  " + t.message) //HERE RETUNRS NULL
//            }
//        })
//
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            AlarmsFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}