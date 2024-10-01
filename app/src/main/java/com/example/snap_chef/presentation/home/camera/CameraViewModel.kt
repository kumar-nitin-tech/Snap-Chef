package com.example.snap_chef.presentation.home.camera

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow


class CameraViewModel(): ViewModel() {
    private val _bitmaps = MutableStateFlow<Bitmap?>(null)
    val bitmaps = _bitmaps.asStateFlow()

    fun onTakePhoto(bitmap: Bitmap){
        _bitmaps.value = bitmap
    }

    fun clearCapturedImage(){
        _bitmaps.value = null
    }

}