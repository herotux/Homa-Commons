package com.goodwy.commons.views

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
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

    companion object {
        private const val LONG_NOTE_THRESHOLD = 180
    }

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
            linksClickable = true
            movementMethod = LinkMovementMethod.getInstance()
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
        if (text.isEmpty()) {
            noteView.text = ""
            noteView.visibility = GONE
            noteView.setOnClickListener(null)
        } else {
            val displayText = SpannableString("Note  ·  $text")
            Linkify.addLinks(displayText, Linkify.WEB_URLS)
            noteView.text = displayText
            noteView.visibility = VISIBLE

            val isLong = text.length > LONG_NOTE_THRESHOLD
            noteView.setOnClickListener(if (isLong) {
                { showFullNote(text) }
            } else {
                null
            })
        }

        visibility = if (tags.isEmpty() && text.isEmpty()) GONE else VISIBLE
    }

    private fun showFullNote(text: String) {
        val fullText = TextView(activity).apply {
            textSize = 14f
            setTextColor(activity.getProperTextColor())
            setPadding(dp(4), dp(4), dp(4), dp(4))
            autoLinkMask = 0
            linksClickable = true
            movementMethod = LinkMovementMethod.getInstance()

            val spannable = SpannableString(text)
            Linkify.addLinks(spannable, Linkify.WEB_URLS)
            this.text = spannable
        }

        val container = LinearLayout(activity).apply {
            orientation = VERTICAL
            setPadding(dp(20), dp(4), dp(20), dp(4))
            addView(fullText, LinearLayout.LayoutParams(-1, -2))
        }

        AlertDialog.Builder(activity)
            .setTitle(com.goodwy.commons.R.string.notes)
            .setView(container)
            .setPositiveButton(android.R.string.ok, null)
            .show()
    }

    private fun withAlpha(color: Int, alpha: Float): Int {
        val a = (Color.alpha(color) * alpha).roundToInt().coerceIn(18, 255)
        return Color.argb(a, Color.red(color), Color.green(color), Color.blue(color))
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density).roundToInt()
}
