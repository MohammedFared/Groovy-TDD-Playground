package petros.efthymiou.groovy.utils

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.test.espresso.Espresso
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.adevinta.android.barista.internal.matcher.DrawableMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher

object RecyclerViewAssertions {
    fun assertDisplayedAtPosition(
        @IdRes listId: Int,
        position: Int,
        @IdRes targetViewId: Int,
        text: String
    ) {
        assertCustomAssertionAtPosition(
            listId = listId,
            position = position,
            targetViewId = targetViewId,
            viewAssertion = ViewAssertions.matches(
                Matchers.allOf(
                    ViewMatchers.withText(text),
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    fun assertDisplayedAtPosition(
        @IdRes listId: Int,
        position: Int,
        @IdRes targetViewId: Int,
        @StringRes text: Int
    ) {
        assertCustomAssertionAtPosition(
            listId = listId,
            position = position,
            targetViewId = targetViewId,
            viewAssertion = ViewAssertions.matches(
                Matchers.allOf(
                    ViewMatchers.withText(text),
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    fun assertDrawableDisplayedAtPosition(
        @IdRes listId: Int,
        position: Int,
        @IdRes targetViewId: Int,
        @DrawableRes drawableRes: Int
    ) {
        assertCustomAssertionAtPosition(
            listId = listId,
            position = position,
            targetViewId = targetViewId,
            viewAssertion = ViewAssertions.matches(
                Matchers.allOf(
                    DrawableMatcher.withDrawable(drawableRes),
                    ViewMatchers.isDisplayed()
                )
            )
        )
    }

    fun assertCustomAssertionAtPosition(
        @IdRes listId: Int,
        position: Int,
        @IdRes targetViewId: Int,
        viewAssertion: ViewAssertion
    ) {
        Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(targetViewId),
                ViewMatchers.isDescendantOfA(nthChildOf(ViewMatchers.withId(listId), position))
            )
        )
            .check(viewAssertion)
    }

    private fun nthChildOf(parentMatcher: Matcher<View>, childPosition: Int): Matcher<View> {
        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("position $childPosition of parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                if (view.parent !is ViewGroup) return false
                val parent = view.parent as ViewGroup

                return (parentMatcher.matches(parent)
                        && parent.childCount > childPosition
                        && parent.getChildAt(childPosition) == view)
            }
        }
    }
}
