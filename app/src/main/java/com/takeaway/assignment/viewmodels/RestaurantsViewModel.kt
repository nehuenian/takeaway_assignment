package com.takeaway.assignment.viewmodels

import androidx.lifecycle.*
import com.takeaway.assignment.data.*
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

    private val _searchFilterPlusSortCondition =
        MutableLiveData<RestaurantFilteringSortingCondition>()
    val searchFilterPlusSortCondition: LiveData<RestaurantFilteringSortingCondition> =
        _searchFilterPlusSortCondition


    val restaurantListState: LiveData<Result<List<Restaurant>>> =
        _searchFilterPlusSortCondition.switchMap {
            liveData {
                emit(Result.Loading)
                emitSource(observeRestaurantListUseCase(it))
            }
        }

    private val _refreshResult = MutableLiveData<Result<Nothing?>>()
    val refreshResult: LiveData<Result<Nothing?>> = _refreshResult

    private val _updateFavouriteResult = MutableLiveData<Result<Nothing?>>()
    val updateFavouriteResult: LiveData<Result<Nothing?>> = _updateFavouriteResult

    fun setSearchFilter(searchText: String) {
        _searchFilterPlusSortCondition.value?.let {
            _searchFilterPlusSortCondition.value = it.copy(searchFilter = searchText)
        } ?: run {
            _searchFilterPlusSortCondition.value =
                RestaurantFilteringSortingCondition(searchFilter = searchText)
        }
    }

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

    fun refreshRestaurants() {
        _refreshResult.value = Result.Loading
        viewModelScope.launch {
            val result = refreshRestaurantListUseCase()
            _refreshResult.value = result
        }
    }

    fun updateFavouriteStatus(restaurant: Restaurant) {
        viewModelScope.launch {
            val result = updateRestaurantFavouriteStatusUseCase(restaurant)
            _updateFavouriteResult.value = result
        }
    }
}
