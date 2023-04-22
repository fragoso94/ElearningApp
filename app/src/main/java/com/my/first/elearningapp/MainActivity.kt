package com.my.first.elearningapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var editextSignUp: TextView
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initListener()
    }

    private fun initUI(){
        btnLogin = findViewById(R.id.btn_login)
        editextSignUp = findViewById(R.id.tv_signup)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    private fun initListener(){
        btnLogin.setOnClickListener {

        }
        editextSignUp.setOnClickListener {

        }
    }
}