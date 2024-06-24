package com.sam.swiftsend.ui.fragments.auth_login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sam.swiftsend.data.repository.AuthRepository
import com.sam.swiftsend.databinding.FragmentLoginBinding

class FragmentLogin : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var authRepository: AuthRepository
    private lateinit var vm: LoginVM

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        authRepository = AuthRepository(context = requireContext())
        vm = LoginVM(
            application = requireActivity().application,
            authRepository = authRepository
        )

        binding.vm = vm
        binding.lifecycleOwner = this


        return binding.root
    }
}