package com.example.snap_chef.presentation.home.util

import androidx.compose.ui.graphics.Color
import com.example.snap_chef.presentation.ui.theme.baseGreen

interface FabButtonSub {
    val iconTint: Color
    val backgroundTint: Color
}

private class FabButtonSubImpl(
    override val iconTint: Color,
    override val backgroundTint: Color
) : FabButtonSub

fun FabButtonSub(
    backgroundTint: Color = baseGreen,
    iconTint: Color = Color.Black
): FabButtonSub = FabButtonSubImpl(iconTint, backgroundTint)
