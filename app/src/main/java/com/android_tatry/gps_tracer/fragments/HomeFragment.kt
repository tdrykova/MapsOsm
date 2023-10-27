package com.android_tatry.gps_tracer.fragments

import android.Manifest
import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.android_tatry.gps_tracer.AppPermissions
import com.android_tatry.gps_tracer.databinding.FragmentHomeBinding
import com.android_tatry.gps_tracer.utils.checkPermission
import com.android_tatry.gps_tracer.utils.showToast
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.util.*

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pLauncher: ActivityResultLauncher<Array<String>>
    private lateinit var permission: AppPermissions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        settingsOsm()
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerPermissions()
        checkLocPermission()
    }

    private fun settingsOsm() {
        Configuration.getInstance().load(
            activity as AppCompatActivity,
            activity?.getSharedPreferences("osm_pref", Context.MODE_PRIVATE)
        )
        Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID
    }

    private fun initOsm() = with(binding) {
        map.controller.setZoom(20.0)
        val mLocProvider = GpsMyLocationProvider(activity)
        val mLocOverlay = MyLocationNewOverlay(mLocProvider, map)
        mLocOverlay.enableMyLocation()
        mLocOverlay.enableFollowLocation()
        mLocOverlay.runOnFirstFix {
            map.overlays.clear()
            map.overlays.add(mLocOverlay)
        }
    }

    private fun registerPermissions() {
        pLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                initOsm()
            } else {
                showToast("You can't see your current location")
            }
        }
    }

    private fun checkLocPermission() {
        // v >= 10
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            checkPermissionAfter10()
        } else {
            checkPermissionBefore10()
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun checkPermissionAfter10() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION) &&
            checkPermission(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
        ) {
            initOsm()
        } else {
            pLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION) )
            pLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION)
            )
        }
    }

    private fun checkPermissionBefore10() {
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            initOsm()
        } else {
            pLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            )
        }
    }

    companion object {
        const val PERMISSION_CAMERA_REQUEST_CODE = 1
        @JvmStatic
        fun newInstance() = HomeFragment()
    }
}