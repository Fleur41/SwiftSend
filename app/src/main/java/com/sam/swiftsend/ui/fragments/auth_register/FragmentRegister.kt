package com.sam.swiftsend.ui.fragments.auth_register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.sam.swiftsend.data.repository.AuthRepository
import com.sam.swiftsend.databinding.FragmentRegisterBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FragmentRegister : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: RegisterVM
    private lateinit var authRepository: AuthRepository
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        authRepository = AuthRepository(requireContext())
        viewModel = RegisterVM(authRepository)

        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        showErrorMessage()

        return binding.root
    }

    private fun showErrorMessage(){
        lifecycleScope.launch {
            viewModel.errorMessage.collect() { value ->
               if (value.isNotBlank()){
                   Toast.makeText(requireContext(), value, Toast.LENGTH_SHORT).show()
               }
            }
        }


    }
}