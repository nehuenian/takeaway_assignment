package com.takeaway.assignment.testutil

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class ActionOnItemViewAtPositionViewAction(
    private val position: Int,
    @param:IdRes private val viewId: Int,
    private val viewAction: ViewAction,
) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun getDescription(): String {
        return ("actionOnItemAtPosition performing ViewAction: "
                + viewAction.description
                + " on item at position: "
                + position)
    }

    override fun perform(uiController: UiController, view: View) {
        ScrollToPositionViewAction(position).perform(uiController, view)
        uiController.loopMainThreadUntilIdle()

        // Try following if above does not work for off screen item
        val viewHolder = (view as RecyclerView).findViewHolderForAdapterPosition(position)

        viewHolder?.itemView?.findViewById<View>(viewId)?.let { targetView ->
            viewAction.perform(uiController, targetView)
        } ?: run {
            throw PerformException.Builder()
                .withActionDescription(toString())
                .withViewDescription(HumanReadables.describe(view))
                .withCause(IllegalStateException("No view with id $viewId found at position: $position"))
                .build()
        }
    }
}

class ScrollToPositionViewAction(private val position: Int) : ViewAction {
    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(isAssignableFrom(RecyclerView::class.java), isDisplayed())
    }

    override fun getDescription(): String {
        return "scroll RecyclerView to position: $position"
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        recyclerView.scrollToPosition(position)
    }
}
