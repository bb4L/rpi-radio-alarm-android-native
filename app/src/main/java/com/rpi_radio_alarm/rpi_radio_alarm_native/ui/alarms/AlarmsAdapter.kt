package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.alarms

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.Alarm

class AlarmsAdapter(
    private val myDataSet: Array<Alarm>,
    private val clickListItemListener: (Alarm) -> Unit,
    private val deleteListItemListener: (Alarm) -> Unit,
    private val switchAlarmListener: (Alarm) -> Unit
) : RecyclerView.Adapter<AlarmsAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(
            alarm: Alarm,
            clickListItemListener: (Alarm) -> Unit,
            deleteListItemListener: (Alarm) -> Unit,
            switchAlarmListener: (Alarm) -> Unit
        ) {
            val tv = view.findViewById<TextView>(R.id.alarm_name)
            tv.text = alarm.name
            val on = view.findViewById<Switch>(R.id.alarm_on)
            on.isChecked = alarm.on!!

            on.setOnClickListener {switchAlarmListener(alarm)}

            view.setOnClickListener { clickListItemListener(alarm) }

            val del = view.findViewById<ImageView>(R.id.alarmDeleteImageView)
            del.setOnClickListener { deleteListItemListener(alarm) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val vh = LayoutInflater.from(parent.context).inflate(R.layout.fragment_alarm, parent, false)
        return ViewHolder(vh)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(myDataSet[position], clickListItemListener, deleteListItemListener, switchAlarmListener)
    }

    override fun getItemCount(): Int {
        return myDataSet.size
    }

}