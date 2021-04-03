package com.jeanporcher.tpjeanporcher.authentication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.jeanporcher.tpjeanporcher.R

class AuthenticationFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val authSignupBtn: Button = view.findViewById(R.id.auth_signup_button)
        val authLoginBtn: Button = view.findViewById(R.id.auth_login_button)

        authLoginBtn.setOnClickListener(){
            findNavController().navigate(R.id.action_authenticationFragment_to_loginFragment)
        }
        authSignupBtn.setOnClickListener(){
            findNavController().navigate(R.id.action_authenticationFragment_to_signupFragment)
        }
    }

}