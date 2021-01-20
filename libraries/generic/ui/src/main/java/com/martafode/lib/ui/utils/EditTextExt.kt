package com.martafode.lib.ui.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.widget.AppCompatEditText

fun AppCompatEditText.addOnTextChangedListener(onTextChange: (String) -> Unit) {
    addTextChangedListener(
        object : TextWatcher {
            override fun afterTextChanged(editable: Editable) = onTextChange(editable.toString())
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }
    )
}
