package com.my.first.elearningapp.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.my.first.elearningapp.MainActivity
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.database.utilities.Utility
import com.my.first.elearningapp.model.SimpleResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var btnSignup: MaterialButton
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMobile: EditText
    private lateinit var etCreatePassword: EditText
    private lateinit var etConfirmPassword: EditText
    private lateinit var tvLogin: TextView

    private lateinit var database: ElearningDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = Firebase.auth //indicamos vamos a utilizar los método de autencación en este activity
        initUI()
        initListener()
    }

    private fun initUI(){
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email_address)
        etMobile = findViewById(R.id.et_mobile)
        etCreatePassword = findViewById(R.id.et_create_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnSignup = findViewById(R.id.btn_signup)
        tvLogin = findViewById(R.id.tv_login)
    }

    private fun initListener(){
        database = Room.databaseBuilder(
            application, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()

        btnSignup.setOnClickListener {
            val res = validationForm()
            if (res.exito){
                if(etCreatePassword.text.toString() == etConfirmPassword.text.toString()){
                    GlobalScope.launch {
                        val data = UserEntity(
                            name = etName.text.toString(),
                            email = etEmail.text.toString(),
                            mobile = etMobile.text.toString(),
                            password = etConfirmPassword.text.toString(),
                            status = false
                        )
                        saveUsers(data)
                    }
                }
                else{
                    showMessage("La contraseña no coinciden.")
                }
            }
            else{
                showMessage(res.mensaje)
            }
        }
        tvLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun navigationHome() {
        //val intent = Intent(this, HomeActivity::class.java) // home
        val intent = Intent(this, MainActivity::class.java) // Login
        startActivity(intent)

        finish() // garantiza que la actividad actual se cierre correctamente
    }

    private fun validationForm(): SimpleResponse{
        val response = SimpleResponse()

        if (etName.text.isEmpty()){
            response.exito = false
            response.mensaje = "El nombre es requerido."
        }
        else if (etEmail.text.isEmpty()){
            response.exito = false
            response.mensaje = "El correo es requerido."
        }
        else if (etMobile.text.isEmpty()){
            response.exito = false
            response.mensaje = "El número de telefono es requerido."
        }
        else if (etCreatePassword.text.isEmpty()){
            response.exito = false
            response.mensaje = "La contraseña es requerido."
        }
        else if (etConfirmPassword.text.isEmpty()){
            response.exito = false
            response.mensaje = "La confirmación de contraseña es requerido."
        }
        else{
            response.exito = true
            response.mensaje = ""
        }

        return response
    }

    private fun showMessage(message: String){
        runOnUiThread {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private suspend fun saveUsers(data: UserEntity){
        var response = database.getUserDao().getUserId(data.name.trim())
        if(response == null)
        {
            database.getUserDao().insertAll(data)
            createAccountFirebase(data.email, data.password)
        }
        else{
            Log.d("dfragoso94","El dato ya existe en la DB")
        }
    }

    private fun createAccountFirebase(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){ task ->
                if (task.isSuccessful){
                    val user = auth.currentUser
                    Log.d(Helpers.TAG,"createAccount: success | User${user}")
                    showMessage("createAccount: success | User${user}")
                    navigationHome()
                }
                else{
                    Log.d(Helpers.TAG,"createAccount: failed->${task.exception?.message}")
                    showMessage("createAccount: failed->${task.exception?.message}")
                }
            }
    }

}