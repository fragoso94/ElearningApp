package com.my.first.elearningapp.database.utilities

import android.content.Context
import android.content.res.Resources
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.my.first.elearningapp.model.Course
import com.my.first.elearningapp.model.CourseResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

class Helpers {
    companion object{
        const val COURSE_ID = "courseID"
        const val COURSE_ITEM = "courseITEM"
        const val IS_VIEW_BUY = "courseTYPE"
        const val URL_BASE_API = "https://apicursosbedu.servicesnet.site/" //"http://192.168.1.164:9095/"
        // Código para los permisos de la camará
        const val PERMISSION_CODE = 1000
        const val IMAGE_CAPTURE_CODE = 1001

        fun convertDataClass(context: Context, dataClassA: CourseResponse): Course {
            val resources: Resources = context.resources
            val packageName: String = context.packageName
            val image = resources.getIdentifier(dataClassA.image, "drawable", packageName)
            //Course(++cont, R.drawable.photoshop, "Diseño","Photoshop", 40F, 269.90F, 3.5F),
            with(dataClassA){
                return Course(id, image, category, name, duration, price, rating)
            }
        }

        fun convertListDataClass(context: Context, dataClassA: List<CourseResponse>): List<Course> {
            val resources: Resources = context.resources
            val packageName: String = context.packageName

            val courses: MutableList<Course> = mutableListOf()
            for (item in dataClassA){
                val image = resources.getIdentifier(item.image, "drawable", packageName)
                with(item){
                    val course = Course(id, image, category, name, duration, price, rating)
                    courses.add(course)
                    //Log.d("dfragoso94", course.toString())
                }
            }

            return courses
        }

        fun createUnsafeOkHttpClient(): OkHttpClient {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                }

                override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>?, authType: String?) {
                }

                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> {
                    return arrayOf()
                }
            })

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            return try {
                val sslContext = SSLContext.getInstance("TLS")
                sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                val sslSocketFactory = sslContext.socketFactory

                OkHttpClient.Builder()
                    .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    .addInterceptor(loggingInterceptor)
                    .hostnameVerifier { _, _ -> true }
                    .build()
            } catch (e: NoSuchAlgorithmException) {
                throw RuntimeException("Failed to create unsafe OkHttpClient", e)
            } catch (e: KeyManagementException) {
                throw RuntimeException("Failed to create unsafe OkHttpClient", e)
            }
        }

        fun isInternetAvailable(context: Context): Boolean {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val network = connectivityManager.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false

                return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } else {
                val networkInfo = connectivityManager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnectedOrConnecting
            }
        }
    }
}