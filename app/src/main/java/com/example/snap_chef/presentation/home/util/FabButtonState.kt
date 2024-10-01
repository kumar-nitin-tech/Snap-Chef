package com.example.snap_chef.presentation.home.util

sealed class FabButtonState {
    data object Collapsed: FabButtonState()
    data object Expand: FabButtonState()

    fun isExpanded() = this == Expand

    fun toggleValue() = if(isExpanded()){
        Collapsed
    }else{
        Expand
    }
}


