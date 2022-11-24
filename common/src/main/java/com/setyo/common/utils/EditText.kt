package com.setyo.common.utils

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.onStart


@ExperimentalCoroutinesApi
fun EditText.asFlow(): Flow<String> = callbackFlow {
    val listener = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence, p1: Int, p2: Int, p3: Int) = Unit

        override fun afterTextChanged(p0: Editable) {
            trySend(p0.toString())
        }

    }
    addTextChangedListener(listener)
    awaitClose { removeTextChangedListener(listener) }
}.onStart { emit(text.toString()) }