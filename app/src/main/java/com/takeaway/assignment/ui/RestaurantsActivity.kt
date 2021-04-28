package com.takeaway.assignment.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.takeaway.assignment.R
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.databinding.ActivityRestaurantsBinding
import com.takeaway.assignment.viewmodels.RestaurantsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RestaurantsActivity :
    AppCompatActivity(),
    SearchView.OnQueryTextListener,
    AdapterView.OnItemSelectedListener {
    private lateinit var viewBinding: ActivityRestaurantsBinding
    private val viewModel: RestaurantsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityRestaurantsBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        supportFragmentManager.beginTransaction()
            .replace(R.id.restaurant_list_container, RestaurantsFragment.newInstance())
            .commitNow()

        viewBinding.restaurantSearch.setOnQueryTextListener(this)
        viewBinding.sortConditionsSpinner.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            SortCondition.values()
        ).apply { setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) }
        viewBinding.sortConditionsSpinner.onItemSelectedListener = this

        // This is not strictly needed, but it's good to have an explicitly defined initial sorting value
        viewBinding.sortConditionsSpinner.setSelection(SortCondition.BEST_MATCH.ordinal)
    }

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

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        (parent?.getItemAtPosition(position) as? SortCondition)?.let { sortCondition ->
            viewModel.setSortCondition(sortCondition)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        viewModel.setSortCondition(SortCondition.DISTANCE)
    }
}
