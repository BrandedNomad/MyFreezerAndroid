package com.myfreezer.app.ui.recipes

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.myfreezer.app.R
import com.myfreezer.app.databinding.FragmentRecipesBinding

import android.app.SearchManager
import android.opengl.Visibility
import android.util.Log
import android.view.View.GONE
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.view.menu.ActionMenuItem
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.constraintlayout.widget.ConstraintSet.GONE
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.myfreezer.app.models.RecipeItem
import com.myfreezer.app.ui.main.NavigationHandler


/**
 * @class RecipesFragment
 * @description Contains the implementation for the RecipesFragment
 */
class RecipesFragment: Fragment(), MenuProvider {

    //Declare Variables
    lateinit var viewModel:RecipesViewModel
    lateinit var adapter:RecipesAdapter
    lateinit var searchActionMode:ActionMode
    lateinit var navigationHandler: NavigationHandler
    lateinit var filteredList:MutableList<RecipeItem>



    /**
     * @method onCreateView
     * @description Inflates and displays the fragment
     * @param {LayoutInflater} inflator
     * @param {ViewGroup?} container
     * @param {Bundle?} savedInstanceState
     * @return {FragmentRecipesBinding} binding.root
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Databinding
        val binding: FragmentRecipesBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_recipes,
            container,
            false
        )



        binding.lifecycleOwner = this

        navigationHandler = requireActivity() as NavigationHandler

        filteredList = mutableListOf<RecipeItem>()



        //Setup viewModel
        val application = requireNotNull(this.activity).application
        val viewModelFactory = RecipesViewModelFactory(application)
        viewModel = ViewModelProvider(this,viewModelFactory)[RecipesViewModel::class.java]

        //setup adapter
        adapter = RecipesAdapter(RecipesAdapter.OnClickListener{
            //Navigate to recipe detail view
            viewModel.navigate(it)
        })

        binding.recipesRecyclerView.adapter = adapter

        //Set Observers
        //Watch for recipes
        viewModel.recipeList.observe(viewLifecycleOwner,Observer{

            if(it.size > 0){ //If the list contains recipes
                //Remove the "no recipes" background message and display the list of recipes
                binding.recipeEmptyMessage.setVisibility(View.GONE)
                adapter.submitList(it)
            }else{
                //Display background "no recipes" message
                binding.recipeEmptyMessage.setVisibility(View.VISIBLE)
            }

        })

        //When the navigation trigger changes
        viewModel.navigationTrigger.observe(viewLifecycleOwner,Observer{
            //recipe has been selected
            if(it != null){


                //reset navigation trigger before navigating
                viewModel.doneNavigating()
                //navigate to details fragment
                navigationHandler.transferData(it)
            }
        })

        viewModel.recipesFilter.observe(viewLifecycleOwner,Observer{filterKeys ->

            //create filtered list
            var filteredList = mutableListOf<RecipeItem>()


            //get the recipe list
            var recipeList = viewModel.recipeList.value


            //modify filtered list
            filterKeys.forEach{ key ->
                for(item in recipeList!!.iterator()){
                    if(item.freezerItem == key ){
                        filteredList.add(item)
                    }
                }
            }

            //submit that list
            Log.e("RecipeFragment - filteredlist-2",filteredList.toString())
            if(filteredList.size > 0){
                var listToDisplay = filterForPreferences(filteredList,viewModel.getPreferences()!!)
                    //TODO implement preferences filter
                adapter.submitList(filteredList)
                Log.e("DisplayveganOnClick",viewModel.getPreferences().toString())
            }else{
                adapter.submitList(recipeList)
            }

        })





        //return view
        return binding.root
    }


    fun filterForPreferences(recipeList:List<RecipeItem>?,filter:MutableList<String>):List<RecipeItem>? {
        Log.e("A","inside")

        if(filter.isNullOrEmpty()){
            Log.e("B","inside")
            return recipeList
        }

        Log.e("C","inside")

        if(filter!!.size!! > 0){
            Log.e("D","inside")
            var newRecipeList = mutableListOf<RecipeItem>()
            filter.forEach{
                Log.e("E","inside")
                for(recipe in recipeList!!.iterator()){
                    Log.e("F","inside")
                    if(recipe.getPreference(it)){
                        Log.e("G","inside")
                        newRecipeList.add(recipe)
                    }
                }
            }
            return newRecipeList
        }else{
            return recipeList
        }

    }

    /**
     * @method onViewCreated
     * @description a fragment lifecycle method, called directly after createView has returned and the view hierarchy has
     * been created, and before the view is attached to it's parents. Used to initialize properties that require an existing view
     * hierarchy before attaching view to parent.
     * @param {View} view - the view created by onCreateView
     * @param {Bundle} savedInstanceState - the instance state data
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Adds an option menu to the actionbar
        activity?.addMenuProvider(this,viewLifecycleOwner) //adding the viewLifecycleOwner, means it does the cleanup automaically

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {



        menuInflater.inflate(R.menu.recipe_action_items,menu)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            R.id.recipeSearch -> {

                //Change the style of actionbar

                val searchView:SearchView = menuItem.getActionView() as SearchView
                searchView.setQueryHint("Search for recipes online")


                searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
                    override fun onQueryTextSubmit(query: String?): Boolean {
                        Log.i("Search1",query.toString())
                        return true
                    }

                    override fun onQueryTextChange(newText: String?): Boolean {
                        Log.i("Search2",newText.toString())
                        return false
                    }

                })


            }
            R.id.recipeFilter ->{
                navigationHandler.filterNavTrigger(viewModel)

            }
        }
        return true

    }

    fun startSearchActionMode(){
        var fragmentActivity = getActivity()
        fragmentActivity!!.startActionMode(searchActionModeCallback())
    }

    fun searchActionModeCallback():ActionMode.Callback{
        return object: ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                //Inflate the menu with options that are displayed when context menu is shown
                mode?.getMenuInflater()!!.inflate(R.menu.freezer_context_menu,menu)

                //Set title for context menu
                mode.setTitle("Options");


                return true
            }

            override fun onPrepareActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return true
            }

            override fun onActionItemClicked(p0: ActionMode?, p1: MenuItem?): Boolean {
                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {

            }

        }
    }

    /**
     * @method onDestroy()
     * @description A lifecycle method that is called before view is destroyed. Used for cleanup
     */
    override fun onDestroy() {
        super.onDestroy()

        //Close the appbar context menu when navigating away from the fragment
        if(this::searchActionMode.isInitialized){
            searchActionMode.finish()
        }
    }


}

