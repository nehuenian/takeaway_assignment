package com.takeaway.assignment.data

import com.google.gson.annotations.SerializedName

data class Restaurant(
    val name: String,
    val status: Status,
    val sortingValues: SortingValues
) {

    enum class Status {
        @SerializedName("open")
        OPEN,

        @SerializedName("closed")
        CLOSED,

        @SerializedName("order ahead")
        ORDER_AHEAD
    }
}
