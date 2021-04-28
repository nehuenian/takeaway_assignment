package com.takeaway.assignment.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.takeaway.assignment.R
import com.takeaway.assignment.data.sources.RestaurantsRepository
import com.takeaway.assignment.testutil.typeSearchViewText
import com.takeaway.assignment.testutil.withViewAtPosition
import com.takeaway.assignment.util.espresso.EspressoIdlingResource
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.junit.After
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

    @Before
    fun init() {
        hiltRule.inject()
    }

    /**
     * Idling resources tell Espresso that the app is idle or busy. This is needed when operations
     * are not scheduled in the main Looper (for example when executed on a different thread).
     */
    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    /**
     * Unregister your Idling Resource so it can be garbage collected and does not leak any memory.
     */
    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun listRestaurantsFilteredBySuffix() {
        val activityScenario = ActivityScenario.launch(RestaurantsActivity::class.java)

        onView(withId(R.id.restaurant_search)).perform(click())
        onView(withId(R.id.restaurant_search)).perform(typeSearchViewText("sushi"))

        Thread.sleep(1000)
        onView(withId(R.id.restaurant_list)).check(
            matches(
                withViewAtPosition(
                    0,
                    hasDescendant(
                        allOf(
                            withId(R.id.restaurant_name),
                            withText("Sushi One"),
                            isDisplayed()
                        )
                    )
                )
            )
        )
        onView(withId(R.id.restaurant_list)).check(
            matches(
                withViewAtPosition(
                    1,
                    hasDescendant(
                        allOf(
                            withId(R.id.restaurant_name),
                            withText("Tanoshii Sushi"),
                            isDisplayed()
                        )
                    )
                )
            )
        )
        onView(withText("Tanoshii Sushi")).check(matches(isDisplayed()))

        activityScenario.close()
    }
}