package com.example.snap_chef.presentation.home.recipe

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ImageViewModel(): ViewModel() {
    private val _imageBitmap = MutableStateFlow<Bitmap?>(null)
    var imageBitmap = _imageBitmap.asStateFlow()

    fun setImageBitmap(imageBitmap: Bitmap){
        _imageBitmap.value = imageBitmap
    }
}