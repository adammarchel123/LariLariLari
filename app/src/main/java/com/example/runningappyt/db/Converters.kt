package com.example.runningappyt.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class Converters {

    // TypeConverter akan mengonversi class kustom ke dan dari jenis yang diketahui dan dapat dipertahankan oleh Room.
    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray {
        // ByteArrayOutputStream mengkonversi objek BitmapData ke file PNG atau JPG
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }
}