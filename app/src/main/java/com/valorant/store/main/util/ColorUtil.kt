package com.valorant.store.main.util

import androidx.annotation.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import android.graphics.Color as BaseColor

class ColorUtil(val color: Color) {

    companion object {
        fun parseRgba(@Size(value = 8) rgbaString: String): ColorUtil =
            (rgbaString.takeLast(2) + rgbaString.dropLast(2))
                .let { BaseColor.parseColor("#$it") }
                .let { ColorUtil(Color(it)) }

        fun setAlpha(color: Color, alpha: Float): Color = color.copy(alpha = alpha)
    }

    val argb: Int by lazy { color.toArgb() }
    val invertedAlpha: Color by lazy { setAlpha(color, 1 - color.alpha) }
}




