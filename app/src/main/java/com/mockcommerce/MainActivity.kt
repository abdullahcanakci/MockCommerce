package com.mockcommerce

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),
        BottomNavigationView.OnNavigationItemSelectedListener{


    private val indexToPage = mapOf(
        0 to R.id.dest_explore,
        1 to R.id.dest_categories,
        2 to R.id.dest_basket,
        3 to R.id.dest_orders,
        4 to R.id.dest_account
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupBottomNavigationView()
    }

    private fun setupBottomNavigationView(){
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav)

        bottomNavigationView.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))

        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    /// BottomNavigationView ItemSelected Implementation
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val position = indexToPage.values.indexOf(item.itemId)
        if (bottom_nav.selectedItemId != position)
            Navigation.findNavController(this, R.id.nav_host_fragment).navigate(indexToPage[position]!!)
        return true
    }

}
