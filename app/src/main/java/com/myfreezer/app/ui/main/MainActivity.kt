package com.myfreezer.app.ui.main

import android.os.Bundle
import android.view.MenuItem
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
import com.myfreezer.app.ui.recipes.RecipesFragment
import com.myfreezer.app.ui.recipes.recipedetail.RecipeDetailFragment
import com.myfreezer.app.ui.shopping.ShoppingFragment

class MainActivity : AppCompatActivity(),Communicator {

    private val freezerFragment = FreezerFragment()
    private val recipesFragment = RecipesFragment()
    private val favouriteFragment = FavouriteFragment()
    private val shoppingFragment = ShoppingFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)


//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
//        val navController = navHostFragment.navController

        //Instantiated here otherwise it produces an error
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        //set navbar color
        bottomNavClickListener(bottomNav,freezerFragment,recipesFragment,favouriteFragment,shoppingFragment)


        //Get Keys
        val appCenterKey = BuildConfig.APP_CENTER_API_KEY

        //App Center Analytics
        AppCenter.start(getApplication(), appCenterKey,Analytics::class.java, Crashes::class.java);


    }

    fun displayContextMenu(){
        var currentTitle = getTitle()

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
                    //supportFragmentManager.getFragment()

                    replaceFragment(freezerFragment)
                    setTitle("My Freezer")

                }
                R.id.bottomNavItemRecipe ->{

                    replaceFragment(recipesFragment)
                    setTitle("Recipe Suggestions")

                }
                R.id.bottomNavItemFavourite ->{

                    replaceFragment(favouriteFragment)
                    setTitle("Favourite Recipes")

                }
                R.id.bottomNavItemShopping ->{

                    replaceFragment(shoppingFragment)
                    setTitle("Shopping List")

                }
                else -> {

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

    override fun transferData(data: RecipeItem) {
        var bundle = Bundle()
        bundle.putParcelable("RecipeItem",data)
        var recipeDetailFragment = RecipeDetailFragment()
        recipeDetailFragment.arguments = bundle

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)


        replaceFragment(recipeDetailFragment)
    }



    override fun onSupportNavigateUp(): Boolean{
        supportActionBar!!.setDisplayHomeAsUpEnabled(false)
        replaceFragment(RecipesFragment())
        setTitle("Recipe Suggestions")
        return true
    }


}

