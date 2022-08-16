package com.myfreezer.app

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
}

//Scenario 1: User successfully added an item to the list
//
//GIVEN the user has navigated to the freezer activity
//
//AND the add form is open and completed correctly
//
//WHEN the user clicks on the add button
//
//THEN the item appears in the list
//
//
//
//Scenario 2: User attempts to add duplicate item
//
//GIVEN the user has successfully navigated to the Freezer activity
//
//AND an item already exist in the list
//
//WHEN the user adds an item with an identical name
//
//THEN the new item is merged with already existing item adding the new item amount to existing item amount, and updating the min amount to the new item’s minimum amount.
//
//
//
//Scenario 3: User Enters the wrong information in text fields
//
//GIVEN that the “Add Item” modal is open
//
//WHEN the user enters the incorrect information
//
//THEN an error message is displayed prompting the user to enter the correct information.
//
//AND the “ADD” button remains disabled
//
//
//
//Scenario 4: User attempts to add item without completing all the fields.
//
//GIVEN the “Add Item” modal is open
//
//AND not all the text fields  have been completed
//
//WHEN the user clicks on the “ADD” button
//
//THEN the item is not added
//
//AND the “ADD” button remains disabled
//
//AND the user is prompted to complete the text fields.
//
//
//
//Scenario 5: User clicks on the add button
//
//GIVEN the user has successfully navigated to the Freezer activity
//
//WHEN the user clicks on the add button
//
//THEN the user is presented with a form that allows the user to enter the item name, quantity, unit type, and minimum amount.
//
//
//
//Scenario 6: User clicks on cancel
//
//GIVEN the add item modal is open
//
//WHEN the user clicks on cancel
//
//THEN the modal disappears
//
//AND the user is taken back to the Freezer activity
//
//AND nothing is added to the Freezer list
//
//
//
//Scenario 7: No items in list
//
//GIVEN that there are no items in the freezer list
//
//WHEN the user navigates to the Freezer activity
//
//THEN the user should be presented with a message  notifying the user that the list is empty.