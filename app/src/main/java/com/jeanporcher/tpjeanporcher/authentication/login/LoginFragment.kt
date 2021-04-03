package com.jeanporcher.tpjeanporcher.authentication.login

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.jeanporcher.tpjeanporcher.R
import com.jeanporcher.tpjeanporcher.userinfo.UserInfoViewModel
import kotlinx.coroutines.launch

class LoginFragment: Fragment() {
    private val userInfoViewModel: UserInfoViewModel by viewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val loginButton: Button = view.findViewById(R.id.login_button)
        val email: EditText = view.findViewById(R.id.login_email)
        val password: EditText = view.findViewById(R.id.login_password)
        loginButton.setOnClickListener(){
            lifecycleScope.launch(){
                val loginform = LoginForm(email.text.toString() ,password.text.toString())
                userInfoViewModel.login(loginform)
                if (userInfoViewModel.logged){

                }else{
                    Toast.makeText(context, "text", Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}