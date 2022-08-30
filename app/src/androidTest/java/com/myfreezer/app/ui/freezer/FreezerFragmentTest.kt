package com.myfreezer.app.ui.freezer


import android.view.View
import android.widget.ListView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.*
import androidx.test.espresso.matcher.PreferenceMatchers.withTitle
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microsoft.appcenter.espresso.Factory
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FreezerListItemBinding
import com.myfreezer.app.models.FreezerItem
import com.myfreezer.app.ui.main.MainActivity
import org.hamcrest.CustomMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.*
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FreezerFragmentTest {

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()

    @Before
    fun setup(){
        //launches both the mainActivity and loads desired fragment into the fragmentContainer
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(FreezerFragment())
        }
    }

    @Test
    fun test_isRecyclerViewDisplaying(){
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
    }

    //Scenario 1: User successfully added an item to the list
    @Test
    fun addItemAddButton_whenClicked_addsItemToList(){

        //GIVEN the user has navigated to the freezer activity
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND the add form is open and completed correctly
        onView(withId(R.id.addItemNameField)).perform(typeText("test7"))
        onView(withId(R.id.addItemQuantityField)).perform(typeText("3"))
        onView(withId(R.id.addItemUnitField)).perform(typeText("kg"))
        onView(withId(R.id.addItemMinimumField)).perform(typeText("2"))

        //WHEN the user clicks on the add button
        onView(withId(R.id.addItemAddButton)).perform(click())

        //THEN the item appears in the list
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("test7")),click()))
    }

    //Test Scenario 2
    @Test
    fun addedItem_isDuplicate_mergedWithExistingItem(){
        //GIVEN the user has successfully navigated to the Freezer activity
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND an item already exist in the list
        onView(withId(R.id.addItemNameField)).perform(typeText("mergeItem"))
        onView(withId(R.id.addItemQuantityField)).perform(typeText("30"))
        onView(withId(R.id.addItemUnitField)).perform(typeText("kg"))
        onView(withId(R.id.addItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.addItemAddButton)).perform(click())
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("mergeItem")),click()))

        //WHEN the user adds an item with an identical name
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))
        onView(withId(R.id.addItemNameField)).perform(typeText("mergeItem"))
        onView(withId(R.id.addItemQuantityField)).perform(typeText("40"))
        onView(withId(R.id.addItemUnitField)).perform(typeText("kg"))
        onView(withId(R.id.addItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.addItemAddButton)).perform(click())

        //THEN the new item is merged with already existing item adding the new item amount to existing item amount, and updating the min amount to the new item’s minimum amount.
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView))
            .perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("70")),click()))

    }

    //Scenario 4: User attempts to add item without completing all the fields.
    @Test
    fun addItemAddButton_whenFormNotCompleted_buttonDisabled(){

        //GIVEN the “Add Item” modal is open
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND not all the text fields  have been completed
        onView(withId(R.id.addItemNameField)).perform(typeText("Incomplete Item"))
        onView(withId(R.id.addItemQuantityField)).perform(typeText("4"))
        onView(withId(R.id.addItemUnitField)).perform(clearText())
        onView(withId(R.id.addItemMinimumField)).perform(clearText())

        //WHEN the user clicks on the “ADD” button
        onView(withId(R.id.addItemAddButton)).perform(click())

        //THEN the “ADD” button remains disabled
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))
        onView(withId(R.id.addItemAddButton)).check(matches(not(isClickable())))

        //AND the user is prompted to complete the text fields.

        //AND the item is not added

    }

    //Test Scenario 5: User clicks on the add button
    @Test
    fun addItemFab_whenClicked_DisplaysAddItemModal(){
        //GIVEN the user has successfully navigated to the freezer Activity
        //WHEN user clicks on add item button
        onView(withId(R.id.fabAddItem)).perform(click())

        //THEN the add item modal is displayed
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //that contains the following fields:
        // item name,
        onView(withId(R.id.addItemNameField)).check(matches(isDisplayed()))
        // quantity,
        onView(withId(R.id.addItemQuantityField)).check(matches(isDisplayed()))
        // unit type,
        onView(withId(R.id.addItemUnitField)).check(matches(isDisplayed()))
        // and minimum amount.
        onView(withId(R.id.addItemMinimumField)).check(matches(isDisplayed()))
    }

    //Test Scenario 6:
    @Test
    fun addItemCancelButton_whenClicked_cancelsDisplayOfModal(){
        //GIVEN the add item modal is open
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //WHEN the user clicks on cancel
        onView(withId(R.id.addItemCancelButton)).perform(click())

        //THEN the modal disappears
        onView(withText(R.string.modal_title_add)).check(doesNotExist())

        //AND the user is taken back to the Freezer activity
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))

        //AND nothing is added to the Freezer list
        //onView(withId(R.id.freezerRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<FreezerAdapter.FreezerViewHolder>(1,click()))

    }

    //Scenario 7: No items in list
    @Test
    fun freezerItemList_isEmpty_bgMessageIsDisplayed(){
        //GIVEN that there are no items in the freezer list
//

        //WHEN the user navigates to the Freezer activity

        //THEN the user should be presented with a message  notifying the user that the list is empty.
        onView(withId(R.id.freezerEmptyMessage)).check(matches(isDisplayed()))
    }

//    Scenario 1: User selects item to delete
    @Test
    fun freezerListItem_isSelected_contextMenuIsDisplayed(){
    //    GIVEN the user has navigated to the freezer activity
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

    //    AND there is at least one item in the list
        onView(withId(R.id.addItemNameField)).perform(typeText("deleteItem"))
        onView(withId(R.id.addItemQuantityField)).perform(typeText("2"))
        onView(withId(R.id.addItemUnitField)).perform(typeText("kg"))
        onView(withId(R.id.addItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.addItemAddButton)).perform(click())
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("deleteItem")),click()))


    //    WHEN the user long press on the item
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("deleteItem")), longClick()))

    //    THEN the item is highlighted

    //    AND a context menu appears with the option to delete item
        onView(withId(R.id.contextMenu)).check(matches(isDisplayed()))
    }


    @After
    fun tearDown(){
        //For app center
        // takes screenshot of end result after test
        reportHelper.label("Finishing test")
    }





}

