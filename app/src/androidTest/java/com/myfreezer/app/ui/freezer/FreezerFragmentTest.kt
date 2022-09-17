package com.myfreezer.app.ui.freezer


import android.graphics.ColorSpace.match
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.ListView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.hamcrest.*
import org.hamcrest.Matchers.*
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.endsWith
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
    lateinit var recyclerView: RecyclerView

    @Before
    fun setup(){
        //launches both the mainActivity and loads desired fragment into the fragmentContainer
        ActivityScenario.launch(MainActivity::class.java).onActivity{
            it.replaceFragment(FreezerFragment())
            recyclerView = it.findViewById(R.id.freezerRecyclerView)
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
        onView(isEditTextInLayout(R.id.dialogItemNameField)).perform(typeText("Add Item"))
        onView(isEditTextInLayout(R.id.dialogItemQuantityField)).perform(typeText("3"))
        onView(isEditTextInLayout(R.id.dialogItemUnitField)).perform(typeText("kg"))
        onView(isEditTextInLayout(R.id.dialogItemMinimumField)).perform(typeText("2"))

        //WHEN the user clicks on the add button
        onView(withId(R.id.dialogItemSubmitButton)).perform(click())

        //THEN the item appears in the list
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("Add Item")),click()))
    }

    //Test Scenario 2
    @Test
    fun addedItem_isDuplicate_mergedWithExistingItem(){
        //TODO:THIS TEST FAILS, Create Merge Item function
        //GIVEN the user has successfully navigated to the Freezer activity
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND an item already exist in the list
        onView(isEditTextInLayout(R.id.dialogItemNameField)).perform(typeText("mergeItem"))
        onView(isEditTextInLayout(R.id.dialogItemQuantityField)).perform(typeText("30"))
        onView(isEditTextInLayout(R.id.dialogItemUnitField)).perform(typeText("kg"))
        onView(isEditTextInLayout(R.id.dialogItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.dialogItemSubmitButton)).perform(click())
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("mergeItem")),click()))

        //WHEN the user adds an item with an identical name
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))
        onView(isEditTextInLayout(R.id.dialogItemNameField)).perform(typeText("mergeItem"))
        onView(isEditTextInLayout(R.id.dialogItemQuantityField)).perform(typeText("40"))
        onView(isEditTextInLayout(R.id.dialogItemUnitField)).perform(typeText("kg"))
        onView(isEditTextInLayout(R.id.dialogItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.dialogItemSubmitButton)).perform(click())

        //THEN the new item is merged with already existing item adding the new item amount to existing item amount, and updating the min amount to the new item’s minimum amount.
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        //onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("70")),click()))

    }

    //Scenario 4: User attempts to add item without completing all the fields.
    @Test
    fun addItemAddButton_whenFormNotCompleted_buttonDisabled(){

        //GIVEN the “Add Item” modal is open
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND not all the text fields  have been completed
        onView(isEditTextInLayout(R.id.dialogItemNameField)).perform(typeText("Incomplete Item"))
        onView(isEditTextInLayout(R.id.dialogItemQuantityField)).perform(typeText("4"))
        onView(isEditTextInLayout(R.id.dialogItemUnitField)).perform(clearText())
        onView(isEditTextInLayout(R.id.dialogItemMinimumField)).perform(clearText())

        //WHEN the user clicks on the “ADD” button
        onView(withId(R.id.dialogItemSubmitButton)).perform(click())

        //THEN the “ADD” button remains disabled
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //AND the user is prompted to complete the text fields.
        onView(withText("Kg? Pcs?")).check(matches(isDisplayed()))
        onView(withText("warn when lower than?")).check(matches(isDisplayed()))

        //AND the item is not added
        onView(withId(R.id.dialogItemCancelButton)).perform(click())
        onView(withId(R.id.freezerRecyclerView)).check(matches(not(hasDescendant(withText("Incomplete item")))))

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
        onView(withId(R.id.dialogItemNameField)).check(matches(isDisplayed()))
        // quantity,
        onView(withId(R.id.dialogItemQuantityField)).check(matches(isDisplayed()))
        // unit type,
        onView(withId(R.id.dialogItemUnitField)).check(matches(isDisplayed()))
        // and minimum amount.
        onView(withId(R.id.dialogItemMinimumField)).check(matches(isDisplayed()))
    }

    //Test Scenario 6:
    @Test
    fun addItemCancelButton_whenClicked_cancelsDisplayOfModal(){
        //TODO:Refactor this test
        //GIVEN the add item modal is open
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

        //WHEN the user clicks on cancel
        onView(withId(R.id.dialogItemCancelButton)).perform(click())

        //THEN the modal disappears
        //onView(withText(R.string.modal_title_add)).check(doesNotExist())

        //AND the user is taken back to the Freezer activity
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))

        //AND nothing is added to the Freezer list
        //onView(withId(R.id.freezerRecyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition<FreezerAdapter.FreezerViewHolder>(1,click()))

    }

    //Scenario 7: No items in list
    @Test
    fun freezerItemList_isEmpty_bgMessageIsDisplayed(){
        //GIVEN that there are no items in the freezer list

        //get datalist
        val adapter = recyclerView.getAdapter()
        var count = adapter!!.itemCount



//        for(i in 1..count){
//            Log.e("Count",i.toString())
//            onView(withId(R.id.freezerRecyclerView))
//                .perform(scrollToPosition<FreezerAdapter.FreezerViewHolder>(i))
//                .perform(actionOnItemAtPosition<FreezerAdapter.FreezerViewHolder>(i, longClick()))
//
//
//            onView(withId(R.id.freezerContextMenuDelete)).check(matches(isDisplayed()))
//            onView(withId(R.id.freezerContextMenuDelete)).perform(click())
//            onView(withId(R.id.deleteConfirmationConfirmButton)).perform(click())
//
//        }

        //click on item
        //find context menu delete button and click
        //find confirmation button and confirm




        //onView(allOf(withId(R.id.freezerListItem), isAssignableFrom(CardView::class.java))).perform(click())
        //onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("deleteItem")), longClick()))


        //WHEN the user navigates to the Freezer activity

        //THEN the user should be presented with a message  notifying the user that the list is empty.
        //onView(withId(R.id.freezerEmptyMessage)).check(matches(isDisplayed()))
    }

