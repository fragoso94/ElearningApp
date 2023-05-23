package com.my.first.elearningapp.update
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.my.first.elearningapp.MainActivity
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {

    private lateinit var btnUpdate: MaterialButton
    private lateinit var database: ElearningDatabase
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMobile: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        btnUpdate =findViewById(R.id.btn_update2)
        etName = findViewById(R.id.et_name_profile)
        etEmail = findViewById(R.id.et_email_address_profile)
        etMobile = findViewById(R.id.et_mobile_profile)

        database = Room.databaseBuilder(
            applicationContext, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME
        ).allowMainThreadQueries()
            .build()

        GlobalScope.launch(Dispatchers.Main) {
            initData2()

        }

       btnUpdate.setOnClickListener {
            val SinglePass = UserData.userPass.toString().trim()
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val mobile = etMobile.text.toString().trim()


            if (name.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty()) {
                GlobalScope.launch(Dispatchers.Main) {
                    val user1 = database.getUserDao().getUserId(UserData.userEmail)
                    user1.name = name
                    user1.email = email
                    user1.mobile = mobile
                    database.getUserDao().update(user1)
                    Toast.makeText(applicationContext, "Data updated successfully", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(applicationContext, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
            }

            }


        }


    private suspend fun initData2() {
        val SingleUser = UserData.userEmail.toString()
        val user = database.getUserDao().getUserId(SingleUser.toString())
        etName.hint = user.name
        etEmail.hint = user.email
        etMobile.hint = user.mobile
    }
    }






