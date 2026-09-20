package com.goodwy.commons.helpers

import android.content.Context
import android.graphics.Typeface

/**
 * Compatibility facade for applications that need a single shared font helper.
 */
object FontHelper {
    fun getPersianTypeface(
        context: Context,
        weight: PersianFontHelper.Weight = PersianFontHelper.Weight.REGULAR
    ): Typeface = PersianFontHelper.typeface(context, weight)

    fun applyPersianFont(
        view: android.view.View,
        weight: PersianFontHelper.Weight = PersianFontHelper.Weight.REGULAR
    ) {
        PersianFontHelper.apply(view, weight)
    }
}