//    Scenario 1: User selects item to delete
    @Test
    fun freezerListItem_isSelected_contextMenuIsDisplayed(){
    //    GIVEN the user has navigated to the freezer activity
        onView(withId(R.id.fabAddItem)).perform(click())
        onView(withText(R.string.modal_title_add)).check(matches(isDisplayed()))

    //    AND there is at least one item in the list
        onView(isEditTextInLayout(R.id.dialogItemNameField)).perform(typeText("deleteItem"))
        onView(isEditTextInLayout(R.id.dialogItemQuantityField)).perform(typeText("2"))
        onView(isEditTextInLayout(R.id.dialogItemUnitField)).perform(typeText("kg"))
        onView(isEditTextInLayout(R.id.dialogItemMinimumField)).perform(typeText("2"))
        onView(withId(R.id.dialogItemSubmitButton)).perform(click())
        onView(withId(R.id.freezerRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("deleteItem")),click()))


    //    WHEN the user long press on the item
        onView(withId(R.id.freezerRecyclerView)).perform(actionOnItem<FreezerAdapter.FreezerViewHolder>(hasDescendant(withText("deleteItem")), longClick()))

    //    THEN the item is highlighted

    //    AND a context menu appears with the option to delete item
        //onView(withId(R.id.contextMenu)).check(matches(isDisplayed()))
    }


    @After
    fun tearDown(){
        //For app center
        // takes screenshot of end result after test
        reportHelper.label("Finishing test")
    }

    //HELPER FUNCTIONS

    /**
     * @method isEditTextInLayout
     * @Description finds the edit text nested within the textInputLayout
     * This is nescesary since textInputLayout is not itself an edit text
     * and therefore exrpesso cannot type() into it.
     * @param {ID} parentViewId: id of the parent view
     * @return the edit text nested inside the input layout view
     */
    fun isEditTextInLayout(parentViewId: Int): Matcher<View> {
        return allOf(
            isDescendantOfA(withId(parentViewId)),
            withClassName(endsWith("EditText"))

        )
    }





}

