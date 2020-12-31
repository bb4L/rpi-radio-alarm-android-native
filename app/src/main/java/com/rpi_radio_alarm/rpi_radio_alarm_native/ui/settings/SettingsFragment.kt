package com.rpi_radio_alarm.rpi_radio_alarm_native.ui.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.rpi_radio_alarm.rpi_radio_alarm_native.R
import com.rpi_radio_alarm.rpi_radio_alarm_native.helper.resouces.RpiSettings


class SettingsFragment : Fragment() {
    private var rpiSettings: RpiSettings? = null
    private var root: View? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_settings, container, false)

        rpiSettings = RpiSettings(this.requireContext())
        loadFromPreferences()

        root!!.findViewById<Button>(R.id.saveSettings).setOnClickListener { saveToPreferences() }
        root!!.findViewById<Button>(R.id.loadFromString).setOnClickListener { loadFromString() }
        root!!.findViewById<Button>(R.id.deleteStorage).setOnClickListener { deleteSettings() }

        return root
    }

    private fun loadFromPreferences() {
        (root!!.findViewById<TextInputEditText>(R.id.hostValue)).setText(rpiSettings!!.host)
        (root!!.findViewById<TextInputEditText>(R.id.portValue)).setText(rpiSettings!!.port)
        (root!!.findViewById<TextInputEditText>(R.id.extraHeaderValue)).setText(rpiSettings!!.customHeader)
        (root!!.findViewById<TextInputEditText>(R.id.apiKeyValue)).setText(rpiSettings!!.apiKey)
        (root!!.findViewById<TextInputEditText>(R.id.extraPathValue)).setText(rpiSettings!!.extraPath)
    }

    private fun saveToPreferences() {
        rpiSettings!!.host =
            (root!!.findViewById<TextInputEditText>(R.id.hostValue)).text!!.toString()
        rpiSettings!!.port =
            (root!!.findViewById<TextInputEditText>(R.id.portValue)).text!!.toString()
        rpiSettings!!.customHeader =
            (root!!.findViewById<TextInputEditText>(R.id.extraHeaderValue)).text!!.toString()
        rpiSettings!!.apiKey =
            (root!!.findViewById<TextInputEditText>(R.id.apiKeyValue)).text!!.toString()
        rpiSettings!!.extraPath =
            (root!!.findViewById<TextInputEditText>(R.id.extraPathValue)).text!!.toString()
    }

    private fun loadFromString() {
        val inputString =
            root!!.findViewById<TextInputEditText>(R.id.inputStringValue).text!!.toString()
                .split(",")
        if (inputString.size != 5) {
            return
        }
        rpiSettings!!.host = inputString[0]
        rpiSettings!!.port = inputString[1]
        rpiSettings!!.customHeader = inputString[2]
        rpiSettings!!.apiKey = inputString[3]
        rpiSettings!!.extraPath = inputString[4]
        loadFromPreferences()
    }

    private fun deleteSettings() {
        rpiSettings!!.host = ""
        rpiSettings!!.port = ""
        rpiSettings!!.customHeader = ""
        rpiSettings!!.apiKey = ""
        rpiSettings!!.extraPath = ""
        loadFromPreferences()
    }
}