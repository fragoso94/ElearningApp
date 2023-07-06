package com.my.first.elearningapp

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.home.HomeActivity
import com.my.first.elearningapp.notification.executeOrRequestPermission
import com.my.first.elearningapp.notification.simpleNotification
import com.my.first.elearningapp.signup.SignUpActivity
import com.my.first.elearningapp.update.UserData
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var etSignUp: TextView
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    lateinit var btnGoogle: ImageButton

    private lateinit var database: ElearningDatabase
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        // Habilita la característica de transiciones antes de la creación de la vista
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth //indicamos vamos a utilizar los método de autencación en este activity
        // Configura la animación de transición
        val transition = Explode()
        window.enterTransition = transition
        window.exitTransition = transition

        initUI()
        initListener()

    }

    private fun initUI(){
        btnLogin = findViewById(R.id.btn_login)
        etSignUp = findViewById(R.id.tv_signup)
        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
        btnGoogle = findViewById(R.id.imagebtn_google)
    }

    private fun initListener(){
        database = Room.databaseBuilder(
            application, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()

        btnLogin.setOnClickListener {
            if(etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty())
            {
                if(Helpers.isInternetAvailable(this))
                {
                    signInFirebase(etEmail.text.toString(), etPassword.text.toString())
                }
                else //Si no hay internet iniciamos sesión de manera offline
                {
                    CoroutineScope(Dispatchers.IO).launch {
                        val users = getAllUsersRoom()
                        val response = users.find { it.name == etEmail.text.toString() && it.password == etPassword.text.toString() }
                        if(response != null) //etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
                        {
                            val emailSingle = etEmail.text.toString()
                            UserData.userEmail = emailSingle
                            val PasslSingle = etPassword.text.toString()
                            UserData.userPass = PasslSingle

                            //navigation("home")

                            lifecycle.coroutineScope.launch(Dispatchers.IO){
                                val deferred = async { setStatusLogin(response.name, true) }
                                deferred.await()
                                navigation("home")
                            }
                        }
                        else
                        {
                            //showMessage("No existe datos en la DB.")
                            executeOrRequestPermission(this@MainActivity) {
                                simpleNotification(this@MainActivity)
                            }
                        }
                    }
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish() // garantiza que la actividad actual se cierre correctamente
        }

        btnGoogle.setOnClickListener {

        }
    }

    private fun navigation(route: String) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    private fun showMessage(message: String){
        runOnUiThread {
            val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
            toast.show()
        }
    }

    private suspend fun setStatusLogin(user: String, status: Boolean) {
        database.getUserDao().updateStatusUser(user, status)
    }

    private suspend fun getAllUsersRoom() : List<UserEntity>{
        val users = database.getUserDao().getAllUser()
        return users
    }

    private fun signInFirebase(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    Log.d(Helpers.TAG, "signInWithEmail:success | user:${user}")
                    navigation("home")
                }
                else {
                    Log.w(Helpers.TAG, "signInWithEmail:failure->${task.exception?.message}") //task.exception
                    showMessage("signInWithEmail:failure->${task.exception?.message}")
                }
            }
    }

}