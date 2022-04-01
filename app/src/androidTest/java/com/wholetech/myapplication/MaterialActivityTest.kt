package com.wholetech.myapplication

import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import kotlinx.android.synthetic.main.content_material.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MaterialActivityTest {
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule<MaterialActivity>(MaterialActivity::class.java)

    @Test
    fun guessWrong() {
        val resources = activityTestRule.activity.resources
        val secret = activityTestRule.activity.secretNumber.secert
        for (n in 1..10) {
            if (n != secret) {
                onView(withId(R.id.number)).perform(clearText())
                onView(withId(R.id.number)).perform(typeText(n.toString()))
                onView(withId(R.id.ok_button)).perform(click())
                val message =
                    if (n < secret) resources.getString(R.string.bigger)
                    else resources.getString(R.string.smaller)
                onView(withText(message)).check(matches(isDisplayed()))
                onView(withText(resources.getString(R.string.ok))).perform(click())
            }
        }
    }

    @Test
    fun replay() {
        val resources = activityTestRule.activity.resources
        val secret = activityTestRule.activity.secretNumber.secert
        //增加遊玩次數
        val n = 5
        if (n != secret) {
            onView(withId(R.id.number)).perform(clearText())
            onView(withId(R.id.number)).perform(typeText(n.toString()))
            onView(withId(R.id.ok_button)).perform(click())
            val message =
                if (n < secret) resources.getString(R.string.bigger)
                else resources.getString(R.string.smaller)
            onView(withText(message)).check(matches(isDisplayed()))
            onView(withText(resources.getString(R.string.ok))).perform(click())
        }
        Espresso.pressBack()
        //replay
        onView(withId(R.id.fab)).perform(click())
        onView(withText(resources.getString(R.string.are_you_sure))).check(matches(isDisplayed()))
        onView(withText(resources.getString(R.string.ok))).perform(click())
        onView(withId(R.id.counter)).check(matches(withText("0")))
    }
}