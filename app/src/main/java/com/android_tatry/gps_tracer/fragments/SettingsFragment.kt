package com.android_tatry.gps_tracer.fragments

import android.graphics.Color
import android.os.Bundle
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.android_tatry.gps_tracer.R

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var timePref: Preference
    private lateinit var colorPref: Preference

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.home_preference, rootKey)
        init()
    }

    private fun init() {
        timePref = findPreference(TIME_PREF_KEY)!!
        colorPref = findPreference(COLOR_PREF_KEY)!!
        timePref.onPreferenceChangeListener = onChangeListener()
        colorPref.onPreferenceChangeListener = onChangeListener()

        initPrefs()
    }

    private fun onChangeListener(): Preference.OnPreferenceChangeListener {
        return Preference.OnPreferenceChangeListener{
            pref, value ->
            when (pref.key) {
                TIME_PREF_KEY -> onTimeChanged(value.toString())
                COLOR_PREF_KEY -> pref.icon?.setTint(Color.parseColor(value.toString()))
            }

            true
        }
    }

    private fun onTimeChanged(value: String) {
        val nameArray = resources.getStringArray(R.array.loc_time_update_name)
        val valueArray = resources.getStringArray(R.array.loc_time_update_value)
        val title = timePref.title.toString().substringBefore(":")
        timePref.title = "$title: ${nameArray[valueArray.indexOf(value)]}"
    }

    private fun initPrefs() {
        val pref = timePref.preferenceManager.sharedPreferences
        val nameArray = resources.getStringArray(R.array.loc_time_update_name)
        val valueArray = resources.getStringArray(R.array.loc_time_update_value)
        val title = timePref.title.toString().substringBefore(":")
        timePref.title = "$title: ${nameArray[valueArray
            .indexOf(pref?.getString(TIME_PREF_KEY,
            TIME_PREF_DEFAULT_VALUE))]}"

        val prefColorTmp = colorPref.preferenceManager.sharedPreferences
        val tracksColor = prefColorTmp?.getString(COLOR_PREF_KEY, COLOR_PREF_DEFAULT_VALUE)
        colorPref.icon?.setTint(Color.parseColor(tracksColor))
    }


    companion object {
        const val TIME_PREF_KEY = "update_time_key"
        const val COLOR_PREF_KEY = "color_key"
        const val TIME_PREF_DEFAULT_VALUE = "3000"
        const val COLOR_PREF_DEFAULT_VALUE = "#BA0000"
    }
}