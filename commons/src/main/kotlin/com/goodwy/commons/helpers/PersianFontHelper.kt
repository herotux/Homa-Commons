package com.goodwy.commons.helpers

import android.content.Context
import android.graphics.Typeface
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import com.goodwy.commons.R

/**
 * Shared Persian font support for Goodwy-based applications.
 *
 * Bundled Persian families are distributed under the SIL Open Font License.
 */
object PersianFontHelper {
    enum class Family {
        VAZIRMATN,
        SAHEL,
        SHABNAM,
        SAMIM,
        TANHA,
        NAHID
    }

    enum class Weight(val value: Int) {
        THIN(100),
        EXTRA_LIGHT(200),
        LIGHT(300),
        REGULAR(400),
        MEDIUM(500),
        SEMI_BOLD(600),
        BOLD(700),
        EXTRA_BOLD(800),
        BLACK(900)
    }

    fun typeface(
        context: Context,
        family: Family = Family.VAZIRMATN,
        weight: Weight = Weight.REGULAR
    ): Typeface {
        val resId = when (family) {
            Family.VAZIRMATN -> when (weight) {
                Weight.THIN -> R.font.vazirmatn_thin
                Weight.EXTRA_LIGHT -> R.font.vazirmatn_extra_light
                Weight.LIGHT -> R.font.vazirmatn_light
                Weight.REGULAR -> R.font.vazirmatn_regular
                Weight.MEDIUM -> R.font.vazirmatn_medium
                Weight.SEMI_BOLD -> R.font.vazirmatn_semi_bold
                Weight.BOLD -> R.font.vazirmatn_bold
                Weight.EXTRA_BOLD -> R.font.vazirmatn_extra_bold
                Weight.BLACK -> R.font.vazirmatn_black
            }
            Family.SAHEL -> when (weight) {
                Weight.LIGHT -> R.font.sahel_light
                Weight.SEMI_BOLD -> R.font.sahel_semi_bold
                Weight.BOLD -> R.font.sahel_bold
                Weight.BLACK -> R.font.sahel_black
                else -> R.font.sahel_regular
            }
            Family.SHABNAM -> when (weight) {
                Weight.THIN -> R.font.shabnam_thin
                Weight.LIGHT -> R.font.shabnam_light
                Weight.MEDIUM -> R.font.shabnam_medium
                Weight.BOLD -> R.font.shabnam_bold
                else -> R.font.shabnam_regular
            }
            Family.SAMIM -> when (weight) {
                Weight.MEDIUM -> R.font.samim_medium
                Weight.BOLD -> R.font.samim_bold
                else -> R.font.samim_regular
            }
            Family.TANHA -> R.font.tanha_regular
            Family.NAHID -> R.font.nahid_regular
        }

        return ResourcesCompat.getFont(context, resId) ?: Typeface.DEFAULT
    }

    fun apply(
        view: View,
        family: Family = Family.VAZIRMATN,
        weight: Weight = Weight.REGULAR
    ) {
        if (view is TextView) {
            view.typeface = typeface(view.context, family, weight)
        }

        if (view is ViewGroup) {
            for (index in 0 until view.childCount) {
                apply(view.getChildAt(index), family, weight)
            }
        }
    }
}
