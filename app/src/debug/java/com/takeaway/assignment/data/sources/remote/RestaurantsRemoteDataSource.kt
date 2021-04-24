package com.takeaway.assignment.data.sources.remote

import android.accounts.NetworkErrorException
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteCantOpenDatabaseException
import androidx.annotation.RawRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.takeaway.assignment.R
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.Result.Error
import com.takeaway.assignment.data.Result.Success
import com.takeaway.assignment.data.sources.RestaurantsDataSource
import kotlinx.coroutines.delay
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.Reader
import javax.inject.Inject

/**
 * Faked implementation of the remote data source that adds a latency
 * to try to emulate a real network response.
 * Used only for debug purposes.
 */
class RestaurantsRemoteDataSource @Inject constructor(context: Context) : RestaurantsDataSource {
    companion object {
        private const val SERVICE_FAKED_LATENCY_IN_MILLIS = 3000L
        private const val READER_CHARSET = "UTF-8"
    }

    // Use this variable to test the error test case
    private val shouldFail: Boolean = false

    private val restaurantsList: List<Restaurant>? =
        Gson().fromJson<List<Restaurant>>(context, R.raw.sample_restaurant_list, READER_CHARSET)

    private val _observableRestaurantList = MutableLiveData<Result<List<Restaurant>>>()
    override val observableRestaurantList: LiveData<Result<List<Restaurant>>> =
        _observableRestaurantList

    override suspend fun getRestaurantList(): Result<List<Restaurant>> {
        // Faked latency to emulate the network charge
        delay(SERVICE_FAKED_LATENCY_IN_MILLIS)
        if (shouldFail) return Error(NetworkErrorException("500: "))
        return restaurantsList?.let {
            Success(it)
        } ?: Success(listOf())
    }

    @SuppressLint("NullSafeMutableLiveData")
    override suspend fun refreshRestaurantList() {
        _observableRestaurantList.value = getRestaurantList()
    }

    override suspend fun updateFavourite(
        restaurantId: String,
        isFavourite: Boolean
    ): Result<Nothing?> {
        return if (shouldFail) {
            Error(SQLiteCantOpenDatabaseException("Cant open the database"))
        } else {
            Success(null)
        }
    }

    override suspend fun insertOrUpdateRestaurant(restaurant: Restaurant) {
        // no-op
    }
}

inline fun <reified T> Gson.fromJson(context: Context, @RawRes jsonId: Int, charset: String): T? {
    val inputStream = context.resources.openRawResource(jsonId)
    val reader: Reader = BufferedReader(InputStreamReader(inputStream, charset))

    return fromJson<T>(reader, object : TypeToken<T>() {}.type)
}
