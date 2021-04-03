package com.jeanporcher.tpjeanporcher.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.jeanporcher.tpjeanporcher.R

class LoginFragment: Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginButton: Button = view.findViewById(R.id.login_button)
        loginButton.setOnClickListener(){

        }
    }
}