package com.takeaway.assignment.viewmodels

import androidx.lifecycle.*
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.RestaurantFilteringSortingCondition
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.usecases.ObserveRestaurantListUseCase
import com.takeaway.assignment.usecases.RefreshRestaurantListUseCase
import com.takeaway.assignment.usecases.UpdateRestaurantFavouriteStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantsViewModel @Inject constructor(
    private val observeRestaurantListUseCase: ObserveRestaurantListUseCase,
    private val refreshRestaurantListUseCase: RefreshRestaurantListUseCase,
    private val updateRestaurantFavouriteStatusUseCase: UpdateRestaurantFavouriteStatusUseCase,
) : ViewModel() {

    // This LiveData will be observed to update the sorting condition label
    // in the items of the restaurant list
    private val _searchFilterPlusSortCondition =
        MutableLiveData<RestaurantFilteringSortingCondition>()
    val searchFilterPlusSortCondition: LiveData<RestaurantFilteringSortingCondition> =
        _searchFilterPlusSortCondition


    // LiveData that controls the loading of the restaurant list, feed by the search filter and sort condition
    val restaurantListState: LiveData<Result<List<Restaurant>>> =
        _searchFilterPlusSortCondition.switchMap {
            liveData {
                emit(Result.Loading)
                emitSource(observeRestaurantListUseCase(it))
            }
        }

    // LiveData used to show/hide the loading indicator and inform of any error during the refresh
    private val _refreshResult = MutableLiveData<Result<Nothing?>>()
    val refreshResult: LiveData<Result<Nothing?>> = _refreshResult

    // LiveData used to inform of any error during the update
    private val _updateFavouriteResult = MutableLiveData<Result<Nothing?>>()
    val updateFavouriteResult: LiveData<Result<Nothing?>> = _updateFavouriteResult

    init {
        // First loading from remote
        refreshRestaurants()
    }

    /**
     * Set up the new text to be used to filter the restaurant list.
     *
     * @param searchText the text that will be used as filter, that can be a suffix, prefix,
     * or the complete name of the desired restaurant
     */
    fun setSearchFilter(searchText: String) {
        _searchFilterPlusSortCondition.value?.let {
            _searchFilterPlusSortCondition.value = it.copy(searchFilter = searchText)
        } ?: run {
            _searchFilterPlusSortCondition.value =
                RestaurantFilteringSortingCondition(searchFilter = searchText)
        }
    }

    /**
     * Set up the new custom sort condition to be used to sort the restaurant list.
     * See [SortCondition] to get the list of available sorting conditions
     *
     * @param sortCondition the sort condition that the user can setup to control the ordering
     * on the restaurant list.
     */
    fun setSortCondition(sortCondition: SortCondition) {
        _searchFilterPlusSortCondition.value?.let {
            _searchFilterPlusSortCondition.value = it.copy(
                sortCondition = sortCondition
            )
        } ?: run {
            _searchFilterPlusSortCondition.value =
                RestaurantFilteringSortingCondition(
                    sortCondition = sortCondition
                )
        }
    }

    /**
     * Refresh the restaurant list with the last updates from the data source.
     */
    fun refreshRestaurants() {
        _refreshResult.value = Result.Loading
        viewModelScope.launch {
            val result = refreshRestaurantListUseCase()
            _refreshResult.value = result
        }
    }

    /**
     * Update the favourite status of a restaurant. The new status (favourite true or false)
     * will be updated on the local data source.
     *
     * @param restaurant the restaurant that will be updated
     */
    fun updateFavouriteStatus(restaurant: Restaurant) {
        viewModelScope.launch {
            val result = updateRestaurantFavouriteStatusUseCase(restaurant)
            _updateFavouriteResult.value = result
        }
    }
}
