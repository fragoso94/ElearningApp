package com.my.first.elearningapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.my.first.elearningapp.bl.services.AutenticationServices
import com.my.first.elearningapp.home.HomeActivity
import com.my.first.elearningapp.signup.SignUpActivity

class MainActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var etSignUp: TextView
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
        etSignUp = findViewById(R.id.tv_signup)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }

    private fun initListener(){
        btnLogin.setOnClickListener {
            if(etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty())
            {
                val services = AutenticationServices()
                val response = services.iniciarSesion(etEmail.text.toString(), etPassword.text.toString())
                if (response.exito)
                {
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                }
                else
                {
                    showMessage(response.mensaje)
                }
            }
            else
            {
                showMessage("Los campos no pueden estar vacíos.")
            }
        }

        etSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showMessage(message: String){
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        toast.show()
    }
}