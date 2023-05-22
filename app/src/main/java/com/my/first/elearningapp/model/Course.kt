package com.my.first.elearningapp.model

import com.my.first.elearningapp.R

// Llave del bundle
val COURSE_ID = "courseID"

data class Course(
    val id: Int,
    val image: Int,
    val category : String,
    val name:String,
    val duration: Float,
    var price: Float,
    var rating: Float){
}

private var cont = 0

var listCourses = listOf<Course>(
    Course(++cont, R.drawable.photoshop, "Diseño","Photoshop", 40F, 269.90F, 3.5F),
    Course(++cont, R.drawable.kotlin,"Programación","Kotlin", 60F, 289.90F, 4F),
    Course(++cont, R.drawable.java, "Programación","Java", 100F, 389.90F,3.5F),
    Course(++cont, R.drawable.canva,"Diseño","Canva", 20F, 169.90F, 2.5F),
    Course(++cont, R.drawable.css3,"Desarrollo Web","CSS 3", 35.5F, 99.90F, 3F),
    Course(++cont, R.drawable.csharp,"Programación","C#", 80.5F, 349.90F, 4.5F),
    Course(++cont, R.drawable.javascript,"Dasarrollo Web","JavaScript", 75.5F, 319.90F, 4.5F),
    Course(++cont, R.drawable.ruby,"Programación","Ruby", 32.5F, 189.90F, 3F),
    Course(++cont, R.drawable.coreldraw,"Diseño","Corel Draw", 30F, 279.90F, 3.5F),
    Course(++cont, R.drawable.perl,"Programación","Perl", 34F, 239.90F, 3F),
    Course(++cont, R.drawable.wordpress,"Desarrollo Web","WordPress", 60F, 359.90F, 4F),
    Course(++cont, R.drawable.html5,"Desarrollo Web","HTML 5", 27.5F, 189.90F, 4.5F),
    Course(++cont, R.drawable.python,"Programación","Python", 50.5F, 359.90F, 5F),
    Course(++cont, R.drawable.go,"Programación","Go", 28.5F, 199.90F, 4F),
    Course(++cont, R.drawable.bootstrap,"Desarrollo Web","Bootstrap", 20F, 139.90F, 3.5F),
    Course(++cont, R.drawable.cplusplus,"Programación","C++", 105F, 399.90F, 4F),
    Course(++cont, R.drawable.visualstudio,"Programación","Visual Studio", 45.5F, 299.90F, 3.5F),
    Course(++cont, R.drawable.figma,"Diseño","Figma", 24F, 219.90F, 4.5F),
    Course(++cont, R.drawable.sass,"Desarrollo Web","SASS", 26.5F, 219.90F, 2.5F),
    Course(++cont, R.drawable.illustrator,"Diseño","Illustrator", 40F, 249.90F, 4.5F),
    Course(++cont, R.drawable.affinity,"Diseño","Affinity", 20F, 149.90F, 4F),
    Course(++cont, R.drawable.php,"Desarrollo Web","PHP", 60F, 279.90F, 3.5F),
    Course(++cont, R.drawable.sketch,"Diseño","Sketch", 46.5F, 329.90F, 3F)
)