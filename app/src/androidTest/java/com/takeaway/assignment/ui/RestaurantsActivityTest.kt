package com.takeaway.assignment.ui

import android.view.View
import androidx.annotation.IdRes
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.takeaway.assignment.R
import com.takeaway.assignment.data.SortCondition
import com.takeaway.assignment.data.sources.RestaurantsRepository
import com.takeaway.assignment.data.sources.TestRestaurantsRepository
import com.takeaway.assignment.testutil.*
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@MediumTest
@HiltAndroidTest
class RestaurantsActivityTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: RestaurantsRepository

    @IdRes
    private val favouriteCheckId = R.id.favourite_check

    private val recyclerViewMatcher = withId(R.id.restaurant_list)
    private val searchMatcher = withId(R.id.restaurant_search)
    private val spinnerMatcher = withId(R.id.sort_conditions_spinner)
    private val genericErrorMatcher = withText(R.string.generic_error)
    private val tryAgainButtonMatcher = withId(R.id.generic_error_try_again)
    private val bestMatchSortConditionMatcher = withText(SortCondition.BEST_MATCH.toString())
    private val newestSortConditionMatcher = withText(SortCondition.NEWEST.toString())
    private val ratingSortConditionMatcher = withText(SortCondition.RATING_AVERAGE.toString())
    private val distanceSortConditionMatcher = withText(SortCondition.DISTANCE.toString())
    private val popularitySortConditionMatcher = withText(SortCondition.POPULARITY.toString())
    private val lowestPriceSortConditionMatcher =
        withText(SortCondition.AVERAGE_PRODUCT_PRICE_LOWEST_FIRST.toString())
    private val highestPriceSortConditionMatcher =
        withText(SortCondition.AVERAGE_PRODUCT_PRICE_HIGHEST_FIRST.toString())
    private val deliveryCostSortConditionMatcher = withText(SortCondition.DELIVERY_COSTS.toString())
    private val minCostSortConditionMatcher = withText(SortCondition.MIN_COST.toString())
    private val restaurantNameMatcher = withId(R.id.restaurant_name)
    private val restaurantStatusMatcher = withId(R.id.restaurant_status)
    private val restaurantSortingValueMatcher = withId(R.id.sorting_value)
    private val restaurantFavouriteCheckMatcher = withId(R.id.favourite_check)
    private val noRestaurantsToShowMatcher = withText(R.string.no_restaurants_to_show)

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun restaurantListFilteredBySuffix() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(searchMatcher).perform(click())
        onView(searchMatcher).perform(typeSearchViewText(SEARCH_FILTER))

        Thread.sleep(1000)

        verifyRestaurantAtPosition(FIRST_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedFilteredRestaurantList)

        activityScenario.close()
    }

    @Test
    fun restaurantListFilteredIsEmpty() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(searchMatcher).perform(click())
        onView(searchMatcher).perform(typeSearchViewText(NO_MATCHING_FILTER))

        Thread.sleep(1000)

        onView(noRestaurantsToShowMatcher).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun errorWhileLoadingRestaurantList() {
        (repository as TestRestaurantsRepository).setReturnError(true)
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(genericErrorMatcher).check(matches(isDisplayed()))

        activityScenario.close()
    }

    @Test
    fun errorWhileLoadingRestaurantListTryAgainToFix() {
        (repository as TestRestaurantsRepository).setReturnError(true)
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(genericErrorMatcher).check(matches(isDisplayed()))

        (repository as TestRestaurantsRepository).setReturnError(false)
        onView(tryAgainButtonMatcher).check(matches(isDisplayed())).perform(click())

        Thread.sleep(1000)

        onView(genericErrorMatcher).check(matches(not(isDisplayed())))
        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByBestMatch)

        activityScenario.close()
    }

    @Test
    fun restaurantMarkUnMarkAsFavourite() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(searchMatcher).perform(click())
        onView(searchMatcher).perform(typeSearchViewText(SEARCH_FILTER))

        Thread.sleep(1000)

        verifyRestaurantAtPosition(FIRST_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedFilteredRestaurantList)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedFilteredRestaurantList)

        recyclerViewMatcher.clickOnViewWithIdAtPosition(R.id.favourite_check, FOURTH_POSITION)

        Thread.sleep(1000)

        expectedFilteredRestaurantList[FOURTH_POSITION].run {
            restaurantNameMatcher.verifyRestaurantAtPosition(FIRST_POSITION, name)
            restaurantStatusMatcher.verifyRestaurantAtPosition(FIRST_POSITION, status)
            restaurantSortingValueMatcher.verifyRestaurantAtPosition(FIRST_POSITION, sortingValue)
            restaurantFavouriteCheckMatcher.verifyRestaurantAtPosition(FIRST_POSITION, true)
        }

        recyclerViewMatcher.clickOnViewWithIdAtPosition(favouriteCheckId, FIRST_POSITION)

        Thread.sleep(1000)

        expectedFilteredRestaurantList[FOURTH_POSITION].run {
            restaurantNameMatcher.verifyRestaurantAtPosition(FOURTH_POSITION, name)
            restaurantStatusMatcher.verifyRestaurantAtPosition(FOURTH_POSITION, status)
            restaurantSortingValueMatcher.verifyRestaurantAtPosition(FOURTH_POSITION, sortingValue)
            restaurantFavouriteCheckMatcher.verifyRestaurantAtPosition(FOURTH_POSITION, false)
        }

        activityScenario.close()
    }

    /*************************************************************
     *                      SORTING SECTION                      *
     *************************************************************/

    @Test
    fun restaurantListSortedByBestMatch() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(bestMatchSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByBestMatch)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByBestMatch)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByNewest() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(newestSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByNewest)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByNewest)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByNewest)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByNewest)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByRating() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(ratingSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByRating)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByRating)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByRating)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByRating)

        activityScenario.close()
    }


    @Test
    fun restaurantListSortedByDistance() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(distanceSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByDistance)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByDistance)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByDistance)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByDistance)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByPopularity() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(popularitySortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByPopularity)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByPopularity)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByPopularity)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByPopularity)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByLowestPrice() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(lowestPriceSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByLowestPrice)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByLowestPrice)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByLowestPrice)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByLowestPrice)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByHighestPrice() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(highestPriceSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByHighestPrice)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByHighestPrice)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByHighestPrice)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByHighestPrice)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByDeliveryCost() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(deliveryCostSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByDeliveryCost)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByDeliveryCost)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByDeliveryCost)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByDeliveryCost)

        activityScenario.close()
    }

    @Test
    fun restaurantListSortedByMinCost() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(spinnerMatcher).perform(click())
        onView(minCostSortConditionMatcher).perform(click())

        Thread.sleep(1000)

        recyclerViewMatcher.scrollToTheTop()

        verifyRestaurantAtPosition(FIRST_POSITION, expectedRestaurantListSortedByMinCost)
        verifyRestaurantAtPosition(SECOND_POSITION, expectedRestaurantListSortedByMinCost)
        verifyRestaurantAtPosition(THIRD_POSITION, expectedRestaurantListSortedByMinCost)
        verifyRestaurantAtPosition(FOURTH_POSITION, expectedRestaurantListSortedByMinCost)

        activityScenario.close()
    }

    private fun verifyRestaurantAtPosition(
        position: Int,
        expectedRestaurantList: List<ExpectedRestaurant>,
    ) {
        expectedRestaurantList[position].run {
            restaurantNameMatcher.verifyRestaurantAtPosition(position, name)
            restaurantStatusMatcher.verifyRestaurantAtPosition(position, status)
            restaurantSortingValueMatcher.verifyRestaurantAtPosition(position, sortingValue)
            restaurantFavouriteCheckMatcher.verifyRestaurantAtPosition(position, isFavourite)
        }
    }
}

private fun Matcher<View>.verifyRestaurantAtPosition(position: Int, value: Boolean) {
    onView(withId(R.id.restaurant_list)).check(
        ViewAssertions.matches(
            withViewAtPosition(
                position,
                hasDescendant(
                    allOf(
                        this,
                        if (value) isChecked() else isNotChecked(),
                        isDisplayed()
                    )
                )
            )
        )
    )
}

private fun Matcher<View>.verifyRestaurantAtPosition(position: Int, value: String) {
    onView(withId(R.id.restaurant_list)).check(
        ViewAssertions.matches(
            withViewAtPosition(
                position,
                hasDescendant(
                    allOf(
                        this,
                        withText(value),
                        isDisplayed()
                    )
                )
            )
        )
    )
}
