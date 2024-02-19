package com.example.menunavegacionaritzperez

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import com.google.android.material.badge.BadgeDrawable
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNavigationView : BottomNavigationView
    private val badgeCounts = mutableMapOf<Int, Int>()
    private val badgeClicks = mutableSetOf<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id. toolbar)
        setSupportActionBar(toolbar)
        setupBottomMenu()
    }

    private fun setupBottomMenu () {
        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.setOnItemSelectedListener { item -> onItemSelectedListener(item) }
        bottomNavigationView.setSelectedItemId(R.id.page_home)

        bottomNavigationView.menu.forEach { menuItem ->
            val badge = bottomNavigationView .getOrCreateBadge(menuItem.itemId)
            badge.isVisible = false
            badge.badgeGravity = BadgeDrawable.TOP_START
            badgeCounts [menuItem.itemId] = 0

        }


    }
    private fun onItemSelectedListener (item: MenuItem): Boolean {
        val itemId = item.itemId
        if (!badgeClicks.contains(itemId)) {
            val badge = bottomNavigationView.getOrCreateBadge(itemId)
            badge.isVisible = true
            badgeClicks.add(itemId)
        }
        val currentCount = badgeCounts [itemId] ?: 0
        badgeCounts [itemId] = currentCount + 1
        val badge = bottomNavigationView .getOrCreateBadge(itemId)
        badge.number = badgeCounts [itemId] ?: 0

        when (item.itemId) {
            R.id. page_home -> {
                showPageFragment(R.drawable. icon_home, R.string. bottom_nav_home)
                return true
            }
            R.id. page_fav -> {
                showPageFragment(R.drawable. icon_fav, R.string. bottom_nav_fav)
                return true
            }
            R.id. page_search -> {
                showPageFragment(R.drawable. icon_search, R.string. bottom_nav_search)
                return true
            }
            R.id. page_settings -> {
                showPageFragment(R.drawable. icon_settings, R.string. bottom_nav_settings)
                return true
            }
            else -> throw IllegalArgumentException( "Item not implemented: ${item.itemId}")
        }
    }
    private fun showPageFragment (@DrawableRes iconId: Int, @StringRes title: Int) {
        val fragment = fragment_page.newInstance(iconId)
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(R.anim.bottom_nav_enter, R.anim.bottom_nav_exit)
            .replace(R.id. container, fragment)
            .commit()
    }
}