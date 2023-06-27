package com.my.first.elearningapp.update
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.google.android.material.button.MaterialButton
import com.my.first.elearningapp.MainActivity
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.databinding.ActivityMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UpdateActivity : AppCompatActivity() {
    private lateinit var btn_update_photo: MaterialButton
    private lateinit var btnUpdate: MaterialButton
    private lateinit var database: ElearningDatabase
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etMobile: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initUI()
        lifecycle.coroutineScope.launch(Dispatchers.IO) {
            initData2()
            lifecycle.coroutineScope.launch(Dispatchers.Main){
                btnUpdate.setOnClickListener {
                    val SinglePass = UserData.userPass.toString().trim()
                    val name = etName.text.toString().trim()
                    val email = etEmail.text.toString().trim()
                    val mobile = etMobile.text.toString().trim()

                    if (name.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty()) {
                        lifecycle.coroutineScope.launch(Dispatchers.Main) {
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

            btn_update_photo.setOnClickListener{
                startForResult.launch(Intent(MediaStore.ACTION_IMAGE_CAPTURE))
            }


        }


        }

private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
    result: ActivityResult ->
    if(result.resultCode == Activity.RESULT_OK)
    {
        val intent = result.data
        // Se obtiene imagen de la camara y se almacena en imageBitmap
        val imageBitmap = intent?.extras?.get("data") as Bitmap
        val imageView = findViewById<ImageView>(R.id.imageView_profile)
       // val imageView2 = findViewById<ImageView>(R.id.imageView_profile2)
        // se cambia la propiedad de la imagen a la obtenida en la foto
        imageView.setImageBitmap(imageBitmap)
     // imageView2.setImageBitmap(imageBitmap)
}

}








    private fun initUI(){
        database = Room.databaseBuilder(
            applicationContext, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME
        ).allowMainThreadQueries()
            .build()

        btnUpdate =findViewById(R.id.btn_update2)
        etName = findViewById(R.id.et_name_profile)
        etEmail = findViewById(R.id.et_email_address_profile)
        etMobile = findViewById(R.id.et_mobile_profile)
        btn_update_photo = findViewById(R.id.btn_update_photo)
    }



    private suspend fun initData2() {
        try{
            val SingleUser = UserData.userEmail.toString()
            val user = database.getUserDao().getUserId(SingleUser.toString())
            etName.setText(user.name)
            etEmail.setText(user.email)
            etMobile.setText(user.mobile)
        }
        catch (e: Exception)
        {
            runOnUiThread {
                Toast.makeText(applicationContext, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        // Asegúrate de finalizar la actividad actual para que no se pueda volver atrás
        finish()
    }
}






