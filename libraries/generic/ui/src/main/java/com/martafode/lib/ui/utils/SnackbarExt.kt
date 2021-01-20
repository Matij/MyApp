package com.martafode.lib.ui.utils

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.martafode.lib.kotlin.Tuple2
import com.martafode.lib.ui.R
import timber.log.Timber

fun Fragment.showSnackbar(
    message: Any,
    fragmentView: View? = view,
    displayLength: Int = Snackbar.LENGTH_SHORT,
    gravity: Int = Gravity.BOTTOM,
    anchorView: Int? = null,
    action: Tuple2<Any, () -> Unit>? = null,
) {
    fragmentView ?: return
    val snackBar = when (message) {
        is Int -> Snackbar.make(fragmentView, message, displayLength)
        is String -> Snackbar.make(fragmentView, message, displayLength)
        else -> null
    } ?: return
    snackBar.apply {
        anchorView?.let { setAnchorView(anchorView) }
        animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE
        view.layoutParams = ((view.layoutParams as FrameLayout.LayoutParams).apply { this.gravity = gravity })
        view.background = ContextCompat.getDrawable(context, R.drawable.snackbar_background)
    }.apply {
        action?.let { (text, action) ->
            val listener = View.OnClickListener { action() }
            when (text) {
                is Int -> setAction(text, listener)
                is String -> setAction(text, listener)
                else -> setAction("action", listener).also {
                    Timber.e(Throwable("Unsupported type of text: $text"))
                }
            }
        }
    }.show()
}
