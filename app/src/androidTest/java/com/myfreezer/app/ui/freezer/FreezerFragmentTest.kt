package com.myfreezer.app.ui.freezer


import androidx.fragment.app.testing.FragmentScenario
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
import com.microsoft.appcenter.espresso.Factory
import com.microsoft.appcenter.espresso.ReportHelper
import org.junit.After
import org.junit.Rule

@RunWith(AndroidJUnit4::class)
class FreezerFragmentTest {

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()

    @Before
    fun setup(){
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(FreezerFragment())
        }


    }

    @Test
    fun test_isRecyclerViewDisplaying(){
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
    }

    @After
    fun tearDown(){
        reportHelper.label("Finishing test")
    }

}