package com.android_tatry.gps_tracer.utils

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android_tatry.gps_tracer.R

fun AppCompatActivity.openFragment(fragment: Fragment) {
    if (supportFragmentManager.fragments.isNotEmpty()) {
        if (supportFragmentManager.fragments[0].javaClass == fragment.javaClass)
            return
    }

    supportFragmentManager.beginTransaction()
        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out)
        .replace(R.id.placeholder, fragment)
        .commit()
}

fun Fragment.openFragment(fragment: Fragment) {
    (activity as AppCompatActivity).supportFragmentManager
        .beginTransaction()
        .setCustomAnimations(androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out)
        .replace(R.id.placeholder, fragment)
        .commit()
}

fun Fragment.showToast(text: String) {
    Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.showToast(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}