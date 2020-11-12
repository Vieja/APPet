package com.vieja.appet

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home, R.id.navigation_care,
            R.id.navigation_info, R.id.blankFragment
        ) //Pass the ids of fragments from nav_graph which you d'ont want to show back button in toolbar
            .setDrawerLayout(drawer_layout) //Pass the drawer layout id from activity xml
            .build()
        visibilityNavElements(navController)

    }

    private fun visibilityNavElements(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.careCategoryFragment -> hideDrawerNavigation()
                R.id.blankFragment -> hideBottomNavigation()
                else -> showBothNavigation()
            }
        }

    }

    private fun hideBothNavigation() { //Hide both drawer and bottom navigation bar
        nav_bottom_view?.visibility = View.GONE
        nav_drawer_view?.visibility = View.GONE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideDrawerNavigation() { //Hide drawer navigation bar
        nav_bottom_view?.visibility = View.VISIBLE
        nav_drawer_view?.visibility = View.GONE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED) //To lock navigation drawer so that it don't respond to swipe gesture
    }

    private fun hideBottomNavigation() { //Hide bottom navigation
        nav_bottom_view?.visibility = View.GONE
        nav_drawer_view?.visibility = View.VISIBLE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED) //To unlock navigation drawer
        nav_drawer_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
    }


    private fun showBothNavigation() {
        nav_bottom_view?.visibility = View.VISIBLE
        nav_drawer_view?.visibility = View.VISIBLE
        drawer_layout?.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
        setupNavControl() //To configure navController with drawer and bottom navigation
    }

    private fun setupNavControl() {
        nav_drawer_view?.setupWithNavController(navController) //Setup Drawer navigation with navController
        nav_bottom_view?.setupWithNavController(navController) //Setup Bottom navigation with navController
    }

    override fun onSupportNavigateUp(): Boolean { //Setup appBarConfiguration for back arrow
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }

    override fun onBackPressed() {
        when { //If drawer layout is open close that on back pressed
            drawer_layout.isDrawerOpen(GravityCompat.START) -> {
                drawer_layout.closeDrawer(GravityCompat.START)
            }
            else -> {
                super.onBackPressed() //If drawer is already in closed condition then go back
            }
        }
    }


}