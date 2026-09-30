package com.goodwy.commons.dialogs

import android.app.AlertDialog
import android.text.InputType
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.goodwy.commons.activities.BaseSimpleActivity
import com.goodwy.commons.helpers.ensureBackgroundThread

object TagNoteDialogs {
    fun editTags(activity: BaseSimpleActivity, currentTags: List<String>, hint: String, help: String, title: String, onSaved: (List<String>) -> Unit) {
        val input = EditText(activity).apply { this.hint = hint; inputType = InputType.TYPE_CLASS_TEXT; setSingleLine(false) }
        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (20 * resources.displayMetrics.density).toInt()
            setPadding(padding, 0, padding, 0)
            addView(input, LinearLayout.LayoutParams(-1, -2))
            addView(TextView(activity).apply { text = help; textSize = 12f; alpha = .7f; setPadding(0, padding / 2, 0, 0) })
        }
        input.setText(currentTags.joinToString(", "))
        AlertDialog.Builder(activity).setTitle(title).setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val tags = input.text.toString().split(',', '\n', '،')
                ensureBackgroundThread { activity.runOnUiThread { onSaved(tags) } }
            }.show()
    }

    fun editNote(activity: BaseSimpleActivity, currentNote: String, hint: String, title: String, onSaved: (String) -> Unit) {
        val input = EditText(activity).apply {
            this.hint = hint
            inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE or InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
            minLines = 4
            maxLines = 8
            setText(currentNote)
            setSelection(text.length)
        }
        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            val padding = (20 * resources.displayMetrics.density).toInt()
            setPadding(padding, 0, padding, 0)
            addView(input, LinearLayout.LayoutParams(-1, -2))
        }
        AlertDialog.Builder(activity).setTitle(title).setView(container)
            .setNegativeButton(android.R.string.cancel, null)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                ensureBackgroundThread { activity.runOnUiThread { onSaved(input.text.toString()) } }
            }.show()
    }
}
