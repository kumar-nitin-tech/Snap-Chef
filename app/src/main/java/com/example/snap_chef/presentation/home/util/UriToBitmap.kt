package com.example.snap_chef.presentation.home.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

class UriToBitmap {

    fun uriToBitmap(context: Context , uri: Uri): Bitmap? {
        val contentResolver = context.contentResolver
        return try{
            if(Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(contentResolver , uri)
                val bitmap = ImageDecoder.decodeBitmap(source) { decoder , _ , _ ->
                    decoder.setTargetSampleSize(1) // shrinking by
                    decoder.isMutableRequired =
                        true // this resolve the hardware type of bitmap problem
                }
                bitmap
            }
        }catch (e:Exception){
            throw e
        }
    }
}