package com.takeaway.assignment.testutil

import android.view.View
import android.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf


fun typeSearchViewText(text: String?): ViewAction {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> {
            //Ensure that only apply if it is a SearchView and if it is visible.
            return allOf(isDisplayed(), isAssignableFrom(SearchView::class.java))
        }

        override fun getDescription(): String {
            return "Change view text"
        }

        override fun perform(uiController: UiController?, view: View) {
            (view as SearchView).setQuery(text, false)
        }
    }
}

fun withViewAtPosition(position: Int, itemMatcher: Matcher<View>): Matcher<in View> {
    return object : BoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
        override fun describeTo(description: Description?) {
            itemMatcher.describeTo(description)
        }

        override fun matchesSafely(recyclerView: RecyclerView): Boolean {
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position)
            return viewHolder != null && itemMatcher.matches(viewHolder.itemView)
        }

    }
}