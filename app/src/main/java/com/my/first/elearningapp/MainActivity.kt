package com.my.first.elearningapp

import android.content.Intent
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.home.HomeActivity
import com.my.first.elearningapp.signup.SignUpActivity
import com.my.first.elearningapp.update.UserData
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    lateinit var btnLogin: Button
    lateinit var etSignUp: TextView
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText
    private lateinit var database: ElearningDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        // Habilita la característica de transiciones antes de la creación de la vista
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                CoroutineScope(Dispatchers.IO).launch {
                    val users = getAllUsersRoom()
                    //Log.d("dfragoso94",users.toString())
                    val response = users.find { it.name == etEmail.text.toString() && it.password == etPassword.text.toString() }
                    Log.d("dfragoso94",response.toString())
                    if(response != null) //etEmail.text.isNotEmpty() && etPassword.text.isNotEmpty()
                    {

                        val emailSingle = etEmail.text.toString()
                        UserData.userEmail = emailSingle
                        val PasslSingle = etPassword.text.toString()
                        UserData.userPass = PasslSingle

                        navigation("home")

//                        setStatusLogin(etEmail.text.trim().toString(), true)
//                        navigation("home")
                        lifecycle.coroutineScope.launch(Dispatchers.IO){
                            val deferred = async { setStatusLogin(response.name, true) }
                            deferred.await()
                            navigation("home")
                        }

                        /*val services = AutenticationServices()
                        val response = services.iniciarSesion(etEmail.text.toString(), etPassword.text.toString())
                        if (response.exito)
                        {
                            Navigation(HomeActivity::class.java)
                        }
                        else
                        {
                            showMessage(response.mensaje)
                        }*/


                    }
                    else
                    {
                        showMessage("No existe datos en la DB.")
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
        /*if(users.isNotEmpty()){
            users.forEach{
                Log.d("dfragoso94","Usuario: ${it.name} con ID: ${it.id}")
            }
        }
        else{
            Log.d("dfragoso94","No hay datos cargados en la DB")
        }*/
        return users
    }

    /*private suspend fun saveUsers(){
        val user1 = UserEntity(name = "Daniel", password = "123456")
        //val user2 = UserEntity(name = "Ana", password = "12345678")
        var response = database.getUserDao().getUserId(user1.name.trim())
        if(response == null)
        {
            database.getUserDao().insertAll(user1)
        }
        else{
            Log.d("dfragoso94","El dato ya existe en la DB")
        }
        //database.getUserDao().insertAll(user2)
    }*/
}