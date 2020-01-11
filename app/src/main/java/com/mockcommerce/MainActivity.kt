package com.mockcommerce

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.navigation.NavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mockcommerce.modules.shared.product_list.ProductListViewModel
import com.mockcommerce.modules.shared.product_page.ProductViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class MainActivity : AppCompatActivity(){

    private var currentNavController: LiveData<NavController>? = null

    private val indexToPage = mapOf(
        0 to R.id.explore,
        1 to R.id.categories,
        2 to R.id.basket,
        3 to R.id.order,
        4 to R.id.account
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            setupBottomNavigationBar()
        }

        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())

        val dataModule = module {
            single {OkHttpClient()}
            single {AppRepository(get())}
            viewModel {ProductViewModel(get())}
            viewModel {ProductListViewModel(get())}
        }

        startKoin {
            androidContext(applicationContext)
            modules(dataModule)
        }


    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        // Now that BottomNavigationBar has restored its instance state
        // and its selectedItemId, we can proceed with setting up the
        // BottomNavigationBar with Navigation
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        val navGraphIds = listOf(
            R.navigation.explore,
            R.navigation.categories,
            R.navigation.basket,
            R.navigation.order,
            R.navigation.account
        )

        // Setup the bottom navigation view with a list of navigation graphs
        val controller = bottomNavigationView.setupWithNavController(
            navGraphIds = navGraphIds,
            fragmentManager = supportFragmentManager,
            containerId = R.id.nav_host_container,
            intent = intent
        )

        // Whenever the selected controller changes, setup the action bar.

        currentNavController = controller
    }

    override fun onSupportNavigateUp(): Boolean {
        return currentNavController?.value?.navigateUp() ?: false
    }





}
