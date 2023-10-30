package com.android_tatry.gps_tracer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.android_tatry.gps_tracer.databinding.ActivityMainBinding
import com.android_tatry.gps_tracer.fragments.HomeFragment
import com.android_tatry.gps_tracer.fragments.SettingsFragment
import com.android_tatry.gps_tracer.fragments.TracksFragment
import com.android_tatry.gps_tracer.utils.openFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBottomNavClick()
        openFragment(HomeFragment.newInstance())
    }

    private fun onBottomNavClick() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.id_home -> openFragment(HomeFragment.newInstance())
                R.id.id_tracks -> openFragment(TracksFragment.newInstance())
                R.id.id_settings -> openFragment(SettingsFragment())
            }
            true
        }
    }
}