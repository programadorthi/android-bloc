package br.com.programadorthi.instrumentationtesthelpers

import androidx.annotation.IdRes
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import org.hamcrest.Matchers.not

fun onViewWithId(@IdRes resId: Int): ViewInteraction = onView(withId(resId))

fun onViewWithText(text: String): ViewInteraction = onView(withText(text))

fun ViewInteraction.isDisplayed(): ViewInteraction = this.check(matches(ViewMatchers.isDisplayed()))

fun ViewInteraction.isEnabled(): ViewInteraction = this.check(matches(ViewMatchers.isEnabled()))

fun ViewInteraction.isNotDisplayed(): ViewInteraction =
    this.check(matches(not(ViewMatchers.isDisplayed())))

fun ViewInteraction.isNotEnabled(): ViewInteraction =
    this.check(matches(not(ViewMatchers.isEnabled())))

fun ViewInteraction.fillEditText(text: String): ViewInteraction {
    return this.perform(
        ViewActions.replaceText(text),
        ViewActions.closeSoftKeyboard()
    )
}

fun ViewInteraction.scrollAndFillEditText(text: String): ViewInteraction {
    return this.perform(ViewActions.scrollTo())
        .perform(
            ViewActions.replaceText(text),
            ViewActions.closeSoftKeyboard()
        )
}

fun ViewInteraction.clickButton(@IdRes resId: Int): ViewInteraction =
    this.perform(ViewActions.click())

fun ViewInteraction.matchText(text: String): ViewInteraction = this.check(matches(withText(text)))
