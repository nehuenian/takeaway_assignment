package com.takeaway.assignment.util

import android.annotation.SuppressLint
import android.os.Build
import android.text.Html
import android.text.Spanned

/**
 * Load the html version of the string.
 * This method works as an extension of String? class, so it can
 * be used from any String inside the class.
 */
@SuppressLint("NewApi")
fun String?.fromHtml(): Spanned = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
    Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
} else {
    @Suppress("DEPRECATION")
    Html.fromHtml(this)
}
