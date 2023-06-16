package com.my.first.elearningapp.model

import android.os.Parcel
import android.os.Parcelable

data class Course(
    var id: Int,
    var image: Int,
    var category : String,
    var name:String,
    var duration: Float,
    var price: Float,
    var rating: Float) : Parcelable
{
    // Implementar funciones necesarias para Parcelable

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeInt(id)
        dest.writeInt(image)
        dest.writeString(category)
        dest.writeString(name)
        dest.writeFloat(duration)
        dest.writeFloat(price)
        dest.writeFloat(rating)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Course> {
        override fun createFromParcel(parcel: Parcel): Course {
            return Course(
                parcel.readInt(),
                parcel.readInt(),
                parcel.readString()!!,
                parcel.readString()!!,
                parcel.readFloat(),
                parcel.readFloat(),
                parcel.readFloat(),
            )
        }

        override fun newArray(size: Int): Array<Course?> {
            return arrayOfNulls(size)
        }
    }
}