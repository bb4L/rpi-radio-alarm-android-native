package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.os.Parcel
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm

class AlarmsAdapter(private val myDataSet : Array<Alarm>) : RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

        class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
            fun bind(alarm: Alarm){
                val tv = view.findViewById<TextView>(R.id.alarm_content)
                tv.text = alarm.name
            }
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmsAdapter.ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.fragment_alarm, parent,false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: AlarmsAdapter.ViewHolder, position: Int) {
        holder.bind(myDataSet[position])
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

}