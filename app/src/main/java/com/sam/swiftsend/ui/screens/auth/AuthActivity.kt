package com.sam.swiftsend.ui.screens.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import com.sam.swiftsend.R
import com.sam.swiftsend.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private  lateinit var binding: ActivityAuthBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_auth)

        val hostFragment = supportFragmentManager
            .findFragmentById(R.id.auth_host) as NavHostFragment

    }
}