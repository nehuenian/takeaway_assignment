package com.takeaway.assignment.ui

import android.os.Bundle
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.takeaway.assignment.R
import com.takeaway.assignment.databinding.ActivityRestaurantsBinding
import com.takeaway.assignment.viewmodels.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsActivity : AppCompatActivity() {
    private lateinit var viewBinding: ActivityRestaurantsBinding
    private val viewModel: RestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRestaurantsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.restaurant_list_container, RestaurantsFragment.newInstance())
            .commitNow()

        viewBinding.restaurantSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return query?.let {
                        viewModel.setSearchFilter(it)
                        true
                    } ?: false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return newText?.let {
                        viewModel.setSearchFilter(it)
                        true
                    } ?: false
                }
            }
        )
    }
}
