package com.my.first.elearningapp.home

import com.my.first.elearningapp.R
import android.content.DialogInterface
import android.content.DialogInterface.OnShowListener
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.transition.Explode
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.coroutineScope
import androidx.room.Room
import com.amrdeveloper.lottiedialog.LottieDialog
import com.my.first.elearningapp.database.ElearningDatabase
import com.my.first.elearningapp.database.entities.ShoppingEntity
import com.my.first.elearningapp.database.entities.UserEntity
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.databinding.ActivityDetailBinding
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseClickListener
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class DetailActivity : AppCompatActivity(), CourseClickListener {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var database: ElearningDatabase

    override fun onClick(course: Course)
    {
        //Lógica al hacer click sobre el detalle del curso
        Log.d("dfragoso94", course.toString())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // Habilita la característica de transiciones antes de la creación de la vista
        window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)

        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // Configura la animación de transición
        val transition = Explode()
        window.enterTransition = transition

        initDatabase(view)
        val course: Course? = intent.getParcelableExtra(Helpers.COURSE_ITEM)
        val isViewBuy = intent.getBooleanExtra(Helpers.IS_VIEW_BUY, false)
        if(course != null) {
            //binding.courseImage.setImageResource(course.image)
            Picasso.get()
                .load(course.image)
                .into(binding.courseImage)
            binding.tvCourse.text = course.name
            binding.tvPrice.text = "$" + String.format("%.2f", course.price)
            binding.tvDuration.text = String.format("%.2f", course.duration) + " horas"
            binding.rbCalification.rating = course.rating
        }
        binding.buttonBuy.visibility = if(isViewBuy) View.VISIBLE else View.INVISIBLE
        binding.buttonPlay.visibility = if(!isViewBuy) View.VISIBLE else View.INVISIBLE

        binding.buttonBuy.setOnClickListener{
            lifecycle.coroutineScope.launch(Dispatchers.IO) {
                val user = getUserLogin()
                if (user != null){
                    val courseIds = getCourseShopping(user.id)
                    lifecycle.coroutineScope.launch(Dispatchers.Main) {
                        if(course != null){
                            if(courseIds.isEmpty()){
                                buyCourse(course.id, user.id)
                                Toast.makeText(applicationContext,"Tienes ${courseIds.size} cursos comprados", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                val bought = courseIds.contains(course.id) //Verificamos si el curso se encuentra en nuestra lista de cursos comprados.
                                if (!bought){
                                    buyCourse(course.id, user.id)
                                    Toast.makeText(applicationContext,"Haz adquirido el curso de ${course.name}", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    Toast.makeText(applicationContext,"Ya has adquirido este curso", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }
                }
            }
        }
        binding.buttonPlay.setOnClickListener {
            val activity = view.context
            val intent = Intent(activity, CourseListVideoActivity::class.java)
            activity.startActivity(intent)
        }

    }

    private fun initDatabase(view: View){
        database = Room.databaseBuilder(
            view.context, ElearningDatabase::class.java, ElearningDatabase.DATABASE_NAME)
            .allowMainThreadQueries()
            .fallbackToDestructiveMigrationOnDowngrade()
            .build()
    }

    private suspend fun buyCourse(courseID: Int, userID: Int){
        val request = ShoppingEntity(
            idUser = userID,
            idCourse = courseID
        )
        database.getUserDao().insertShopping(request)

        /*lateinit var dialog : LottieDialog
        val okButton = Button(this)
        okButton.setText("Ok")
        okButton.setOnClickListener{
            dialog.cancel()
        }


        try{
            dialog = LottieDialog(this)
                .setAnimation(R.raw.succeful)
                .setAnimationRepeatCount(LottieDialog.INFINITE)
                .setAutoPlayAnimation(true)
                .setTitle("Elearning")
                .setTitleColor(Color.BLACK)
                .setMessage("Gracias por su compra!")
                .setMessageColor(Color.BLACK)
                .setDialogBackground(Color.WHITE)
                .setCancelable(false)
                .addActionButton(okButton)
                //.addActionButton(cancelButton)
                .setOnShowListener(OnShowListener { dialogInterface: DialogInterface? -> })
                .setOnDismissListener(DialogInterface.OnDismissListener { dialogInterface: DialogInterface? -> })
                .setOnCancelListener(DialogInterface.OnCancelListener { dialogInterface: DialogInterface? -> })
            dialog.show()
        }
        catch (e: Exception){
            Log.d("dfragoso94", e.message.toString())
        }*/
    }

    private suspend fun getCourseShopping(idUser: Int): List<Int>{
        var response = database.getUserDao().getAllShopping(idUser)
        return response
    }

    private suspend fun getUserLogin(): UserEntity? {
        var response = database.getUserDao().getUserLogin()
        return response
    }


    override fun onBackPressed() {
        finish()
    }
}