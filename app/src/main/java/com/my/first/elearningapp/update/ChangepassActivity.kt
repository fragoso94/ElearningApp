package com.my.first.elearningapp.update
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.my.first.elearningapp.MainActivity
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.home.HomeActivity
import com.my.first.elearningapp.model.SimpleResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ChangepassActivity : AppCompatActivity(){
    private lateinit var database: ElearningDatabase
    private lateinit var btnChangePass: MaterialButton
    private lateinit var etCreatePassword: EditText
    private lateinit var etConfirmPassword: EditText


    override  fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        etCreatePassword = findViewById(R.id.et_create_password)
        etConfirmPassword = findViewById(R.id.et_confirm_password)
        btnChangePass = findViewById(R.id.btn_update3)

        database = Room.databaseBuilder(
            applicationContext, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME
        )
            .build()
        btnChangePass.setOnClickListener {
            val password = etCreatePassword.text.toString().trim()
            val passAuth = etConfirmPassword.text.toString().trim()

            if (password.isNotEmpty() && passAuth.isNotEmpty() ) {
                GlobalScope.launch(Dispatchers.Main) {
                    val user1 = database.getUserDao().getUserId(UserData.userEmail)
                    user1.password = password

                    database.getUserDao().update(user1)
                    Toast.makeText(applicationContext, "Data updated successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        }


}
