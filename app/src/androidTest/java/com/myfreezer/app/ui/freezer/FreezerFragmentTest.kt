package com.myfreezer.app.ui.freezer


import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.myfreezer.app.R
import com.myfreezer.app.ui.main.MainActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FreezerFragmentTest {

    private lateinit var scenario:FragmentScenario<FreezerFragment>

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(FreezerFragment())
        }


    }

    @Test
    fun test_ifFreezerFragmentLoads(){
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
    }

}