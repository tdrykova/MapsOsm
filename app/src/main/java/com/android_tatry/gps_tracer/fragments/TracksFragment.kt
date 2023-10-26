package com.android_tatry.gps_tracer.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.android_tatry.gps_tracer.databinding.FragmentTracksBinding

class TracksFragment : Fragment() {
    private lateinit var binding: FragmentTracksBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTracksBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = TracksFragment()
    }
}