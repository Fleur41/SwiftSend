package com.sam.swiftsend.ui.fragments.auth_getting_started

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.sam.swiftsend.R
import com.sam.swiftsend.databinding.FragmentGetStartedBinding

class FragmentGetStarted : Fragment() {
    private lateinit var binding: FragmentGetStartedBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetStartedBinding.inflate(inflater, container, false)

        binding.btnLogin.setOnClickListener{
            binding.root.findNavController().navigate(R.id.action_fragmentGetStarted_to_fragmentLogin)
        }
        return binding.root
    }
}