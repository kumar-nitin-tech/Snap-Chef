package com.example.snap_chef.presentation.home.util

data class FabButtonItem(
    val iconRes: Int,
    val onFabItemClicked: (fabItem: FabButtonItem) -> Unit,
)
