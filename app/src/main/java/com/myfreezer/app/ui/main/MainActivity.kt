package com.myfreezer.app.ui.main

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.myfreezer.app.BuildConfig
import com.myfreezer.app.R
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.favourites.FavouriteFragment
import com.myfreezer.app.ui.freezer.FreezerFragment

import com.myfreezer.app.ui.recipes.recipesFilter.RecipesFilterFragment
import com.myfreezer.app.ui.recipes.RecipesFragment
import com.myfreezer.app.ui.recipes.RecipesViewModel

import com.myfreezer.app.ui.recipes.recipedetail.RecipeDetailFragment
import com.myfreezer.app.ui.shopping.ShoppingFragment

/**
 * @class: MainActivity
 * @description: The main activity class. This class contains the navigation and fragment host view
 * as well as the bottom navigation bar, and the top action bar.
 */

class MainActivity : AppCompatActivity(),NavigationHandler {


    //Declare variables for main activity
    private val freezerFragment = FreezerFragment()
    private val recipesFragment = RecipesFragment()
    private val favouriteFragment = FavouriteFragment()
    private val shoppingFragment = ShoppingFragment()

    //LifeCycle Methods

    /**
     * @method: onCreate
     * @description: The first method in the activity Lifecycle. Used to initialized and inflate views before they are displayed.
     * @param {Bundle?} savedInstanceState: contains the data with which to initialize activity. To save data before activity is killed,
     * use onSaveInstanceState(bundle), to restore the data after activity restart call onRestoreInstanceState()
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)

        //Setup App Center Analytics
        val appCenterKey = BuildConfig.APP_CENTER_API_KEY
        AppCenter.start(getApplication(), appCenterKey,Analytics::class.java, Crashes::class.java);

        //Create bottom navigation bar
        //Instantiated here otherwise it produces an error
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavClickListener(bottomNav,freezerFragment,recipesFragment,favouriteFragment,shoppingFragment)

    }



    /**
     * @method bottomNavClickListener
     * @description Listens for touch events on bottom navigation bar and
     * navigates to the appropriate fragment
     * @param {BottomNavigationView} bottomNavBar- the bottom navigation bar
     * @param {Fragment} freezerFragment - the fragment for the freezer view
     * @param {Fragment} recipeFragment - the fragment for the freezer view
     * @param {Fragment} favouriteFragment - the fragment for the freezer view
     * @param {Fragment} shoppingFragment - the fragment for the freezer view
     */

    /**
     * @method bottomNavClickListener
     * @description Listens for touch events on bottom navigation bar and
     * navigates to the appropriate fragment
     * @param {BottomNavigationView} bottomNavBar- the bottom navigation bar
     * @param {Fragment} freezerFragment - the fragment for the freezer view
     * @param {Fragment} recipeFragment - the fragment for the freezer view
     * @param {Fragment} favouriteFragment - the fragment for the freezer view
     * @param {Fragment} shoppingFragment - the fragment for the freezer view
     */
    private fun bottomNavClickListener(
        bottomNavBar: BottomNavigationView,
        freezerFragment: FreezerFragment,
        recipesFragment: RecipesFragment,
        favouriteFragment: FavouriteFragment,
        shoppingFragment: ShoppingFragment
    ){
        bottomNavBar.setOnItemSelectedListener {
            when(it.itemId){
                R.id.bottomNavItemFreezer -> {
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    replaceFragment(freezerFragment)
                    setTitle("My Freezer")

                }
                R.id.bottomNavItemRecipe ->{
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    replaceFragment(recipesFragment)
                    setTitle("Recipe Suggestions")

                }
                R.id.bottomNavItemFavourite ->{
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    replaceFragment(favouriteFragment)
                    setTitle("Favourite Recipes")

                }
                R.id.bottomNavItemShopping ->{
                    supportActionBar!!.setDisplayHomeAsUpEnabled(false)
                    replaceFragment(shoppingFragment)
                    setTitle("Shopping List")

                }
                else -> {
                    //Do nothing
                }
            }
            return@setOnItemSelectedListener true
        }
    }


    /**
     * @method replaceFragment
     * @description swaps fragments within the NavHost
     * @param {Fragment} the fragment to change to
     */
    fun replaceFragment(fragment: Fragment){
        if(fragment !=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.navHostFragment, fragment)
            transaction.commit()
        }
    }

    /**
     * @method: transferData
     * @description: A method inherited from the communicator interface. It saves the recipe item in a bundle
     * which it passses to the recipeDetails fragment as an argument, after which it navigates to the recipeDetails fragment
     * This method, although defined here, is called from the recipe fragment.
     * @param {RecipeItem} data - the RecipeItem to pass to the details view
     */
    override fun transferData(data: RecipeItem) {
        //wrap in bundle
        var bundle = Bundle()
        bundle.putParcelable("RecipeItem",data)

        //pass as argument to recipeDetailFragment
        var recipeDetailFragment = RecipeDetailFragment()
        recipeDetailFragment.arguments = bundle

        //Set back button on action bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //navigate to recipeDetail view
        replaceFragment(recipeDetailFragment)
    }


    override fun filterNavTrigger(viewModel:RecipesViewModel){


        val filterMenu = RecipesFilterFragment(viewModel)
        if(filterMenu !=null){


            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.navHostFragment, filterMenu)
            transaction.addToBackStack("Filter")
            transaction.commit()
        }
    }


    /**
     * @method:onSupportNavigateUp
     * @description: Called when the actionbar back button is pressed. Navigates back to the recipes list view.
     * @return {Boolean} true
     */
    override fun onSupportNavigateUp(): Boolean{
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        replaceFragment(RecipesFragment())
        setTitle("Recipe Suggestions")
        return true
    }


}

