package com.my.first.elearningapp.fragments

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionInflater
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.my.first.elearningapp.R
import com.my.first.elearningapp.database.utilities.Helpers
import com.my.first.elearningapp.update.ChangepassActivity
import com.my.first.elearningapp.update.UpdateActivity
import java.text.SimpleDateFormat
import java.util.*

class ProfileFragment: Fragment(R.layout.fragment_profile) {

    private lateinit var btnIrAActividad: ImageButton
    private lateinit var btnIrAActividad2: ImageButton
    private lateinit var imageViewUser: ImageView
    private lateinit var addPhoto: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Configura la animación de transición
        val enterTransitionAnim: Transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
        enterTransitionAnim.duration = 1000

        val exitTransitionAnim: Transition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.fade)
        exitTransitionAnim.duration = 1000

        // Establece la transición de entrada y salida en el Fragment
        enterTransition = enterTransitionAnim
        exitTransition = exitTransitionAnim

        initUI(view)
        initListener()
    }

    fun initUI(view: View){
        btnIrAActividad = view.findViewById(R.id.imageButton3)
        btnIrAActividad2 = view.findViewById(R.id.imageButton4)
        imageViewUser = view.findViewById(R.id.imageViewUser)
        addPhoto = view.findViewById(R.id.addPhotoFab)
    }

    fun initListener(){
        btnIrAActividad.setOnClickListener {
            // Acción al hacer clic en el botón
            val intent = Intent(requireContext(), UpdateActivity::class.java)
            startActivity(intent)
        }

        btnIrAActividad2.setOnClickListener {
            // Acción al hacer clic en el botón
            val intent = Intent(requireContext(), ChangepassActivity::class.java)
            startActivity(intent)
        }

        addPhoto.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera()
            } else {
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ),
                    Helpers.PERMISSION_CODE
                )
            }
        }
    }

    private fun openCamera() {
        try
        {
            val format = SimpleDateFormat("dd-MM-yyyy", Locale.US)
                .format(System.currentTimeMillis())
            val name = "elearningPhoto_$format"
            val values = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
                    put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/BeduGallery")
                }
            }
            //val values = ContentValues()
            val resolver = requireActivity().contentResolver
            val imageUri: Uri? = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)

           val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
           intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri)
           startActivityForResult(intent, Helpers.IMAGE_CAPTURE_CODE)
        }
        catch (e: Exception){
           Log.d("dfragoso94", e.toString())
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            Helpers.PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openCamera()
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Permiso de cámara y almacenamiento denegado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Helpers.IMAGE_CAPTURE_CODE && resultCode == Activity.RESULT_OK) {
            try {
                val bitmap = data?.extras?.get("data") as Bitmap
                imageViewUser.setImageBitmap(bitmap)
            }
            catch (e: Exception){
                Log.d("dfragoso94", e.toString())
            }
        }
    }

}