package com.goodwy.commons.views

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.TextUtils
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.setPadding
import com.goodwy.commons.activities.BaseSimpleActivity
import com.goodwy.commons.extensions.getProperTextColor
import com.goodwy.commons.models.Note
import com.goodwy.commons.models.Tag
import kotlin.math.roundToInt

class TagNoteView(private val activity: BaseSimpleActivity) : LinearLayout(activity) {
    private val tagsRow = LinearLayout(activity)
    private val noteView = MyTextView(activity)

    init {
        orientation = VERTICAL
        gravity = Gravity.START
        visibility = GONE
        setPadding(dp(2), dp(2), dp(2), dp(2))
        tagsRow.orientation = HORIZONTAL
        tagsRow.gravity = Gravity.START or Gravity.CENTER_VERTICAL
        addView(tagsRow, LayoutParams(-1, -2))
        noteView.apply {
            textSize = 11f
            setTextColor(activity.getProperTextColor())
            alpha = .78f
            maxLines = 2
            ellipsize = TextUtils.TruncateAt.END
            setPadding(dp(4), dp(2), dp(4), dp(2))
        }
        addView(noteView, LayoutParams(-1, -2))
    }

    fun render(tags: List<Tag>, note: Note?) {
        tagsRow.removeAllViews()
        tags.forEach { tag ->
            tagsRow.addView(TextView(activity).apply {
                text = "#${tag.name}"
                textSize = 10f
                maxLines = 1
                ellipsize = TextUtils.TruncateAt.END
                gravity = Gravity.CENTER
                setTextColor(tag.color)
                setPadding(dp(7), dp(2), dp(7), dp(2))
                background = GradientDrawable().apply {
                    cornerRadius = dp(10).toFloat()
                    setColor(withAlpha(tag.color, .12f))
                    setStroke(dp(1), withAlpha(tag.color, .28f))
                }
                layoutParams = LayoutParams(-2, dp(22)).apply { marginEnd = dp(5) }
            })
        }
        val text = note?.text?.trim().orEmpty()
        noteView.text = if (text.isEmpty()) "" else "Note  ·  $text"
        noteView.visibility = if (text.isEmpty()) GONE else VISIBLE
        visibility = if (tags.isEmpty() && text.isEmpty()) GONE else VISIBLE
    }

    private fun withAlpha(color: Int, alpha: Float): Int {
        val a = (Color.alpha(color) * alpha).roundToInt().coerceIn(18, 255)
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color))
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).roundToInt()
}
