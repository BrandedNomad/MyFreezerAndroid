package com.myfreezer.app.ui.main

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.myfreezer.app.R
import com.myfreezer.app.ui.freezer.FreezerFragment
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.microsoft.appcenter.espresso.Factory
import com.microsoft.appcenter.espresso.ReportHelper
import org.junit.After

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get: Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()

    @Test
    fun test_isMainActivityInView(){

//        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.mainActivity)).check(matches(isDisplayed()))
    }



    @Test
    fun test_isBottomNavigationWorking(){
        onView(withId(R.id.bottomNavItemFreezer)).perform(click())
        onView(withText("My Freezer")).check(matches(isDisplayed()))

        onView(withId(R.id.bottomNavItemRecipe)).perform(click())
        onView(withText("Recipe Suggestions")).check(matches(isDisplayed()))

        onView(withId(R.id.bottomNavItemFavourite)).perform(click())
        onView(withText("Favourite Recipes")).check(matches(isDisplayed()))

        onView(withId(R.id.bottomNavItemShopping)).perform(click())
        onView(withText("Shopping List")).check(matches(isDisplayed()))

        onView(withId(R.id.bottomNavItemFreezer)).perform(click())
        onView(withText("My Freezer")).check(matches(isDisplayed()))
    }

    @Test
    fun test_ifFreezerFragmentLoads(){
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(FreezerFragment())
        }

        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown(){
        reportHelper.label("Finishing test")
    }

}