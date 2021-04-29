package com.takeaway.assignment.util

import android.content.Context
import androidx.annotation.RawRes
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.takeaway.assignment.data.Result
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader

/**
 * Get an object from a json resource in the project.
 *
 * @param context the context used to get the resources
 * @param jsonId the id for the json resource
 * @param charset the charset used to parse the json
 * @return the object represented by the json resource
 */
inline fun <reified T> Gson.fromJson(context: Context, @RawRes jsonId: Int, charset: String): T? {
    val inputStream = context.resources.openRawResource(jsonId)
    val reader: Reader = BufferedReader(InputStreamReader(inputStream, charset))

    return fromJson<T>(reader, object : TypeToken<T>() {}.type)
}

const val READER_CHARSET = "UTF-8"
