package com.takeaway.assignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.takeaway.assignment.R
import com.takeaway.assignment.data.Restaurant
import com.takeaway.assignment.data.RestaurantFilteringSortingCondition
import com.takeaway.assignment.data.Result
import com.takeaway.assignment.databinding.FragmentRestaurantsBinding
import com.takeaway.assignment.ui.adapters.RestaurantsAdapter
import com.takeaway.assignment.viewmodels.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RestaurantsFragment : Fragment() {
    private var _viewBinding: FragmentRestaurantsBinding? = null
    private val viewBinding: FragmentRestaurantsBinding get() = _viewBinding!!
    private val viewModel: RestaurantsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _viewBinding = FragmentRestaurantsBinding.inflate(inflater, container, false)
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListAdapter()
        setUpObservers()

        // First load using default values (no search filter and sorting by lowest distance first)
        viewModel.refreshRestaurants()
        viewModel.setFilterAndSortCondition(RestaurantFilteringSortingCondition())
    }

    private fun setUpObservers() {
        viewModel.restaurantListState.observe(::getLifecycle, ::handleRestaurantsResult)
        viewModel.refreshResult.observe(::getLifecycle, ::handleRefreshResult)
        viewModel.updateFavouriteResult.observe(::getLifecycle, ::handleUpdateFavouriteResult)
    }

    private fun handleUpdateFavouriteResult(result: Result<Nothing?>) {
        if (result is Result.Error) {
            Toast.makeText(context, R.string.generic_error, Toast.LENGTH_SHORT).show()
        }
    }

    private fun handleRestaurantsResult(result: Result<List<Restaurant>>) {
        when (result) {
            is Result.Error -> showErrorLayout()
            Result.Loading -> showLoading()
            is Result.Success -> showRestaurantList(result.data)
        }
    }

    private fun handleRefreshResult(result: Result<Nothing?>) {
        when (result) {
            is Result.Error -> showRefreshErrorMessage()
            Result.Loading -> showRefreshing(true)
            is Result.Success -> showRefreshing(false)
        }
    }

    private fun showRefreshErrorMessage() {
        showRefreshing(false)
        Toast.makeText(context, R.string.refresh_error, Toast.LENGTH_SHORT).show()
    }

    private fun showRestaurantList(restaurantList: List<Restaurant>) {
        viewBinding.loadingRestaurantsProgress.visibility = View.GONE
        viewBinding.genericError.genericErrorContainer.visibility = View.GONE
        if (restaurantList.isEmpty()) {
            showNoRestaurantsToShow()
        } else {
            viewBinding.restaurantList.visibility = View.VISIBLE
            viewBinding.noRestaurantsMessage.visibility = View.GONE
            (viewBinding.restaurantList.adapter as RestaurantsAdapter).submitList(restaurantList)
        }
    }

    private fun showRefreshing(isRefreshing: Boolean) {
        viewBinding.swipeRefreshLayout.isRefreshing = isRefreshing
    }

    private fun showNoRestaurantsToShow() {
        viewBinding.restaurantList.visibility = View.GONE
        viewBinding.noRestaurantsMessage.visibility = View.VISIBLE
    }

    private fun showLoading() {
        viewBinding.loadingRestaurantsProgress.visibility = View.VISIBLE
        viewBinding.genericError.genericErrorContainer.visibility = View.GONE
    }

    private fun showErrorLayout() {
        viewBinding.loadingRestaurantsProgress.visibility = View.GONE
        viewBinding.restaurantList.visibility = View.GONE
        viewBinding.genericError.genericErrorContainer.visibility = View.VISIBLE
    }

    private fun setUpListAdapter() {
        viewBinding.restaurantList.layoutManager = LinearLayoutManager(context)
        viewBinding.restaurantList.adapter = RestaurantsAdapter(viewModel)
        viewBinding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshRestaurants()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RestaurantsFragment()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _viewBinding = null
    }
}
