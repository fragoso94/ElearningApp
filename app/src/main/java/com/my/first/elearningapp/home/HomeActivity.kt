package com.my.first.elearningapp.home

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.my.first.elearningapp.MainActivity
import com.my.first.elearningapp.fragments.ProfileFragment
import com.my.first.elearningapp.R
import com.my.first.elearningapp.fragments.CourseFragment
import com.my.first.elearningapp.fragments.HomeFragment

class HomeActivity : AppCompatActivity() {
    val homeFragment: Fragment = HomeFragment()
    val courseFragment: Fragment = CourseFragment()
    val profileFragment: Fragment = ProfileFragment()

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var navHostFragment: NavHostFragment
    lateinit var bottomNavigationView : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        loadFragment(homeFragment) //lo primero que cargará es el primer fragmento
        initUI()
        initListener()
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onBackPressed() {
        //super.onBackPressed()

        // Aquí puedes agregar la lógica para determinar a qué pantalla deseas regresar

        // Por ejemplo, si deseas regresar a una pantalla llamada "PantallaPrincipalActivity":
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

        // Asegúrate de finalizar la actividad actual para que no se pueda volver atrás
        finish()
    }

    private fun initUI(){
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        /*val menu = bottomNavigationView.menu
        for (i in 0 until menu.size()) {
            val menuItem = menu.getItem(i)
            val icon = menuItem.icon as Drawable
            val iconTintList = resources.getColorStateList(R.color.selector_bottom_navigation_icon_color)
            icon.setTintList(iconTintList)
        }*/
    }

    private fun initListener(){
        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeFragment -> {
                    loadFragment(homeFragment)
                }
                R.id.courseFragment -> {
                    loadFragment(courseFragment)
                }
                R.id.profileFragment -> {
                    loadFragment(profileFragment)
                }
            }
            true
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.courseFragment,
                R.id.profileFragment
            )
        )
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.profileFragment) {
                supportActionBar?.show()
            } else {
                supportActionBar?.hide()
            }
        }
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        //transaction.replace(R.id.frame_container, fragment)
        transaction.replace(R.id.nav_host_fragment, fragment)
        transaction.commit()
    }
}