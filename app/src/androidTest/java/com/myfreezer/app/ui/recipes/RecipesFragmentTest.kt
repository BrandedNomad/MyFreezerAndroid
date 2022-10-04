package com.myfreezer.app.ui.recipes

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microsoft.appcenter.espresso.Factory
import com.myfreezer.app.R
import com.myfreezer.app.shared.mock.mockDataList
import com.myfreezer.app.ui.freezer.FreezerFragment
import com.myfreezer.app.ui.main.MainActivity
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RecipesFragmentTest{

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()
    lateinit var recyclerView: RecyclerView

    @Before
    fun setup(){
        //launches both the mainActivity and loads desired fragment into the fragmentContainer
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(RecipesFragment())
            //recyclerView = it.findViewById(R.id.recipesRecyclerView)
        }


    }

    //Scenario 1: User navigates to the recipe view
    @Test
    fun navBarRecipeButton_whenClicked_navigatesToRecipeViewAndDisplaysRecipes(){
        //GIVEN some fake recipe data
        var fakeRecipeData = mockDataList
        //    WHEN the user navigates to the recipe view
        onView(withId(R.id.bottomNavItemRecipe)).perform(click())
        //    THEN the user is presented with a list of recipe recommendations
        onView(withId(R.id.recipesRecyclerView)).check(matches(isDisplayed()))
        onView(withText("Pomodoro")).check(matches(isDisplayed()))
    }



}