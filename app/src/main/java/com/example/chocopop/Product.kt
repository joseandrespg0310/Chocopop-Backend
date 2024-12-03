package com.example.chocopop
import android.os.Parcel
import android.os.Parcelable

data class Product(
    val _id: String,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val imagen: String,
    val categoria: List<String>?,
    val codigo_de_barras: String,
    val ingredientes: String?,
    val pais: String,
    val peso: Int,
    val fechaRegistro: String?,
    var cantidad: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readString()!!,
        parcel.createStringArrayList(),
        parcel.readString()!!,
        parcel.readString(),
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readString()!!
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(_id)
        parcel.writeString(nombre)
        parcel.writeString(descripcion)
        parcel.writeDouble(precio)
        parcel.writeString(imagen)
        parcel.writeStringList(categoria)
        parcel.writeString(codigo_de_barras)
        parcel.writeString(ingredientes)
        parcel.writeString(pais)
        parcel.writeInt(peso)
        parcel.writeString(fechaRegistro)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}
